package com.food.trucks.service;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.ObjectUtil;
import com.alibaba.excel.EasyExcel;
import com.alibaba.excel.support.ExcelTypeEnum;
import com.fasterxml.jackson.core.type.TypeReference;
import com.food.trucks.entitiy.FoodTruckEntity;
import com.meilisearch.sdk.Client;
import com.meilisearch.sdk.Config;
import com.meilisearch.sdk.Index;
import com.meilisearch.sdk.exceptions.MeilisearchException;
import com.meilisearch.sdk.model.SearchResult;
import lombok.extern.slf4j.Slf4j;
import org.redisson.api.GeoEntry;
import org.redisson.api.GeoUnit;
import org.redisson.api.RGeo;
import org.redisson.api.RedissonClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;
import reactor.util.retry.Retry;

import java.io.ByteArrayInputStream;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.time.Duration;
import java.util.*;
import java.util.stream.Collectors;

import static com.food.trucks.utils.JsonUtils.toJSON;
import static com.food.trucks.utils.JsonUtils.toJavaType;

@Slf4j
@Service
public class FoodTrucksService {

    @Autowired
    private WebClient webClient;
    @Autowired
    private RedissonClient redissonClient;


    private static final String DBKEY = "foodTrucks";
    private static final String GEOHASH_KEY = "foodTrucks_geo";

    public List<FoodTruckEntity> nearBy(double latitude, double longitude, double radius) {

        RGeo<String> geo = redissonClient.getGeo(GEOHASH_KEY);
        Map<String, Double> trucksMap = geo.radiusWithDistance( longitude,latitude,radius,GeoUnit.KILOMETERS);
        List<FoodTruckEntity> trucks =trucksMap.entrySet().stream().map(item -> {
            String json = item.getKey();
            Double distance = item.getValue();
            TypeReference<FoodTruckEntity> typeRef = new TypeReference<FoodTruckEntity>() {};
            FoodTruckEntity foodTruckEntity = toJavaType(json,typeRef);
            foodTruckEntity.setDistanceRedis(distance);
            return foodTruckEntity;
        }).collect(Collectors.toList());

        return trucks;
    }

    public List<FoodTruckEntity> getFromRedis() {
        List<FoodTruckEntity> items = (List<FoodTruckEntity>) redissonClient.getBucket(DBKEY).get();
        if (CollUtil.isEmpty(items)) {
            items = fetchCSVToEntitiy();
            toRedis(items);
            toMeiliSearch(items);
        }
        return items;
    }

    private void toRedis(List<FoodTruckEntity> items) {

        if (CollUtil.isNotEmpty(items)) {
            redissonClient.getBucket(DBKEY).delete();
            redissonClient.getBucket(GEOHASH_KEY).delete();

            redissonClient.getBucket(DBKEY).set(items);
            // to geo key
            RGeo<String> geo = redissonClient.getGeo(GEOHASH_KEY);
            items.forEach(item -> {
                if (item.getLatitude() != 0 && item.getLongitude() != 0) {
                    geo.add(new GeoEntry(
                            item.getLongitude(),
                            item.getLatitude(),
                            toJSON(item)
                    ));
                }
            });
        }
    }

    private void toMeiliSearch(List<FoodTruckEntity> items) {
        if (ObjectUtil.isEmpty(items)) {
            throw new RuntimeException("not found corrent json string in toMeiliSearch()");
        }
        Client client = null;
        try {
            // toulan
            client = new Client(new Config("http://localhost:7700", null));
            Index index = client.index(DBKEY);
            index.addDocuments(toJSON(items));
        } catch (MeilisearchException e) {
            throw new RuntimeException(e);
        } catch (RuntimeException ex) {

        } finally {

        }
    }

    public ArrayList<HashMap<String, Object>> search(String keyword)  {
        ArrayList<HashMap<String, Object>> res = null;
        Client client = null;

        try {
            client = new Client(new Config("http://localhost:7700", null));
            Index index = null;
            index = client.index(DBKEY);
            SearchResult results = index.search(keyword);
            res =  results.getHits();
        } catch (MeilisearchException e) {
            throw new RuntimeException(e);
        }finally {
            return res;
        }
    }

    private List<FoodTruckEntity> fetchCSVToEntitiy() {
        Optional<String> result = fetchCSVString().blockOptional();
        if (result.isPresent()) {
            var stream = new ByteArrayInputStream(result.get().getBytes(StandardCharsets.UTF_8));
            var listener = new FoodTrucksFinderCSVListener();
            EasyExcel.read(stream, FoodTruckEntity.class, listener)
                    .excelType(ExcelTypeEnum.CSV).charset(Charset.defaultCharset()).sheet().doRead();
            return listener.getItems();
        }
        return null;
    }

    private Mono<String> fetchCSVString() {
//        var webClient = WebClient.builder().baseUrl("https://data.sfgov.org/api/views/rqzj-sfat/rows.csv").build();
        return webClient.get()
                .uri("https://data.sfgov.org/api/views/rqzj-sfat/rows.csv")
                .retrieve()
                .bodyToMono(String.class)
                .timeout(Duration.ofSeconds(10))
                .retryWhen(Retry.backoff(3, Duration.ofSeconds(2)));
    }

}


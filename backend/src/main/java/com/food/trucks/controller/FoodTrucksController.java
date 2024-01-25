package com.food.trucks.controller;

import com.food.trucks.entitiy.FoodTruckEntity;
import com.food.trucks.entitiy.response.Resp;
import com.food.trucks.service.FoodTrucksService;
import com.food.trucks.service.HelloWorldService;
import jakarta.annotation.Resource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@CrossOrigin
@RequestMapping("/api/trucks")
public class FoodTrucksController
{
    @Resource
    FoodTrucksService foodTrucksService;
    @Resource
    HelloWorldService helloWorldService;


    @GetMapping("")
    public Object trucks() {
        var list = foodTrucksService.getFromRedis();
        return Resp.ok(list);
    }

    @GetMapping("/nearby")
    public Resp<List<FoodTruckEntity>> findNearbyTrucks(@RequestParam double latitude,
                                              @RequestParam double longitude,
                                              @RequestParam double radius) {
        try {
            var itemMap = foodTrucksService.nearBy(latitude, longitude, radius);
            return Resp.ok(itemMap);
        } catch (Exception e) {
            return Resp.fail(HttpStatus.INTERNAL_SERVER_ERROR.toString(),e.getMessage());
        }
    }

    @GetMapping("/search")
    public Object search(@RequestParam String keyword) {
        try {
            var res = foodTrucksService.search(keyword);
            return Resp.ok(res);
        } catch (Exception e) {
            return Resp.fail(HttpStatus.INTERNAL_SERVER_ERROR.toString(),e.getMessage());
        }
    }

}

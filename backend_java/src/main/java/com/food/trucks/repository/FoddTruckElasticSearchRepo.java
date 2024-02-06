package com.food.trucks.repository;

import com.food.trucks.entitiy.FoodTruckEntity;
import io.qdrant.client.grpc.Points;
import org.springframework.stereotype.Repository;

import java.util.List;

import static io.qdrant.client.ConditionFactory.geoRadius;

@Repository
public class FoddTruckElasticSearchRepo {


    public void addAll(List<FoodTruckEntity> items){
        for ( FoodTruckEntity item:items){

        }
    }
    public void getRadius(){
        Points.Condition condition =  geoRadius("location",
                52.520711, 13.403683, 1000.0f);



    }
}
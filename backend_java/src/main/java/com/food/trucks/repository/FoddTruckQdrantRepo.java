package com.food.trucks.repository;

import io.qdrant.client.grpc.Points;
import org.springframework.stereotype.Repository;
import static io.qdrant.client.ConditionFactory.geoRadius;

@Repository
public class FoddTruckQdrantRepo {


    public void getRadius(){
        Points.Condition condition =  geoRadius("location",
                52.520711, 13.403683, 1000.0f);



    }
}
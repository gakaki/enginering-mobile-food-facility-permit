package com.food.trucks.service;

import com.alibaba.excel.context.AnalysisContext;
import com.alibaba.excel.event.AnalysisEventListener;
import com.food.trucks.entitiy.FoodTruckEntity;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.Map;

@Data
@Slf4j
public class FoodTrucksFinderCSVListener extends AnalysisEventListener<FoodTruckEntity> {
    private ArrayList<FoodTruckEntity> items = new ArrayList<>();
    /**
     * 一行一行的读取excel内容
     */
    public void invoke(FoodTruckEntity row, AnalysisContext analysisContext) {
//        Map<Integer, Cell> rowMap = analysisContext.readRowHolder().getCellMap();
        if ( row.getLatitude() != 0 && row.getLongitude() != 0 ){
            items.add(row);
            log.info("**** {}",row);
        }
    }

    /**
     * 读取表头内容
     */
    public void invokeHeadMap(Map<Integer, String> headMap, AnalysisContext context) {
        log.info("表头为 {}",headMap);
    }

    /**
     * 读取完成操作
     */
    public void doAfterAllAnalysed(AnalysisContext analysisContext) {
        log.info("读取 CSV 完毕,csv总长度为 {}",items.size());
    }
}
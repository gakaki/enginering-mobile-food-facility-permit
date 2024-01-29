package com.food.trucks.entitiy;

import com.alibaba.excel.annotation.ExcelIgnore;
import com.alibaba.excel.annotation.ExcelProperty;
import lombok.Data;
import lombok.extern.jackson.Jacksonized;
import org.nustaq.serialization.annotations.Serialize;

@Data
@Jacksonized
//和easy excel 不能一起加 不然会出现数据遗漏的问题
//@NoArgsConstructor
//@AllArgsConstructor
public class FoodTruckEntity {

    // redis计算出的距离 暂存变量
    @ExcelIgnore()
    private Double distanceRedis;

    // 位置编号
    @ExcelProperty(value = "locationid")
    private String locationid;
    // 申请人
    @ExcelProperty(value = "Applicant")
    private String Applicant;
    // 设施类型
    @ExcelProperty(value = "FacilityType")
    private String FacilityType;
    // CNN编号
    @ExcelProperty(value = "cnn")
    private String cnn;
    // 位置描述
    @ExcelProperty(value = "LocationDescription")
    private String LocationDescription;
    // 地址
    @ExcelProperty(value = "Address")
    private String Address;
    // 地块编号
    @ExcelProperty(value = "blocklot")
    private String blocklot;
    // 街区
    @ExcelProperty(value = "block")
    private String block;
    // 地块
    @ExcelProperty(value = "lot")
    private String lot;
    // 许可证
    @ExcelProperty(value = "permit")
    private String permit;
    // 状态
    @ExcelProperty(value = "Status")
    private String Status;
    // 食品项目
    @ExcelProperty(value = "FoodItems")
    private String FoodItems;
    // X坐标
    @ExcelProperty(value = "x",index = 12)
    private String x;
    // Y坐标
    @ExcelProperty(value = "y",index = 13)
    private String y;
    // 纬度
    @ExcelProperty(value = "Latitude")
    private Double Latitude;
    // 经度
    @ExcelProperty(value = "Longitude")
    private Double Longitude;
    // 时间表
    @ExcelProperty(value = "Schedule")
    private String Schedule;
    // 天小时
    @ExcelProperty(value = "dayshours")
    private String dayshours;
    // 发送噪音通知
    @ExcelProperty(value = "NOISent")
    private String NOISent;
    // 已批准
    @ExcelProperty(value = "Approved")
    private String Approved;
    // 已接收
    @ExcelProperty(value = "Received")
    private Integer Received;
    // 原许可证
    @ExcelProperty(value = "PriorPermit")
    private Integer PriorPermit;
    // 许可证到期日
    @ExcelProperty(value = "ExpirationDate")
    private String ExpirationDate;
    // 位置
    @ExcelProperty(value = "Location")
    private String Location;
    // 消防预防区域
    @ExcelProperty(value = "Fire Prevention Districts")
    private String FirePreventionDistricts;
    // 警察区域
    @ExcelProperty(value = "Police Districts")
    private String PoliceDistricts;
    // 监督区域
    @ExcelProperty(value = "Supervisor Districts")
    private String SupervisorDistricts;
    // 邮政编码
    @ExcelProperty(value = "Zip Codes")
    private String ZipCodes;
    // 旧邻里 (已过时)
    @ExcelProperty(value = "Neighborhoods (old)")
    private String NeighborhoodsOld;
}



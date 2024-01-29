use serde::{Deserialize, Serialize};
#[derive(Debug,Serialize,Deserialize,Clone)]
struct FoodTruck {
    locationid: String,                   // 位置ID
    applicant: String,                    // 申请人
    facility_type: String,                // 设施类型
    cnn: String,                          // CNN（可能是某种代码或标识）
    location_description: String,         // 位置描述
    address: String,                      // 地址
    block_lot: String,                    // 街区地块
    block: String,                        // 街区
    lot: String,                          // 地块
    permit: String,                       // 许可证
    status: String,                       // 状态
    food_items: String,                   // 食品项目
    x: String,                            // X坐标
    y: String,                            // Y坐标
    latitude: String,                     // 纬度
    longitude: String,                    // 经度
    schedule: String,                     // 时间表
    days_hours: String,                   // 天小时（营业时间）
    noi_sent: String,                     // NOI发送
    approved: String,                     // 已批准
    received: String,                     // 已接收
    prior_permit: String,                 // 先前许可证
    expiration_date: String,              // 过期日期
    location: String,                     // 位置
    fire_prevention_districts: String,    // 消防预防区域
    police_districts: String,             // 警察区域
    supervisor_districts: String,         // 监督区域
    zip_codes: String,                    // 邮政编码
    neighborhoods_old: String,            // 老邻里
}
use serde::{Deserialize, Serialize};
#[derive(Debug,Serialize,Deserialize,Clone)]
pub struct FoodTruck {
    pub locationid: String,                   // 位置ID
    #[serde(rename = "Applicant")]
    pub applicant: String,                    // 申请人
    #[serde(rename = "FacilityType")]
    pub facility_type: String,                // 设施类型
    pub cnn: String,                          // CNN（可能是某种代码或标识）
    #[serde(rename = "LocationDescription")]
    pub location_description: String,         // 位置描述
    #[serde(rename = "Address")]
    pub address: String,                      // 地址
    #[serde(rename = "blocklot")]
    pub block_lot: String,                    // 街区地块
    pub block: String,                        // 街区
    pub lot: String,                          // 地块
    pub permit: String,                       // 许可证
    #[serde(rename = "Status")]
    pub status: String,                       // 状态
    #[serde(rename = "FoodItems")]
    pub food_items: String,                   // 食品项目
    #[serde(rename = "X")]
    pub x: String,                            // X坐标
    #[serde(rename = "Y")]
    pub y: String,                            // Y坐标
    #[serde(rename = "Latitude")]
    pub latitude: String,                     // 纬度
    #[serde(rename = "Longitude")]
    pub longitude: String,                    // 经度
    #[serde(rename = "Schedule")]
    pub schedule: String,                     // 时间表
    #[serde(rename = "dayshours")]
    pub days_hours: String,                   // 天小时（营业时间）
    #[serde(rename = "NOISent")]
    pub noi_sent: String,                     // NOI发送
    #[serde(rename = "Approved")]
    pub approved: String,                     // 已批准
    #[serde(rename = "Received")]
    pub received: String,                     // 已接收
    #[serde(rename = "PriorPermit")]
    pub prior_permit: String,                 // 先前许可证
    #[serde(rename = "ExpirationDate")]
    pub expiration_date: String,              // 过期日期
    #[serde(rename = "Location")]
    pub location: String,                     // 位置
    #[serde(rename = "Fire Prevention Districts")]
    pub fire_prevention_districts: String,    // 消防预防区域
    #[serde(rename = "Police Districts")]
    pub police_districts: String,             // 警察区域
    #[serde(rename = "Supervisor Districts")]
    pub supervisor_districts: String,         // 监督区域
    #[serde(rename = "Zip Codes")]
    pub zip_codes: String,                    // 邮政编码
    #[serde(rename = "Neighborhoods (old)")]
    pub neighborhoods_old: String,            // 老邻里
}

export type FoodTruck = {
    uuid: string
    name: string
    short_description: string
    homepage_url: string
    country_code: string
    roles: "company" | "school" | "investor"

    distanceRedis: Number,//距离id
    locationid: string   // 位置ID
    applicant: string   // 申请人
    facilitytype: string   // 设施类型
    cnn: string   // cnn（可能是某种代码或标识）
    locationDescription: string   // 位置描述
    address: string   // 地址
    blocklot: string   // 街区地块
    block: string   // 街区
    lot: string   // 地块
    permit: string   // 许可证
    status: string   // 状态
    foodItems: string   // 食品项目
    x: string   // x坐标
    y: string   // y坐标
    latitude: string   // 纬度
    longitude: string   // 经度
    schedule: string   // 时间表
    dayshours: string   // 天小时（营业时间）
    noisent: string   // noi发送
    approved: string   // 已批准
    received: string   // 已接收
    priorpermit: string   // 先前许可证
    expirationDate: string   // 过期日期
    location: string   // 位置
    firepreventiondistricts: string   // 消防预防区域
    policedistricts: string   // 警察区域
    supervisorDistricts: string   // 监督区域
    zipcodes: string   // 邮政编码
    neighborhoodsOld: string   // 老邻里
}

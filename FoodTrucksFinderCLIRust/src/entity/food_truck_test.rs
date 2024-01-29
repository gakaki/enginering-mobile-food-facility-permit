use serde::{Deserialize, Serialize};
use crate::entity::foodTruck::FoodTruck;

#[tokio::test]
async fn test_parse_url_domain() {
    let food_truck = FoodTruck {
        locationid: "".to_string(),
        applicant: "".to_string(),
        facility_type: "".to_string(),
        cnn: "".to_string(),
        location_description: "".to_string(),
        address: "".to_string(),
        block_lot: "".to_string(),
        block: "".to_string(),
        lot: "".to_string(),
        permit: "".to_string(),
        status: "".to_string(),
        food_items: "".to_string(),
        x: "".to_string(),
        y: "".to_string(),
        latitude: "".to_string(),
        longitude: "".to_string(),
        schedule: "".to_string(),
        days_hours: "".to_string(),
        noi_sent: "".to_string(),
        approved: "".to_string(),
        received: "".to_string(),
        prior_permit: "".to_string(),
        expiration_date: "".to_string(),
        location: "".to_string(),
        fire_prevention_districts: "".to_string(),
        police_districts: "".to_string(),
        supervisor_districts: "".to_string(),
        zip_codes: "".to_string(),
        neighborhoods_old: "".to_string(),
    };
    // 序列化到JSON
    let json_string = serde_json::to_string(&food_truck).unwrap();
    println!("JSON: {}", json_string);
    // 从JSON反序列化
    let deserialized_food_truck: FoodTruck = serde_json::from_str(&json_string).unwrap();
    println!("Deserialized FoodTruck: {:?}", deserialized_food_truck);

    assert_ne!(
        deserialized_food_truck.location,
        ""
    );
}
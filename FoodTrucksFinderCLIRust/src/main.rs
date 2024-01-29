use clap::{App, Arg};

#[tokio::main]
async fn main() {
    let food_truck = FoodTruck { };
    // 序列化到JSON
    let json_string = serde_json::to_string(&food_truck).unwrap();
    println!("JSON: {}", json_string);
    // 从JSON反序列化
    let deserialized_food_truck: FoodTruck = serde_json::from_str(&json_string).unwrap();
    println!("Deserialized FoodTruck: {:?}", deserialized_food_truck);


    let csv_path = matches.value_of("csv_path").unwrap_or("food_trucks.csv");
    // 下载CSV数据
    let client = reqwest::Client::new();
    let response = client.get(&format!("https://data.sfgov.org/api/views/rqzj-sfat/rows.csv"))
        .header("Accept", "text/csv")
        .send()?;
    // 写入CSV数据到文件
    let mut file = fs::File::create(csv_path)?;
    file.write_all(&response.bytes().unwrap())?;
    // 解析CSV数据
    let csv_data = csv::Reader::from_path(csv_path)?;
    for result in csv_data.deserialize() {
        let food_truck: FoodTruck = result?;
        // 处理每一个food_truck
        println!("{:?}", food_truck);
    }

    let matches = App::new("CLIFoodTrucksFinderRust")
    .version("1.0")
    .about("Food trucks finder in San Francisco")
    .arg(Arg::with_name("filter_keyword")
         .short("k")
         .long("keyword")
         .takes_value(true)
         .help("Filter keyword for LocationID, Applicant, LocationDescription, Address"))
    .get_matches();

`    // let filter_keyword = matches.value_of("filter_keyword").unwrap_or_else(|| "");
    // // 调用fetch_food_trucks获取数据，并过滤
    // let food_trucks = fetch_food_trucks().await;
    // // 处理并打印过滤后的数据
    // match food_trucks {
    //     Ok(trucks);
    // }

}


async fn fetch_food_trucks() -> Result<Vec<FoodTruck>, reqwest::Error> {
    let client = reqwest::Client::new();
    let response = client.get("https://data.sfgov.org/api/views/rqzj-sfat/rows.csv")
        .send().await?;
    let body = response.bytes().await?;
    // 使用csv解析库（例如csv-parse）来解析CSV数据
    // 然后将每一行解析为FoodTruck结构体
    let food_trucks = csv::Reader::from_reader(body.as_ref())
        .deserialize()
        .map(|result| result.map_err(|e| e.into_inner()))
        .collect::<Vec<_>>();
    Ok(food_trucks)
}
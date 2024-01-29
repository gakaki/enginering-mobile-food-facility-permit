// mod entity;
// mod main_test;
//
// use std::str::{Bytes, FromStr};
// use clap::{App, Arg};
// use crate::entity::FoodTruck::*;
// use reqwest::{Error, Client};
//
// fn parse_csv_to_structs<T: FromStr>(body: &[u8]) -> Result<Vec<T>, Error>
//     where
//         T: serde::de::DeserializeOwned,
//         <T as FromStr>::Err: std::error::Error + Send + Sync + 'static,
// {
//     let mut reader = csv::Reader::from_reader(body);
//     let mut records = Vec::new();
//     for result in reader.deserialize() {
//         let record: T = result?;
//         records.push(record);
//     }
//     Ok(records)
// }
//
// // Specify that the function returns a Future that resolves to a Result
// pub async fn request_csv() -> Result<Vec<u8>, Error> {
//     let client = Client::new();
//     let response = client.get("https://data.sfgov.org/api/views/rqzj-sfat/rows.csv")
//         .send().await?;
//     let body = response.bytes().await?;
//     Ok(Vec::from(body))
// }
// pub async fn fetch_food_trucks() -> Result<Vec<FoodTruck>, reqwest::Error> {
//     let client = reqwest::Client::new();
//     let response = client.get("https://data.sfgov.org/api/views/rqzj-sfat/rows.csv")
//         .send().await?;
//     let body = response.bytes().await?;
//     // 使用csv解析库（例如csv-parse）来解析CSV数据
//     // 然后将每一行解析为FoodTruck结构体
//     let food_trucks = parse_csv_to_structs::<FoodTruck>(body.as_ref());
//     match food_trucks {
//         Ok(food_trucks) => {
//             println!("{:?}", food_trucks);
//             // Work with the food_trucks vector
//         }
//         Err(e) => eprintln!("Error parsing CSV: {}", e),
//     }
//
//     // Assuming `food_trucks` is a `Vec<Result<FoodTruck, Error>>`
//     let mut food_trucks: Vec<FoodTruck> = Vec::new();
//     for truck_result in food_trucks {
//         match truck_result {
//             Ok(truck) => food_trucks.push(truck),
//             Err(e) => {
//                 // Handle the error appropriately
//                 println!("Error processing food truck: {}", e);
//             }
//         }
//     }
//     Ok(food_trucks)
// }
// #[tokio::main]
// async fn main() {

//
//     let csv_path = matches.value_of("csv_path").unwrap_or("food_trucks.csv");
//     // 下载CSV数据
//     let client = reqwest::Client::new();
//     let response = client.get(&format!("https://data.sfgov.org/api/views/rqzj-sfat/rows.csv"))
//         .header("Accept", "text/csv")
//         .send()?;
//     // 写入CSV数据到文件
//     let mut file = fs::File::create(csv_path)?;
//     file.write_all(&response.bytes().unwrap())?;
//     // 解析CSV数据
//     let csv_data = csv::Reader::from_path(csv_path)?;
//     for result in csv_data.deserialize() {
//         let food_truck: FoodTruck = result?;
//         // 处理每一个food_truck
//         println!("{:?}", food_truck);
//     }
//
//     let matches = App::new("CLIFoodTrucksFinderRust")
//     .version("1.0")
//     .about("Food trucks finder in San Francisco")
//     .arg(Arg::with_name("filter_keyword")
//          .short("k")
//          .long("keyword")
//          .takes_value(true)
//          .help("Filter keyword for LocationID, Applicant, LocationDescription, Address"))
//     .get_matches();
//
// `    // let filter_keyword = matches.value_of("filter_keyword").unwrap_or_else(|| "");
//     // // 调用fetch_food_trucks获取数据，并过滤
//     // let food_trucks = fetch_food_trucks().await;
//     // // 处理并打印过滤后的数据
//     // match food_trucks {
//     //     Ok(trucks);
//     // }

// }


mod entity;
use clap::{App, Arg};
use std::fs;
use std::path::{Path, PathBuf};
use crate::entity::food_truck::FoodTruck;
use reqwest::{ Client};
use csv::Reader;
use std::io::{ Read}; // Bring Read and Write traits into scope
use std::fs::{File, metadata};

// Specify that the function returns a Future that resolves to a Result
pub async fn request_csv() -> Result<Vec<FoodTruck>, Box<dyn std::error::Error>> {

    let file_path = Path::new("usa_sf_food_trucks_data.csv");
    if let Ok(metadata) = metadata(file_path) {
        if metadata.is_file() {
            // File exists, read and parse it
            let mut file = File::open(file_path)?;
            let mut csv_data = String::new();
            file.read_to_string(&mut csv_data)?;
            let mut reader = Reader::from_reader(csv_data.as_bytes());
            let food_trucks = get_food_trucks_from_reader(&mut reader)?;
            return Ok(food_trucks);
        }
    }

    let client = Client::new();
    let response = client.get("https://data.sfgov.org/api/views/rqzj-sfat/rows.csv")
        .send().await?;

    println!("read food trucks from network");

    if response.status().is_success() {
        response.error_for_status_ref()?;
        let bytes = response.bytes().await?;
        let mut reader = Reader::from_reader(bytes.as_ref());
        let food_trucks = get_food_trucks_from_reader(&mut reader)?;

        let new_file_path = PathBuf::from("usa_sf_food_trucks_data.csv");
        fs::write(new_file_path, bytes)?;

        Ok(food_trucks)
    } else {
        Err(Box::from(response.status().to_string()))
    }
}

fn get_food_trucks_from_reader(reader: &mut Reader<&[u8]>) -> Result<Vec<FoodTruck>, Box<dyn std::error::Error>> {
    let mut food_trucks = Vec::new();
    for result in reader.deserialize() {
        let record: FoodTruck = result?;
        food_trucks.push(record);
    }
    Ok(food_trucks)
}

#[tokio::main]
async fn main() {
    match request_csv().await {
        Ok(food_trucks) => {
            println!("food trucks length is {:?}", food_trucks.len());


            let matches = clap::App::new("filter_food_trucks")
                .version("1.0")
                .about("Filter FoodTrucks based on locationid, Applicant, and location_description.")
                .arg(Arg::with_name("locationid")
                    .short("l")
                    .long("locationid")
                    .help("Filter by locationid")
                    .takes_value(true))
                .get_matches();


        }
        Err(e) => eprintln!("Error processing CSV: {}", e),
    }
}

mod entity;
use clap::{Arg};
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


            // Filter the FoodTrucks based on command-line arguments
            let matches = clap::App::new("filter_food_trucks")
                .version("1.0")
                .about("Filter FoodTrucks keyword based on locationid, Applicant, or location_description.")
                .arg(Arg::new("keyword")
                    .help("CSV data to filter")
                    .takes_value(true))
                .get_matches();

            let keyword = matches.value_of("keyword").map(|s| s.to_string());
            if let Some(keyword) = keyword {
                if keyword.is_empty() {

                }else{
                    println!("input args is : {:?}", keyword);
                    let r = food_trucks
                        .clone()
                        .into_iter()
                        .filter(|item| return
                            item.location.to_lowercase().contains(&keyword) ||
                                item.applicant.to_lowercase().contains(&keyword) ||
                                item.location_description.to_lowercase().contains(&keyword)
                        ).collect::<Vec<_>>();
                    println!("filter rows length are : {:?}", r.len());

                    r.iter().for_each(|food_truck: &FoodTruck| {
                        println!("Value: {}-{}-{}", food_truck.location,food_truck.applicant, food_truck.location_description );
                    });
                }
            }
        }
        Err(e) => eprintln!("Error processing CSV: {}", e),
    }
}

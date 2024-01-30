# Run Result

    /Users/macbook/.cargo/bin/cargo run --color=always --package cli --bin cli hot
    Finished dev [unoptimized + debuginfo] target(s) in 0.06s
    Running `target/debug/cli hot`
    food trucks length is 481
    input args is : "hot"
    filter rows length are : 22
    Value: (37.76008693198698, -122.41880648110114)-Leo's Hot Dogs-MISSION ST: 19TH ST to 20TH ST (2300 - 2399)
    Value: (37.78511092044776, -122.40742266827704)-The New York Frankfurter Co. of CA, Inc. DBA: Annie's Hot Dogs-MARKET ST: 04TH ST \ ELLIS ST \ STOCKTON ST to POWELL ST (800 - 890) -- NORTH --
    Value: (37.786856111883054, -122.40689189299718)-The New York Frankfurter Co. of CA, Inc. DBA: Annie's Hot Dogs-STOCKTON ST: OFARRELL ST to GEARY ST (100 - 199)
    Value: (37.7879142297241, -122.40527052309521)-San Francisco Carts & Concessions, Inc. DBA Stanley's Steamers Hot Dogs-GEARY ST: GRANT AVE to STOCKTON ST (100 - 199)
    Value: (37.785469746489916, -122.40661576107294)-The New York Frankfurter Co. of CA, Inc. DBA: Annie's Hot Dogs-STOCKTON ST: 04TH ST \ ELLIS ST \ MARKET ST intersection
    Value: (37.787242602596706, -122.40687973821338)-San Francisco Carts & Concessions, Inc. DBA Stanley's Steamers Hot Dogs-GEARY ST: STOCKTON ST to POWELL ST (200 - 299)
    Value: (37.78891014957045, -122.40719603412224)-San Francisco Carts & Concessions, Inc. DBA Stanley's Steamers Hot Dogs-POST ST: STOCKTON ST to POWELL ST (300 - 399)
    Value: (37.788877117266644, -122.42009102003807)-The Huge Hotdog Concession-POLK ST: BUSH ST to AUSTIN ST \ FRANK NORRIS ST (1300 - 1329)
    Value: (37.76735502136977, -122.42051791382852)-Julie's Hot Dogs-MISSION ST: 14TH ST to 15TH ST (1800 - 1899)
    Value: (37.76317461963382, -122.41900951477052)-Julie's Hot Dogs-MISSION ST: 17TH ST to CLARION ALY (2100 - 2131)
    Value: (37.746948295513135, -122.40339096031298)-Eli's Hot Dogs-JERROLD AVE: BARNEVELD AVE to JERROLD AVE (2351 - 2369)
    Value: (37.75924028515023, -122.4185441711166)-Julie's Hot Dogs-MISSION ST: 19TH ST to 20TH ST (2300 - 2399)
    Value: (37.764745350719494, -122.41656213947006)-Santana ESG, Inc.-SHOTWELL ST: 16TH ST to 17TH ST (200 - 299)
    Value: (37.78717629174164, -122.40739807595821)-San Francisco Carts & Concessions, Inc. DBA Stanley's Steamers Hot Dogs-GEARY ST: STOCKTON ST to POWELL ST (200 - 299)
    Value: (37.78862121908618, -122.40806890640651)-San Francisco Carts & Concessions, Inc. DBA Stanley's Steamers Hot Dogs-POST ST: STOCKTON ST to POWELL ST (300 - 399)
    Value: (37.75887999201479, -122.41937920298372)-Julie's Hot Dogs-MISSION ST: 19TH ST to 20TH ST (2300 - 2399)
    Value: (0.0, 0.0)-San Francisco Carts & Concessions, Inc. DBA Stanley's Steamers Hot Dogs-POST ST: STOCKTON ST to POWELL ST (300 - 399)
    Value: (37.787755298967475, -122.40878221396503)-San Francisco Carts & Concessions, Inc. DBA Stanley's Steamers Hot Dogs-POWELL ST: GEARY ST to POST ST (300 - 399)
    Value: (0.0, 0.0)-San Francisco Carts & Concessions, Inc. DBA Stanley's Steamers Hot Dogs-GEARY ST: STOCKTON ST to POWELL ST (200 - 299)
    Value: (37.74793093611294, -122.41519315390947)-Brazuca Grill-SHOTWELL ST: 15TH ST to 16TH ST (100 - 199)
    Value: (37.76785244271805, -122.41610489253189)-El Alambre-14TH ST: FOLSOM ST to SHOTWELL ST (100 - 150)
    Value: (37.76785244271805, -122.41610489253189)-El Alambre-SHOTWELL ST: 14TH ST to 15TH ST (1 - 99)
    
    Process finished with exit code 0





## chatgpt


改变一下逻辑 如果能够读取到本地的"usa_sf_food_trucks_data.csv" 那么读取该文件解析到vec<Foodtrucks> 中,如果没有那么请求"https://data.sfgov.org/api/views/rqzj-sfat/rows.csv" 并写入文件,继续解析到vec<Foodtrucks> 
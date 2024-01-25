# How To Use

[Install Go](https://go.dev/dl/)

```go
go build
chmod +x CLIFoodTrucksFinder
./CLIFoodTrucksFinder hot
```
will output: (hot is use to filter Applicant and LocationDescription with "hot")
```go
Input string: hot
Using existing CSV file.
Filtered Data:
[locationid] 1657803,[Applicant] is San Francisco Carts & Concessions, Inc. DBA Stanley's Steamers Hot Dogs,[LocationDescription] is GEARY ST: GRANT AVE to STOCKTON ST (100 - 199)
[locationid] 1750909,[Applicant] is The New York Frankfurter Co. of CA, Inc. DBA: Annie's Hot Dogs,[LocationDescription] is STOCKTON ST: 04TH ST \ ELLIS ST \ MARKET ST intersection
[locationid] 1657804,[Applicant] is San Francisco Carts & Concessions, Inc. DBA Stanley's Steamers Hot Dogs,[LocationDescription] is GEARY ST: STOCKTON ST to POWELL ST (200 - 299)
[locationid] 1657805,[Applicant] is San Francisco Carts & Concessions, Inc. DBA Stanley's Steamers Hot Dogs,[LocationDescription] is POST ST: STOCKTON ST to POWELL ST (300 - 399)
[locationid] 946047,[Applicant] is The Huge Hotdog Concession,[LocationDescription] is POLK ST: BUSH ST to AUSTIN ST \ FRANK NORRIS ST (1300 - 1329)
[locationid] 1034228,[Applicant] is Julie's Hot Dogs,[LocationDescription] is MISSION ST: 14TH ST to 15TH ST (1800 - 1899)
[locationid] 1039786,[Applicant] is Julie's Hot Dogs,[LocationDescription] is MISSION ST: 17TH ST to CLARION ALY (2100 - 2131)
[locationid] 948140,[Applicant] is Eli's Hot Dogs,[LocationDescription] is JERROLD AVE: BARNEVELD AVE to JERROLD AVE (2351 - 2369)
[locationid] 1163405,[Applicant] is Julie's Hot Dogs,[LocationDescription] is MISSION ST: 19TH ST to 20TH ST (2300 - 2399)
[locationid] 953198,[Applicant] is Santana ESG, Inc.,[LocationDescription] is SHOTWELL ST: 16TH ST to 17TH ST (200 - 299)
[locationid] 1657809,[Applicant] is San Francisco Carts & Concessions, Inc. DBA Stanley's Steamers Hot Dogs,[LocationDescription] is GEARY ST: STOCKTON ST to POWELL ST (200 - 299)
[locationid] 1750911,[Applicant] is The New York Frankfurter Co. of CA, Inc. DBA: Annie's Hot Dogs,[LocationDescription] is STOCKTON ST: OFARRELL ST to GEARY ST (100 - 199)
[locationid] 1750910,[Applicant] is The New York Frankfurter Co. of CA, Inc. DBA: Annie's Hot Dogs,[LocationDescription] is MARKET ST: 04TH ST \ ELLIS ST \ STOCKTON ST to POWELL ST (800 - 890) -- NORTH --
[locationid] 1265606,[Applicant] is San Francisco Carts & Concessions, Inc. DBA Stanley's Steamers Hot Dogs,[LocationDescription] is POST ST: STOCKTON ST to POWELL ST (300 - 399)
[locationid] 1010174,[Applicant] is Julie's Hot Dogs,[LocationDescription] is MISSION ST: 19TH ST to 20TH ST (2300 - 2399)
[locationid] 1265597,[Applicant] is San Francisco Carts & Concessions, Inc. DBA Stanley's Steamers Hot Dogs,[LocationDescription] is POST ST: STOCKTON ST to POWELL ST (300 - 399)
[locationid] 1265569,[Applicant] is San Francisco Carts & Concessions, Inc. DBA Stanley's Steamers Hot Dogs,[LocationDescription] is POWELL ST: GEARY ST to POST ST (300 - 399)
[locationid] 1265607,[Applicant] is San Francisco Carts & Concessions, Inc. DBA Stanley's Steamers Hot Dogs,[LocationDescription] is GEARY ST: STOCKTON ST to POWELL ST (200 - 299)
[locationid] 1723876,[Applicant] is Brazuca Grill,[LocationDescription] is SHOTWELL ST: 15TH ST to 16TH ST (100 - 199)
[locationid] 1735285,[Applicant] is El Alambre,[LocationDescription] is 14TH ST: FOLSOM ST to SHOTWELL ST (100 - 150)
[locationid] 1735284,[Applicant] is El Alambre,[LocationDescription] is SHOTWELL ST: 14TH ST to 15TH ST (1 - 99)
Exiting.

....
```


#### chatgpt usage to coding golang cli

使用golang cobra 和 resty库 编写 gui程序, 
功能为获取如下地址的csv,
https://data.sfgov.org/api/views/rqzj-sfat/rows.csv , 
该csv的对应的golang struct 
字段为locationid,Applicant,FacilityType,cnn,LocationDescription,Address,
blocklot,block,lot,permit,Status,FoodItems,X,Y,
Latitude,Longitude,Schedule,dayshours,NOISent,
Approved,Received,PriorPermit,ExpirationDate,
Location,Fire Prevention Districts,
Police Districts,Supervisor Districts,
Zip Codes,Neighborhoods (old) , 
命令行的第一个参数为name 用来过滤 
字段Applicant 对应的行数据. 
该程序流程为使用resty 下载csv,
保存csv.若第二次使用命令行先判断本地是否有csv,如果没有下载,
如果有读取. 之后打印过滤过的数据.
打印内容为locationid,cnn ,locationDescriprtion 
注意用表格的方式打印.


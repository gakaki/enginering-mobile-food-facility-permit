## enginering-mobile-food-facility-permit

![Description](images/main.png|250x250)
![CLI](images/cli.png|250x250)


### Docker Compose

use below docker-compose will startup redis and meilisearch (search engine)

```go

cd backend
docker-compose up -d 
docker ps
    
```
download jdk21 suggest using SDKMan install

use idea to import , than run spring boot application

###  [Backend] Java Spring Boot 3.2 + JDK21

postman 3 apis
```
// fetch the http csv to redis geohash and meilisearch
curl --request GET --url http://localhost:9888/api/trucks

// use redis geohash find near by food trucks
curl --request GET \
   --url 'http://127.0.0.1:9888/api/trucks/nearby?latitude=37.76008693198698&longitude=-122.41880648110114&radius=1'

// use meilisearch for full search every field
curl --request GET \
  --url 'http://127.0.0.1:9888/api/trucks/search?keyword=DRUMM'
```

### [Frontend] React Vite Tailwind HereMaps

```javascript
cd frontend
pnpm i
pnpm run dev
pnpm run build
```


### [Cli] GoLang

```javascript
cd FoodTrucksFinderCLIGolang
go build
./CLIFoodTrucksFinder hot 
```
## Docker-Compose 使用
### 设置 mac orbstack docker hub镜像加速
来自[阿里云](https://cr.console.aliyun.com/cn-hangzhou/instances/mirrors)
{
"registry-mirrors": ["https://xi1udf2u.mirror.aliyuncs.com"]
}
###
docker-compose up -d elasticsearch kibana

Kibana server is not ready yet
等1分钟
http://localhost:5601/app/home#/

## GitHub Action for GraalVM and Maven Dependencies Cache




## Redis库 Redission 配置

https://github.com/redisson/redisson/wiki/2.-%E9%85%8D%E7%BD%AE%E6%96%B9%E6%B3%95#23-%E5%B8%B8%E7%94%A8%E8%AE%BE%E7%BD%AE

## csv show
https://data.sfgov.org/Economy-and-Community/Mobile-Food-Facility-Permit/rqzj-sfat/data

```json

db.places.find(
   {
     location:
       { $near :
          {
            $geometry: { type: "Point",  coordinates: [ -73.9667, 40.78 ] },
            $minDistance: 1000,
            $maxDistance: 5000
          }
       }
   }
)


db.legacy2d.find(
{ location : { $near : [ -73.9667, 40.78 ], $maxDistance: 0.10 } }
)


```



##
redission 配置
redisson常用配置
https://github.com/redisson/redisson/wiki/2.-%E9%85%8D%E7%BD%AE%E6%96%B9%E6%B3%95#23-%E5%B8%B8%E7%94%A8%E8%AE%BE%E7%BD%AE


## csv show
https://data.sfgov.org/Economy-and-Community/Mobile-Food-Facility-Permit/rqzj-sfat/data


有道开源 RAG 引擎 QAnything 版本更新啦
https://www.v2ex.com/t/1009470#reply20


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



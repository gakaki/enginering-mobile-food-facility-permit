import React, { useEffect, useRef, useState } from 'react';
import H from '@here/maps-api-for-javascript';
import ngeohash from 'ngeohash';
import { FoodTruck } from './enitity';
import { useRequestProcessor } from './api/api';
import axiosClient from './api/axios';


let currentPos          =  { lat: 37.76008693198698, lng: -122.41880648110114 }
let markers_food_trucks = []
let items               = []
const radius            = 1
let markerMe            = null
let ui                  = null
const apikey            = "E5gZPRjXBNxWkGPfJUZZ7UKLeNfTx57S8iH1ZeTK4qo";

const MyMap: React.FC = function( props )  {
  
  const mapRef = useRef(null);
  const map = useRef(null);
  const platform = useRef(null)
  const [message,setMessage] = useState("")
  

  const { query } = useRequestProcessor();

  const trucks_near_by = function() {

    let requestPrams = { latitude: currentPos.lat, longitude: currentPos.lng, radius: radius };

    return axiosClient.get('/nearby',{
      params: requestPrams,
    }).then((res) => { 
        const d = res.data.data
        items = d;
        return d
    })
  }
  let { status, data, error, refetch} = query(
    ['trucks_near_by'],
    function(){ return trucks_near_by()},
    { refetchOnWindowFocus: false,
      enabled: false  }
  );

  let addMaker = function()  {

    // let near_points = nearbyPoints(points,radius,currentPos)

    setMessage(`Food Trucks Near By ${radius}km are ${items.length} count. {${currentPos.lat},${currentPos.lng}}` )

    items.map( item => {
      if (item){
        const marker = new H.map.Marker({lat:item.latitude, lng:item.longitude});
        map.current.addObject(marker);
        markers_food_trucks.push(marker)

        // var bubble = new H.ui.InfoBubble({lat:item.latitude, lng:item.longitude}, {
        //   content: `<div>${item.locationDescription}</div>`  
        // });
        // ui.addBubble(bubble);

      }else{
        // console.log("the point is below and equal zero")
      }
    })
  }


  // function calculateDistance(lat1, lon1, lat2, lon2) {
  //   const R = 6371; // 地球半径，单位为千米
  //   const dLat = deg2rad(lat2 - lat1);
  //   const dLon = deg2rad(lon2 - lon1);
  //   const a =
  //       Math.sin(dLat / 2) * Math.sin(dLat / 2) +
  //       Math.cos(deg2rad(lat1)) * Math.cos(deg2rad(lat2)) *
  //       Math.sin(dLon / 2) * Math.sin(dLon / 2);
  //   const c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));
  //   const d = R * c; // 距离，单位为千米
  //   return d;
  // }
  // function deg2rad(deg) {
  //     return deg * (Math.PI / 180);
  // }

  // const nearbyPoints = (points,radius = 3,center) => {
  //   // radius = 3; // 半径，单位为千米
  //   points.filter(point => {
  //     const distance = calculateDistance(center.lat, center.lng, point.lat, point.lng);
  //     return distance <= radius;
  //   }); 
  //   return points;
  // }
  const nearbyPoints = (points,radius,center) => {

    // radius半径，单位为千米
    // 计算中心点的Geohash
    const centerGeohash = ngeohash.encode(center.lat, center.lng);
    // 确定覆盖3公里半径所需的Geohash精度
    // 注意：Geohash的精度与覆盖的区域大小有关，这里需要根据实际情况调整
    // const precision = ngeohash.calculatePrecison(radius);
    const precision = 6;

    // 过滤出在3公里内的坐标点
    points = points.filter(point => {
        const pointGeohash = ngeohash.encode(point.lat, point.lng, precision);
        return centerGeohash.startsWith(pointGeohash) || pointGeohash.startsWith(centerGeohash);
    });

    return points;
  }

  useEffect(
    () => {
      if (!map.current) {
        platform.current = new H.service.Platform({ apikey });
        
        let defaultLayers = platform.current.createDefaultLayers();
        const newMap = new H.Map(mapRef.current, 
          defaultLayers.vector.normal.map,{
            pixelRatio: window.devicePixelRatio  || 1,
            center: {
              lat: 64.144,
              lng: -21.94,
            },
            zoom: 14,
        });
      
        // add a resize listener to make sure that the map occupies the whole container
        window.addEventListener('resize', () => newMap.getViewPort().resize());

        // Add panning and zooming behavior to the map
        const behavior = new H.mapevents.Behavior(
          new H.mapevents.MapEvents(newMap)
        );
        // ui =  H.ui.UI.createDefault(newMap, defaultLayers, 'en-US');
        
        map.current = newMap;
        
        
       
    
        map.current.setCenter(currentPos);
        map.current.setZoom(15);
    
        //Create the svg mark-up
        const svgMarkup = '<svg  width="70" height="24" xmlns="http://www.w3.org/2000/svg">' +
        '<rect stroke="black" fill="${FILL}" x="1" y="1" width="70" height="22" />' +
        '<text x="3" y="18" font-size="12pt" font-family="Arial" font-weight="bold" ' +
        'text-anchor="left" fill="${STROKE}" >Drag me</text></svg>';
    
        // Add the first marker
        const bearsIcon = new H.map.Icon(svgMarkup.replace('${FILL}', 'blue').replace('${STROKE}', 'red'));
        // @ts-ignore
        const bearsMarker = new H.map.Marker(currentPos,{icon: bearsIcon,volatility: false});
        bearsMarker.draggable = true;
        map.current.addObject(bearsMarker)
        let markerMe            = bearsMarker
      

        map.current.addEventListener('dragstart', function(ev) {
          var target = ev.target,
              pointer = ev.currentPointer;
          if (target instanceof H.map.Marker) {
            var targetPosition = map.current.geoToScreen(target.getGeometry());
            target['offset'] = new H.math.Point(pointer.viewportX - targetPosition.x, pointer.viewportY - targetPosition.y);
            behavior.disable();
          }
        }, false);
      
      
        // re-enable the default draggability of the underlying map
        // when dragging has completed
        map.current.addEventListener('dragend', function(ev) {
          var target = ev.target;
          if (target instanceof H.map.Marker) {
            behavior.enable();
            
            // console.log("refetch before is",currentPos)
            refetch().then( ()=> {
              addMaker()
            })
            
            if (map.current && markers_food_trucks.length > 0){
              // console.log(map.current,markers_food_trucks.length,markers_food_trucks)
              map.current.removeObjects(markers_food_trucks)
              markers_food_trucks = []

            }
          }
        }, false);
      
        // Listen to the drag event and move the position of the marker
        // as necessary
        map.current.addEventListener('drag', function(ev) {
          var target = ev.target,
              pointer = ev.currentPointer;
          if (target instanceof H.map.Marker) {
            currentPos = map.current.screenToGeo(pointer.viewportX - target['offset'].x, pointer.viewportY - target['offset'].y)
            // console.log("current pos is",currentPos)
            target.setGeometry(currentPos);

          }
        }, false);
    

        // map.current.addEventListener('click', function(ev) {
        //   console.log(ev)
        //   var target = ev.target,
        //       pointer = ev.currentPointer;

        //   currentPos = map.current.screenToGeo(pointer.viewportX - target['offset'].x, pointer.viewportY - target['offset'].y)
        //   markerMe.setGeometry(currentPos);
        //   refetch().then( ()=> {
        //     addMaker()
        //   })


        // }, false);

        refetch().then( ()=> {
          addMaker()
        })
      }
    },
    [apikey]
  );

  return <div>
    <div style={ { width: "100%", height: "400px" } } ref={mapRef} />
    <div className='mt-4'>{message}</div>
    
  </div>;
}

export default MyMap;

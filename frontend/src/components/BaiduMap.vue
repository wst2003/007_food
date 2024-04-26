<template>
    <div class="uppermap" >
        <div @click=" areaSelect " style="display: flex;align-items: center;">
            <svg xmlns="http://www.w3.org/2000/svg" width="15" height="19" viewBox="0 0 15 19" fill="none">
                <path fill-rule="evenodd" clip-rule="evenodd" d="M6.46687 18.3965C7.13969 19.1026 8.21182 19.0822 8.86155 18.3511L12.8052 13.9115L13.01 13.6799C14.2787 12.1881 15 10.2305 15 8.15036C15 3.64904 11.6421 0 7.5 0C3.35786 0 0 3.64904 0 8.15036C0 10.4029 0.846031 12.5108 2.31367 14.038L6.46687 18.3965ZM7.49965 11.3054C9.10306 11.3054 10.4029 9.89284 10.4029 8.1504C10.4029 6.40795 9.10306 4.99542 7.49965 4.99542C5.89624 4.99542 4.59643 6.40795 4.59643 8.1504C4.59643 9.89284 5.89624 11.3054 7.49965 11.3054Z" fill="#93B090"/>
            </svg>
            <div class="locationpicker" >{{ areastr }}</div>
        </div>
        <!-- <img height="19" src="./assets/catuppermap.png" /> -->
    </div>
    <!-- <div @click=" areaSelect ">{{ areastr }}</div> -->
    <div style="position: relative;">
        <img src="../assets/cat.svg" @click="toggleHeight" style="position:absolute;right: 0;top:0;z-index: 100;transform:  translateX(-30%) translateY(-100%);"/>
        <!-- <div id="baidumap" :style="{ height: currentHeight}" @click="toggleHeight"></div>  -->
        <div id="baidumap" :style="{ height: currentHeight}"></div> 
    </div>
</template>
<script setup>
 /* eslint-disable */ 
import { onMounted ,reactive,ref} from 'vue';
import BaiduMap from 'BaiduMap'
import globalData from '../global.js'
import axios from 'axios';
import qs from 'qs'

const BaseUrl = globalData.BaseUrl
/*
    地区切换模块
*/
import LcCity from 'lc-city-selector';

var areastr=ref("选择地区");
var City = new LcCity({
    confirm: function (data) {
        console.log(data);  // data就是选中的地区，一个拼接好的字符串
        
        var arr=data.split(' ')
        //arr.shift()
        areastr.value=arr.join(' | ').slice(0, -3)
        rezoom(areastr.value)
    }
});

function areaSelect(){
    City.show();
}

function rezoom(replacestr){
    var myGeo = new BMapGL.Geocoder();
    myGeo.getPoint(replacestr, function(point){
            if(point){
                map.centerAndZoom(point, 16);
                map.addOverlay(new BMapGL.Marker(point, {title: replacestr}))
                // 将坐标与地址设置到全局变量
                globalData.userPosition.setCoordination(point.lat,point.lng)
                globalData.userPosition.address= replacestr;

            }else{
                alert('您选择的地址没有解析到结果！');
            }
        }, '北京市')
}
/*
    地图模块
*/
var map=null;//地图对象
var currentPoint=null; // 当前定位信息,BMapPoint

var mkList=[]
var calcList=[]
var markedPoint=[]

function sto_info(sto_obj){
    this.sto_name=sto_obj.sto_name
    this.sto_address=sto_obj.user_address
    this.sto_latitude=sto_obj.sto_latitude
    this.sto_longitude=sto_obj.sto_longitude
    this.sto_rating=sto_obj.sto_rating
    this.sto_logo=sto_obj.sto_logo
    this.introduction=sto_obj.sto_introduction
}

const sto_arr=reactive([])

onMounted(()=>{
    var geolocation = new qq.maps.Geolocation("7WQBZ-GJ2K5-V2PID-IBVSK-6FHK7-4IFXU", "mapqq");
    // 调用QQ地图API，获取位置
    geolocation.getLocation(
        function(position){
            // alert(position.lat+' '+position.lng)
            console.log(position)
            var {lng,lat}=qqMapTransBMap(position.lng,position.lat);
            // alert(lat+' '+lng)
            // var {lng,lat}={lng: -74,lat:40.7}
            // alert(lat+' '+lng)
            afterLocation(lat,lng)

        },
        function(){
            alert('定位失败,请检查网络连接')
        },
        {
            timeout:5000
        }
    )
})

// 转换QQ地图坐标到百度地图坐标
function qqMapTransBMap(lng, lat) {
      let x_pi = 3.14159265358979324 * 3000.0 / 180.0;
      let x = lng;
      let y = lat;
      let z = Math.sqrt(x * x + y * y) + 0.00002 * Math.sin(y * x_pi);
      let theta = Math.atan2(y, x) + 0.000003 * Math.cos(x * x_pi);
      let lngs = z * Math.cos(theta) + 0.0065;
      let lats = z * Math.sin(theta) + 0.006;
     
      return {
          lng: lngs,
          lat: lats 
      } 
}

function initalizatin(lat,lng){
    //初始化
    map = new BaiduMap.Map("baidumap");
    globalData.mapObj.map=map
    let point = new BaiduMap.Point(lng, lat); // 百度BD09坐标
    map.centerAndZoom(point, 15); // 地图实例化
    map.enableScrollWheelZoom(true);     //开启鼠标滚轮缩放
    map.addControl(new BaiduMap.ScaleControl());// 添加比例尺控件
    map.addControl(new BaiduMap.ZoomControl());// 添加缩放控件
    //map.addControl(new BaiduMap.LocationControl());// 添加定位控件
    var mk = new BaiduMap.Marker(point);
    map.addOverlay(mk);
    var circle = new BMapGL.Circle(point,1000,{strokeColor:"red", strokeWeight:2, strokeOpacity:0.5,fillColor:"blue",fillOpacity:0.5}); //创建圆
    map.addOverlay(circle);
    map.panTo(point);
    currentPoint  = point; // 百度BD09坐标

    // 将坐标与地址设置到全局变量
    globalData.userPosition.setCoordination(lat,lng)
    var geoc = new BMapGL.Geocoder();
    geoc.getLocation(point, function(rs){
        var addComp = rs.addressComponents;
        globalData.userPosition.address= addComp.province + ", " + addComp.city + ", " + addComp.district + ", " + addComp.street + ", " + addComp.streetNumber;
        //arr.shift()
        areastr.value=addComp.province + " | " + addComp.city + " | " + addComp.district
        console.log('全局变量用户地址设置成功：'+globalData.userPosition.address)
    })
}

function computeDistance(){
    var promises = [];
    sto_arr.forEach(function(element) {
        var sto_point=new BaiduMap.Point(element.sto_longitude,element.sto_latitude); 
        var mk = new BaiduMap.Marker(sto_point);
        mkList.push(mk);
        // 添加信息窗口
        var opts={
            width:250,
            height:80,
            title:element.sto_name,
            message:element.sto_name
        }
        map.addOverlay(mk);
        mk.addEventListener("click",()=>{
            var infoWindow = new BMapGL.InfoWindow(element.sto_introduction,opts);
            map.openInfoWindow(infoWindow,sto_point);
        })

        var promise = new Promise((resolve, reject) => {
            globalData.mapObj.walkingRoute(currentPoint.lat,currentPoint.lng,sto_point.lat,sto_point.lng,false, (distance, duration) => {
                //console.log('Distance:', distance, 'Duration:', duration);
                calcList.push([distance,duration])
                if (!distance.includes("公里")){ markedPoint.push(point);}
                else if(distance.includes("公里")){
                    var numberPattern = /\d+/g; // 正则表达式匹配任意数量的数字
                    var result = str.match(numberPattern); // 匹配字符串中的数字
                    if (result) {
                        var number = parseInt(result[0]); // 将匹配到的数字字符串转换为整数
                        // console.log(number); // 输出提取到的数字
                        if (number<3){
                            markedPoint.push(point);
                        }
                    } 
                    // else {
                    //     console.log("未找到数字");
                    // }
                }

                var opts = {
                    width : 200,     // 信息窗口宽度
                    height: 100,     // 信息窗口高度
                    title : element.sto_name , // 信息窗口标题
                    message:element.introduction
                }
                var infoWindow = new BaiduMap.InfoWindow('步行距离'+distance+'，步行时间'+duration, opts);  // 创建信息窗口对象 
                    mk.addEventListener("click", function(){          
                    map.openInfoWindow(infoWindow, point); //开启信息窗口
                }); 
                // 其他处理逻辑
                // 解决Promise
                consolee.log("Promise 结束")
                resolve(); 
            });
        });
        promises.push(promise);
    });
    Promise.all(promises)
    .then(() => {
        // 在这里执行绘制多边形的逻辑，使用markedPoint数组中的点
        // 可以调用一个函数来处理这部分逻辑
        markedPoint.push(currentPoint)
        console.log('需要做标记的点'+markedPoint)
        drawPolygon(markedPoint);
    })
    .catch(error => {
        console.error('Error:', error);
    });
} 

function afterLocation(lat,lng){
    lat=31.289
    lng=121.22
    initalizatin(lat,lng)
    var sto_ids=[]
    axios.get(BaseUrl+'/api/pub/map',{
    params:{
        cur_latitude:lat,
        cur_longitude:lng
    }}).then(res=>{
        console.log('获取的所有商家')
        console.log(res.data)
        sto_ids=res.data.sto_id
        return axios.get(BaseUrl+'/api/sto/informationdetail',{
            params:{
                sto_ID:sto_ids
            },
            paramsSerializer: params => {
                return qs.stringify(params, { indices: false })
            }
        })
    }).then(res=>{
        res.data.forEach(ele=>{
            sto_arr.push(new sto_info(ele))
        })
        console.log('拉取到的所有商家数组：')
        console.log(sto_arr)
    }).then(()=>{
        computeDistance()
    })

}



const drawPolygon=(points)=>{
    // 调用graham凸包算法，建立凸包多边形
    var polygon = new BMapGL.Polygon( grahamScan(points), {
        zIndex: 10,
        fillOpacity: 0.3,
        fillColor:'blue',
        strokeStyle: 'dashed',
        strokeColor: 'red',
        strokeWeight: 8,
        strokeOpacity: 0.3,
    });
    map.addOverlay(polygon);
}

// 骑行规划路线
const ridingRoute=(start,end,flag=false, callback)=>{
    var distance=null;
    var duration=null;
    if(flag==false){
        var riding = new BaiduMap.RidingRoute(map, { 
            renderOptions: { 
                map: null, 
                autoViewport: false 
            },
            onSearchComplete: function (results){
                if (riding.getStatus() != BMAP_STATUS_SUCCESS){console.log('测距失败');return;}
                var plan = results.getPlan(0);
                distance=plan.getDistance(true);
                duration=plan.getDuration(true);
                //console.log('骑行距离'+plan.getDistance(true)+'骑行时间'+plan.getDuration(true)); 
                if (callback && typeof callback === 'function') {
                    callback(distance, duration);
                }
            },
            onPolylinesSet: function(){setTimeout(function(){},"1000");		
            }});
        riding.search(start, end);
    }
}
// 步行规划路线
const walkingRoute=(start,end,flag=false, callback)=>{
    var distance=null;
    var duration=null;
    if(flag==false){
        var walking = new BaiduMap.WalkingRoute(map, { 
            renderOptions: { 
                map: null, 
                autoViewport: false 
            },
            onSearchComplete: function (results){
                if (walking.getStatus() != BMAP_STATUS_SUCCESS){console.log('测距失败');return;}
                var plan = results.getPlan(0);
                distance=plan.getDistance(true);
                duration=plan.getDuration(true);
                //console.log('步行距离'+plan.getDistance(true)+'步行时间'+plan.getDuration(true)); 
                 // 调用回调函数，传递distance和duration
                if (callback && typeof callback === 'function') {
                    callback(distance, duration);
                }
            },
            onPolylinesSet: function(){setTimeout(function(){},"1000");		
        }});
        walking.search(start, end);
    }
}



// 凸包算法
function crossProduct(p1, p2, p3) {
    return (p2.lat - p1.lat) * (p3.lng - p2.lng) - (p2.lng - p1.lng) * (p3.lat - p2.lat);
}

function distance(p1, p2) {
    return (p1.lng - p2.lng) ** 2 + (p1.lat - p2.lat) ** 2;
}

function nextToTop(stack) {
    return stack[stack.length - 2];
}

function compare(p1, p2, p0) {
    let order = crossProduct(p0, p1, p2);
    if (order === 0) {
        return distance(p0, p2) >= distance(p0, p1) ? -1 : 1;
    }
    return order > 0 ? -1 : 1;
}

function grahamScan(points) {
    let n = points.length;
    if (n < 3) return;

    let l = 0;
    for (let i = 1; i < n; i++) {
        if (points[i].lng < points[l].lng) {
            l = i;
        }
    }

    [points[0], points[l]] = [points[l], points[0]];

    let p0 = points[0];
    points.sort((p1, p2) => compare(p1, p2, p0));

    let stack = [];
    stack.push(points[0], points[1], points[2]);

    for (let i = 3; i < n; i++) {
        while (stack.length > 1 && crossProduct(nextToTop(stack), stack[stack.length - 1], points[i]) <= 0) {
            stack.pop();
        }
        stack.push(points[i]);
    }

    return stack;
}

const currentHeight = ref('20vh');// 地图初始高度为20vh
const unfoldtext=ref('展开')
function toggleHeight(e) {
    if(unfoldtext.value=='展开'){
        currentHeight.value = currentHeight.value === '20vh' ? '80vh' : '20vh';
        unfoldtext.value='收回'
    }
    else if(unfoldtext.value=='收回'){
        unfoldtext.value='展开'
        currentHeight.value = currentHeight.value === '20vh' ? '80vh' : '20vh';
    }
}
</script>
<style scoped>

    #baidumap{
        margin: 10px 20px 10px 20px;
        transition: height 0.5s ease-in-out; /* 平滑过渡效果 */
        border-radius: 4px;

    }    
    .locationpicker{
        color: black;
        font-size: 14px; 
        font-family: Source Han Sans SC; 
        font-weight: 500; 
        line-height: 24px; 
        word-wrap: break-word;
        margin-left: 10px;
    }
    .uppermap{
        display: flex;
        position: relative;
        margin: 10px 20px;
        justify-content: space-between;
    }
    .unfoldtextcss{
        color: black;
        font-size: 14px; 
        font-family: Source Han Sans SC; 
        font-weight: 500; 
        line-height: 24px; 
        position: relative;
        right: 10px;
    }
    #baidumap{

    }
</style>
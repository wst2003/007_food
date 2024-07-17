<template>
    <nut-navbar title="出行路径规划" left-show @click-back="returnPage"></nut-navbar>
    <div class="container">
        <div id="baidumap2" ></div>
        <div style="font-size: large;margin-left: 30vw;margin-top: 30px;font-weight: bold; color: #93B090;" v-if="destinations.length==0">今日无配送需求！</div> 
        <div class="info-container" v-else>
            <div class="line1">智能配送规划助手</div>
            <div class="line2">今日还需配送{{destinations.length}}单，已根据要求送达时间和用户位置为您规划最优路线</div>
            <div>
                <span class="line3b">全程总长共计</span>
                <span class="line3g">{{ pathInfo.distance.toFixed(1) }}</span>
                <span class="line3b">公里</span>
            </div>
            <div>
                <span class="line3b">预计配送用时</span>
                <span class="line3g">{{ pathInfo.duration }}</span>
                <span class="line3b">分钟</span>
            </div>
            <div v-if="solutionPath.length>1" style="margin-top:10px">
                <div>路径规划：
                    <div style="color: #93B090;font-weight: 700; ">{{ pathStr }} </div>
                </div>
                <div>当前目的地：<span style="color: #93B090;font-weight: bold; ">{{posi[solutionPath[1]].address}}</span></div>
            </div>

            <!-- <nut-steps :current="2" class="steps" :v-if="cur_index!=null">
                <nut-step :title="destinations[solutionPath[cur_index-1]].address" content="描述信息">1</nut-step>
                <nut-step :title="destinations[solutionPath[cur_index]].address" content="描述信息">2</nut-step>
                <nut-step :title="destinations[solutionPath[cur_index+1]].address" content="描述信息">3</nut-step>
            </nut-steps> -->
        </div>
        
    </div>
</template>

<script setup>
/* eslint-disable */ 
import { onMounted ,reactive,ref} from 'vue';
import qs from 'qs';
import BaiduMap from 'BaiduMap'
import axios from 'axios';
import globalData from "../../global.js"
const BaseUrl = globalData.BaseUrl
import { useRouter } from 'vue-router';
const router=useRouter();
var global_map=null;//地图对象
var current_location={
    latitude:0,
    longitude:0
}; // 当前定位信息
var sto_location={
    address:"",
    latitude:0,
    longitude:0
}
var pathInfo=reactive({
    distance:0,// km
    duration:0,// minute
})
var adjacencyMat=null
const destinations=reactive([])//存储infoCls
const posi=reactive([])// 存储所有点，在destinations的基础上添加商家本身
var solutionPath=reactive([])// 存储posi中的元素序号
const cur_index=ref(null)
const pathStr=ref("")
function infoCls(data){
    console.log(data)
    // this.date=data.ind_creationTime;//yyyy:MM:dd HH:mm:ss 用于排查今日订单
    // this.state=data.ind_state;//0为待送货，1为送货完成
    // this.method=data.delivery_method;//配送方法，1为商家配送
    this.address=data.delivery_address;
    this.latitude=data.delivery_altitude;
    this.longitude=data.delivery_longitude;
}

// 挂载时，获取当前定位
onMounted(()=>{
    var geolocation = new qq.maps.Geolocation("7WQBZ-GJ2K5-V2PID-IBVSK-6FHK7-4IFXU", "mapqq");
    // 调用QQ地图API，获取位置
    geolocation.getLocation(
        function(position){
            console.log('current location：'+position)
            var {lng,lat}=qqMapTransBMap(position.lng,position.lat);
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
function initalization(lat,lng){
    //初始化
    global_map = new BaiduMap.Map("baidumap2");
    globalData.mapObj.map=global_map
    let point = new BaiduMap.Point(lng, lat); // 百度BD09坐标
    global_map.centerAndZoom(point, 15); // 地图实例化
    global_map.enableScrollWheelZoom(true);     //开启鼠标滚轮缩放
    global_map.addControl(new BaiduMap.ScaleControl());// 添加比例尺控件
    global_map.addControl(new BaiduMap.ZoomControl());// 添加缩放控件
    //map.addControl(new BaiduMap.LocationControl());// 添加定位控件
    var mk = new BaiduMap.Marker(point);
    global_map.addOverlay(mk);
    global_map.panTo(point);
    current_location.latitude  = point.lat; // 百度BD09坐标
    current_location.longitude  = point.lng; // 百度BD09坐标

    // 将坐标与地址设置到全局变量
    globalData.userPosition.setCoordination(lat,lng)
    var geoc = new BMapGL.Geocoder();
    geoc.getLocation(point, function(rs){
        var addComp = rs.addressComponents;
        globalData.userPosition.address= addComp.province + ", " + addComp.city + ", " + addComp.district + ", " + addComp.street + ", " + addComp.streetNumber;
       
    })
}
function afterLocation(lat,lng){
    //********** 
    // lat=31.282431
    // lng=121.212268
    // 同济嘉定校区大门坐标，用于测试；正式版需要注释掉
    lat = 37.536;
    lng = 122.068;
    // 山东大学威海校区坐标
    initalization(lat,lng) // 初始化地图与位置信息
    var ind_ids=[]
    console.log('SessionStorage中的id '+sessionStorage.getItem("user_id"))
    axios.get(BaseUrl+'/api/sto/getIndentList', {
        params: {
            // sto_id: 29,
            sto_id: sessionStorage.getItem("user_id"), //To be replaced 
        }
      }).then(res => {
        // 获取该商家的订单列表
        console.log('该商家所有订单:')
        console.log(res.data)
        ind_ids=res.data

        return axios.get(BaseUrl+'/api/cus/getIndById',{
            params: {
                ind_id: ind_ids,
                // ind_id: [1,2,3,4,5],
            },
            paramsSerializer: params => {
                // 这里使用 qs 来序列化参数，`indices: false` 表示不添加索引
                return qs.stringify(params, { arrayFormat: 'repeat' })
            }
        })
      }).then(res =>{
        console.log('订单详情列表：')
        console.log(res.data)
        // 拉取订单详细信息
        // 获取今天的日期，并格式化为 yyyy:MM:dd 格式
        const today = new Date();
        const todayStr = today.getFullYear() + '-' + 
                        ('0' + (today.getMonth() + 1)).slice(-2) + '-' + 
                        ('0' + today.getDate()).slice(-2);
                        console.log(todayStr)
        res.data.forEach(element => {
            if(element.delivery_method==1&&element.ind_state==0&&element.ind_creationTime.startsWith(todayStr)){
                destinations.push(new infoCls(element))
            }
        });
        console.log('今日需配送的订单:')
        console.log(destinations)
        return axios.get(BaseUrl+'/api/sto/informationdetail',{
            params: {
                // sto_ID: 29,
              sto_ID: sessionStorage.getItem("user_id"), //To be replaced 
            }
        })
      }).then(res =>{
        // 获取商家位置
        sto_location.address=res.data[0].user_address
        sto_location.latitude=res.data[0].sto_latitude 
        sto_location.longitude=res.data[0].sto_longitude
        console.log('商家位置:')
        console.log(sto_location)
      }).then(()=>{

        if(destinations.length!=0){
            destinations.forEach(ele=>{
                var mk = new BaiduMap.Marker( new BaiduMap.Point(ele.longitude, ele.latitude));
                global_map.addOverlay(mk);
            })
            Traveller()
        }
      }).catch(err => console.log(err))

    // axios.get('/api/sto/informationdetail',{
    //     params: {
    //         sto_ID: 29,
    //     //  sto_ID: globalData.userInfo.user_id, //To be replaced 
    //     }
    //   }).then(res =>{
    //     // 获取商家位置
    //     sto_location.address=res.data[0].user_address
    //     sto_location.latitude=res.data[0].sto_latitude 
    //     sto_location.longitude=res.data[0].sto_longitude
    //     console.log(sto_location)
    //   }).then(()=>{
    //     // 加偏移，得到测试点
    //     destinations.push(new infoCls({delivery_address:'测试点1',delivery_altitude:sto_location.latitude-0.001,delivery_longitude:sto_location.longitude-0.001}))
    //     destinations.push(new infoCls({delivery_address:'测试点2',delivery_altitude:sto_location.latitude-0.02,delivery_longitude:sto_location.longitude-(-0.01)}))
    //     destinations.push(new infoCls({delivery_address:'测试点3',delivery_altitude:sto_location.latitude-(-0.01),delivery_longitude:sto_location.longitude-0.02}))
    //     destinations.forEach(ele=>{
    //         var mk = new BaiduMap.Marker( new BaiduMap.Point(ele.longitude, ele.latitude));
    //         global_map.addOverlay(mk);
    //     }) 
    //     Traveller()
    //   })

}
function Traveller(){
    console.log('根据直线距离，执行旅行商算法')
    Routing()
    console.log('根据算法结果，进行骑行规划')
    var promises = [];
    for(let i=0;i<solutionPath.length-1;i++){
        var promise = new Promise((resolve, reject) => {
            ridingRoute(posi[solutionPath[i]].lat,
            posi[solutionPath[i]].lng,
            posi[solutionPath[i+1]].lat,
            posi[solutionPath[i+1]].lng,
            false,
            (distance,duration)=>{
                console.log(distance,duration)
                pathInfo.distance+=convertToKilometers(distance)
                pathInfo.duration+=convertToMinutes(duration)
                resolve(); 
            })
            // 对于已经规划的路径，调用接口计算骑行时间距离
            // Compute total riding duration and distance of real path
        })
        promises.push(promise);
    }
    Promise.all(promises)
    .then(() => {
        console.log('总路径',pathInfo.distance,'公里',pathInfo.duration,'分钟')
        ridingRouteMap(posi[solutionPath[0]].lat,
            posi[solutionPath[0]].lng,
            posi[solutionPath[1]].lat,
            posi[solutionPath[1]].lng,
            false,
            (distance,duration)=>{
                console.log(distance,duration)
            })
    })
    // 更新路径显示
    // console.log(posi)
    pathStr.value = concatenateAddresses(posi, solutionPath);
}
// 将地址连接为字符串的函数
function concatenateAddresses(arr, order) {
    let concatenatedAddress = '';
    for (let i = 0; i < order.length; i++) {
        let index = order[i];
        if (index < arr.length) {
            concatenatedAddress += arr[index].address;
            if (i < order.length - 1) {
                concatenatedAddress += ' -> ';
            }
        }
    }
    return concatenatedAddress;
} 


const convertToKilometers = distance => {
    // 移除非数字字符
    const numericValue = parseFloat(distance.replace(/[^\d.]/g, ''));
    // 检查单位并转换
    if (distance.includes('米')) {
      // 米转换为公里
      return numericValue / 1000;
    } else if (distance.includes('公里')) {
      // 直接返回公里数值
      return numericValue;
    }
    // 默认情况（理论上不应该发生，除非格式不正确）
    return numericValue;
};

const convertToMinutes = duration => {
    // 移除非数字字符
    const numericValue = parseFloat(duration.replace(/[^\d.]/g, ''));
    return numericValue;
};


function ridingRoute(begin_lat,begin_lng,end_lat,end_lng,flag=false, callback){
    let start = new BaiduMap.Point(begin_lng, begin_lat); 
    let end = new BaiduMap.Point(end_lng, end_lat); 
    var distance=null;
    var duration=null;
    if(flag==false){
        var riding = new BaiduMap.RidingRoute(global_map, { 
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

function ridingRouteMap(begin_lat,begin_lng,end_lat,end_lng,flag=false, callback){
    let start = new BaiduMap.Point(begin_lng, begin_lat); 
    let end = new BaiduMap.Point(end_lng, end_lat); 
    var distance=null;
    var duration=null;
    if(flag==false){
        var riding = new BaiduMap.RidingRoute(global_map, { 
            renderOptions: { 
                map: global_map, 
                autoViewport: true 
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

// 以下为TSP算法相关

function lineDistance(a_lat, a_lng, b_lat, b_lng) {
    // 地球半径，单位是公里
    var R = 6371;
    // 将纬度和经度从度转换为弧度
    var lat1 = a_lat * Math.PI / 180;
    var lat2 = b_lat * Math.PI / 180;
    var deltaLat = (b_lat - a_lat) * Math.PI / 180;
    var deltaLng = (b_lng - a_lng) * Math.PI / 180;

    // 应用haversine公式计算距离
    var a = Math.sin(deltaLat/2) * Math.sin(deltaLat/2) +
            Math.cos(lat1) * Math.cos(lat2) *
            Math.sin(deltaLng/2) * Math.sin(deltaLng/2);
    var c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1-a));

    // 计算并返回两点之间的距离
    var distance = R * c;
    return distance;
}

function returnPage(){
    console.log('returnPage触发')
    router.go(-1)
}

function Routing(){
    // posi数组用于做路径规划，其中第一个元素是当前位置
    posi.push({
        address:"当前位置",
        lat:current_location.latitude,
        lng:current_location.longitude
    })
    destinations.forEach(ele=>{
        posi.push({
            address:ele.address,
            lat:ele.latitude,
            lng:ele.longitude
        })
    })
    // posi.push({
    //     address:sto_location.address,
    //     lat:sto_location.latitude,
    //     lng:sto_location.longitude
    // })
    // 创建邻接矩阵
    let adjacencyMatrix = [];
    for (let i = 0; i < posi.length; i++) {
        adjacencyMatrix[i] = []; // 初始化矩阵的一行
        for (let j = 0; j < posi.length; j++) {
            // 对角线元素（距离自身）设为 0
            if (i === j) {
                adjacencyMatrix[i].push(0) ;
            } else {
                // 计算两点之间的距离并存储在矩阵中
                adjacencyMatrix[i].push(lineDistance(posi[i].lat, posi[i].lng, posi[j].lat, posi[j].lng));
            }
        }
    }
    console.log(posi)
    // 输出邻接矩阵以查看结果
    adjacencyMat=adjacencyMatrix
    let result = solveTSP();
    // 在问题求解的最后将商店位置push上去
    posi.push({
        address:sto_location.address,
        lat:sto_location.latitude,
        lng:sto_location.longitude
    })
    result.path.pop()
    result.path.push(posi.length-1)
    console.log(`Shortest path: ${result.path.map(i => i + 1).join(' -> ')}`);
    console.log(`Shortest distance: ${result.distance.toFixed(2)} km`);
    solutionPath=result.path
}



// TSP算法辅助函数
function calculateTotalDistance(path) {
    let totalDistance = 0;
    for (let i = 0; i < path.length - 1; i++) {
        totalDistance += adjacencyMat[path[i]][path[i + 1]];
    }
    // 加上从最后一个点返回到起点的距离
    totalDistance += adjacencyMat[path[path.length - 1]][path[0]];
    return totalDistance;
}

function generateAllPaths(vertex, path, visited, allPaths) {
    visited[vertex] = true;
    path.push(vertex);

    if (path.length == adjacencyMat.length) {
        // 所有顶点都访问了，一个路径生成
        allPaths.push([...path]);
    } else {
        // 继续探索其他顶点
        for (let i = 0; i < adjacencyMat.length; i++) {
            if (!visited[i]) {
                generateAllPaths(i, path, visited, allPaths);
            }
        }
    }

    // 回溯
    path.pop();
    visited[vertex] = false;
}

function solveTSP() {
    let allPaths = [];
    generateAllPaths(0, [], Array(adjacencyMat.length).fill(false), allPaths);

    let shortestDistance = Infinity;
    let shortestPath = [];
    allPaths.forEach(path => {
        let distance = calculateTotalDistance(path);
        if (distance < shortestDistance) {
            shortestDistance = distance;
            shortestPath = path;
        }
    });
    shortestPath.push(0)
    return {
        path: shortestPath,
        distance: shortestDistance
    };
}
</script>

<style scoped>
#baidumap2{
    width:100%;
    height:60vh
}
.steps{
    –nut-steps-base-line-color:#93B090;
    –nut-steps-base-title-color:#93B090;
    –nut-steps-base-content-color:#93B090;
    –nut-steps-wait-icon-bg-color:#93B090;
    margin-top: 10px;
}
.container{
    width:90vw;
    margin: auto;
    padding-top:30px
}
.line1{
    margin-top: 10px;
    color: #93B090;
    font-family: "Source Han Sans CN";
    font-size: 18px;
    font-style: normal;
    font-weight: 700;
    line-height: 18px; /* 100% */
}
.line2{
    color: #979797;
    font-family: "Source Han Sans CN";
    font-size: 10px;
    font-style: normal;
    font-weight: 400;
    line-height: normal;
}
.line3b{
    color: black; 
    font-size: 12px; 
    font-family: Source Han Sans CN; 
    font-weight: 400; 
    word-wrap: break-word
}
.line3g{
    color: #93B090;
    font-size: 12px; 
    font-family: Source Han Sans CN; 
    font-weight: 700; 
    word-wrap: break-word
}
.info-container{
    margin-left: 10px;
    display: flex;
    align-content: flex-start;
    justify-content: flex-start;
    flex-direction: column;
    box-shadow: 0px 0px 6px rgba(0, 0, 0, 0.05); 
    border-radius: 4px;
}
</style>
<template>
  <nut-navbar :title="route.query.name" left-show @click-back="goBackPage">
    <template #left>
      <div>Back</div>
    </template>
  </nut-navbar>

  <div class="swiper-demo">
    <nut-swiper ref="swiperRef">
      <nut-swiper-item v-for="(item, index) in list" :key="index" style="height: 150px">
        <img :src="item" alt="" style="height: 100%; width: 100%" draggable="false" />
      </nut-swiper-item>
    </nut-swiper>
    <div class="swiper-btns">
      <div class="swiper-btn" @click="handlePrev">
        <Left></Left>
      </div>
      <div class="swiper-btn" @click="handleNext">
        <Right></Right>
      </div>
    </div>
  </div>


  <nut-cell>
    <nut-row>
      <nut-col>
        <nut-tag type="primary" style="
          background-color: #93B090;
          font-family: Source Han Sans SC;
          font-size: 8px;
          font-weight: 500;
          line-height: 11px;
          letter-spacing: 0.849056601524353px;
          text-align: left;
          width: 20.42px;
          height: 15.28px;
          top: 319px;
          left: 18px;
          border-radius: 8.49px;

        "> 
        {{ "当前" }} </nut-tag>
        <nut-price :price="route.query.price" size="normal" style="color: black;"/>
      </nut-col>
      <div>
        <Shop />
        {{ "  "+route.query.position }}
      </div>
      <Clock />
      {{timePeriod}}
    </nut-row>
  </nut-cell>

  <div style="font-family: Source Han Sans SC;
      font-size: 14px;
      font-weight: 700;
      line-height: 12px;
      letter-spacing: 0px;
    ">
    {{ "保质期&价格" }}
  </div>
  <div style="font-family: Source Han Sans CN;
      font-size: 10px;
      font-weight: 400;
      line-height: 18px;
      letter-spacing: 0px;
      text-align: right;
      color: #C8C8C8;
      margin-left: 100px;
    ">
    {{ "商品的价格会随着保质期剩余的天数越少越便宜。保质期剩余天数少于1天的商品将会自动下架" }}
  </div>
  <div id="main" style="width:auto;height: 300px;"></div>
</template>
<script setup>
import { ref,onMounted } from 'vue';
import { Shop,Clock } from '@nutui/icons-vue';
import { useRoute,useRouter } from 'vue-router';
import * as echarts from 'echarts';
import axios from 'axios';

var option = {
  tooltip: {
    trigger: 'axis'
  },
  xAxis: {
    type: 'category',
    boundaryGap: false,
    data: ['Mon', 'Tue', 'Wed', 'Thu', 'Fri', 'Sat', 'Sun']
  },
  yAxis: {
    type: 'value'
  },
  series: [
    {
      data: [820, 932, 901, 934, 1290, 1330, 1320],
      type: 'line',
      areaStyle: {
        color: new echarts.graphic.LinearGradient(0, 0, 0, 1, [
                { offset: 0, color: "#93B090" },  
                { offset: 1, color: "#FBFCFA" },
              ]),
      },
      itemStyle: {
            normal: {
                color: '#93B090', // 点的颜色
                lineStyle: {
                    color: '#93B090' // 线的颜色
                }
            }
        }
    }
  ]
};

var myChart;
const router=useRouter();
const route=useRoute();

const convert=(response)=>{
  timePeriod.value=response.sto_openingTime+" - "+response.sto_closingTime;
  
  option.xAxis.data=[];
  option.series[0].data=[];
  for(let i=0;i<response.commodityPriceCurve.length;++i){
    var nowCurve=response.commodityPriceCurve[i];
    option.xAxis.data.push(nowCurve.com_pc_time);
    option.series[0].data.push(nowCurve.com_pc_price);
  }

  list.value=[];
  for(let i=0;i<response.commodityImage.length;++i)
    list.value.push("https://007-food.obs.cn-east-3.myhuaweicloud.com/"+response.commodityImage[i].com_image);
  console.log(list.value);
  console.log(option);
  myChart.setOption(option);
}

onMounted(()=>{
  console.log(route.query);
  myChart = echarts.init(document.getElementById('main'));
  // myChart.setOption(option);

  axios.get('api/com/commoditydetail',{
    params:{
      com_ID:route.query.id
    }
  })
  .then(response=>{
    console.log(response.data);
    convert(response.data);
  })
})

const timePeriod=ref("");


const list = ref([]);
const swiperRef = ref();
const handlePrev = () => {
  swiperRef.value?.prev();
};
const handleNext = () => {
  swiperRef.value?.next();
};

const goBackPage=()=>{
  router.go(-1);
}
</script>

<style>
.swiper-demo {
  position: relative;
}
.swiper-btns {
  width: 100%;
  position: absolute;
  top: 50%;
  transform: translateY(-50%);
  z-index: 1;
  display: flex;
  justify-content: space-between;
}
.swiper-btn {
  display: flex;
  align-items: center;
  justify-content: center;
  width: 20px;
  height: 30px;
  background-color: rgba(0, 0, 0, 0.2);
}
</style>
  
  
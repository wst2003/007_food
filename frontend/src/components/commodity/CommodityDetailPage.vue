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
    <div style="display:flex;flex-direction: column;width: 100%;">
      <nut-row>
        <nut-col :span="12">
          <div style="display: flex;flex-direction: row;margin-bottom: 15px;">
          <nut-tag style="
            background-color: #93B090;
            border-radius: 30px;
            ">
            {{ "当前" }} </nut-tag>
            <div style="font-size: 30px; font-weight: 500;margin-left: 20px;color: black;">
            {{ "¥"+route.query.price }}
          </div>
          </div>
        </nut-col>
        <nut-col :span="12">
          <nut-input-number style="float:right;" v-model="buying_quantity" min="0" :max="route.query.left" />
        </nut-col>
      </nut-row>
      <nut-row style="display: flex;align-items: center;">
        <Shop style="margin-right: 15px;"/>
        <span @click="router.push({
           path:'/storeDetail',
           query:{
              sto_id:sto_ID
           }
        })">
        {{ " " + route.query.position }}
          </span>
      </nut-row>
      <nut-row style="display: flex;align-items: center;">
        <Clock style="margin-right: 15px;"/>
        {{ timePeriod }}
      </nut-row>
    </div>
  </nut-cell>

  <div style="font-family: Source Han Sans SC;
      font-size: 14px;
      font-weight: 700;
      line-height: 12px;
      letter-spacing: 0px;
      margin: 20px 20px  10px 20px;
    ">
    {{ "保质期&价格" }}
  </div>
  <div style="margin: 10px 20px 20px 20px;color: #C8C8C8;font-size: 10px;">
    {{ "商品的价格会随着保质期剩余的天数越少越便宜。保质期剩余天数少于1天的商品将会自动下架" }}
  </div>
  <div id="main" style="width:auto;height: 300px;"></div>

  <!-- Auto recommendation block -->
  <div style="font-family: Source Han Sans SC;
      font-size: 14px;
      font-weight: 700;
      line-height: 12px;
      letter-spacing: 0px;
      margin: 20px 20px  10px 20px;
    ">
    {{ "更多推荐" }}
  </div>
  <nut-infinite-loading v-model="ifLoading" :has-more="hasMore" @load-more="loadMore">
    <div style="padding: 0 20px;">
    <div v-for="(item) in recommendationList" :key=item.com_ID>
      <div class="commodity-card"
              @click="showDetail(item.com_ID, item.com_position,0, item.com_price, item.com_name, item.com_left)">
              <div style="height: 150px;position: relative;">
                <img :src="item.commodityImage" style="width:100%;height: 150px;border-radius: 20px 20px 0 0;" />
                <div style="position:absolute;bottom: 0;display: flex;height: fit-content;">
                  <div style="background-color: white;">
                    <div class="price-tag">
                      {{ '¥' + item.com_price }}
                    </div>
                  </div>
                  <div class="left-tag">
                    <span style="color:#969696;font-size: 10px;">剩余</span>
                    <span style="font-size: 13px;color: #93B090;font-weight: 700;margin: 0 2px 0 2px;">{{ item.com_left
                      }}</span>
                    <span style="color:#969696;font-size: 10px;">件</span>
                  </div>
                </div>
              </div>
              <div
                style="background-color:#e9f2e7;border-radius: 0 0 20px 20px;display: flex;justify-content: center;flex-direction: column;padding-left: 10px;position: relative;padding:10px">
                <div style="font-size: 14.5px;width:70%;">
                  {{ item.com_name }}
                </div>
                <div
                  style="font-size: 11px;color:#808080;text-overflow: ellipsis;white-space: nowrap;overflow: hidden;width: 80%;">
                  {{ item.com_position }}
                </div>
                <div class="rate-container">
                  <div class="rate">
                    {{ item.praise_rate }}
                  </div>
                </div>
                <div class="distance">
                  {{ item.com_dist + 'km' }}
                </div>
              </div>

            </div>
          </div>
    </div>
  </nut-infinite-loading>

  <!--Fixed block for shopping cart-->
  <div style="width: 90%; height: 44px; position:fixed;bottom: 70px; margin: auto;left:0;right:0">
    <div
      style="width: 100%; height: 44px; left: 0; top: 0; position: absolute; box-shadow: 0px 4px 25px rgba(0, 0, 0, 0.25); border-radius: 50px" />
    <div
      style="width: 70%; height: 44px; left: 0; top: 0; position: absolute; background: #FDFDFD; border-top-left-radius:50px;border-bottom-left-radius:50px;display: flex;align-items: end;">
      <div
        style="width: 70%; height: 29.59px; left: 5%; bottom:5px; position: absolute;display: flex;align-items: center;">
        <div
          style="height: 16px;  color: #666666; font-size: 10; font-family: 'Source Han Sans SC'; font-weight: 400; word-wrap: break-word">
          合计：</div>
        <span style="color: black; font-size: 18.49; font-family: 'Source Han Sans SC'; font-weight: 500;  ">￥</span>
        <span
          style="color: black; font-size: 2rem; font-family: 'Source Han Sans SC'; font-weight: 500;vertical-align:top  ">{{
    globalData.shoppingCart.getTotalPrice() }}</span>
      </div>
      <div
        style="width: 30%; height: 16px; right: 5px; bottom: 10px; position: absolute; color: #666666; font-size: 10; font-family: 'Source Han Sans SC'; font-weight: 400;  word-wrap: break-word">
        共{{ globalData.shoppingCart.getTotalQuantity() }}件商品</div>
    </div>
    <div
      style="width:30%;height:44px;right:0;top:0;position:absolute;background: black; display: flex;align-items: center;justify-content: center;border-top-right-radius: 50px; border-bottom-right-radius: 50px; "
      @click="EnterIndentConfirmPage()">
      <div
        style="text-align: center; color: white; font-size: 16; font-family: 'Source Han Sans SC'; font-weight: 700; word-wrap: break-word">
        去结算</div>
    </div>
  </div>

</template>
<script setup>
import { ref, onMounted, watch } from 'vue';
import { Shop, Clock } from '@nutui/icons-vue';
import { useRoute, useRouter } from 'vue-router';
import * as echarts from 'echarts';
import globalData from "../../global.js"
import axios from 'axios';

const buying_quantity = ref(0);// quantity in shopping cart
// Whenever quantity is modified, synchronize with shopping cart
watch(buying_quantity, () => {
  globalData.shoppingCart.modify(route.query.id, Number(buying_quantity.value), buying_quantity.value * route.query.price)
  console.log(globalData.shoppingCart.items)
})

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
      step: 'end',
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
const router = useRouter();
const route = useRoute();
const hasMore = ref(true);

const recommendationInfo = {
  page_num: 0,
  page_size: 5,
}

const recommendationList = ref([]);

const ifLoading = ref(false);

const sto_ID=ref(0);
const loadMore = () => {

  setTimeout(() => {
    axios.get('api/com/searchCommodity', {
      params: {
        content: "",
        com_type: "",
        sort_by: 1,
        sort_order: 0,
        page_size: recommendationInfo.page_size,
        page_num: ++recommendationInfo.page_num
      }
    }).then((res) => {
      for(let i=0;i<res.data.length;i++){
        res.data[i].commodityImage = "https://007-food.obs.cn-east-3.myhuaweicloud.com/"+res.data[i].commodityImage;
      }
      recommendationList.value = recommendationList.value.concat(res.data);
      ifLoading.value = false;
      if (res.data.length < recommendationInfo.page_size)
        hasMore.value = false;
    })
  }, 1000)

}

const convert = (response) => {
  sto_ID.value=response.sto_ID;
  console.log(sto_ID.value)
  timePeriod.value = response.sto_openingTime + " - " + response.sto_closingTime;

  option.xAxis.data = [];
  option.series[0].data = [];
  for (let i = 0; i < response.commodityPriceCurve.length; ++i) {
    var nowCurve = response.commodityPriceCurve[i];
    option.xAxis.data.push(nowCurve.com_pc_time);
    option.series[0].data.push(nowCurve.com_pc_price);
  }

  list.value = [];
  for (let i = 0; i < response.commodityImage.length; ++i)
    list.value.push("https://007-food.obs.cn-east-3.myhuaweicloud.com/" + response.commodityImage[i].com_image);
  console.log(list.value);
  console.log(option);
  myChart.setOption(option);
}

onMounted(() => {
  console.log("use")
  console.log(route.query);
  myChart = echarts.init(document.getElementById('main'));
  // myChart.setOption(option);
  buying_quantity.value=globalData.shoppingCart.getItemById(route.query.id).quantity
  console.log('购物车中的对象：'+globalData.shoppingCart.getItemById(route.query.id))
  console.log(globalData.shoppingCart.items)
  axios.get('api/com/commoditydetail', {
    params: {
      com_ID: route.query.id    // TODO: replace with router's params
    }
  })
    .then(response => {
      console.log(response.data);
      convert(response.data);
    })

  axios.get('api/com/searchCommodity', {
    params: {
      content: "",
      com_type: "",
      sort_by: 1,
      sort_order: 0,
      page_size: recommendationInfo.page_size,
      page_num: ++recommendationInfo.page_num
    }
  }).then((res) => {
    for(let i=0;i<res.data.length;i++){
        res.data[i].commodityImage = "https://007-food.obs.cn-east-3.myhuaweicloud.com/"+res.data[i].commodityImage;
      }
    recommendationList.value = recommendationList.value.concat(res.data);
    if (res.data.length < recommendationInfo.page_size)
      hasMore.value = false;
  })
})

const timePeriod = ref("");


const list = ref([]);
const swiperRef = ref();
const handlePrev = () => {
  swiperRef.value?.prev();
};
const handleNext = () => {
  swiperRef.value?.next();
};

const goBackPage = () => {
  router.go(-1);
}

const EnterIndentConfirmPage = () => {
  if (globalData.shoppingCart.items.length > 0) {
    router.push({
      path: '/indentConfirm'
    })
  }

}

const showDetail = (id, position, distance, price, name, left) => {
  console.log(name)
  router.push({
    path: '/commodityDetail',
    query: {
      id: id,
      position: position,
      distance: distance,
      price: price,
      name: name,
      left: left
    }
  })

  console.log("use")
  console.log(route.query);
  myChart = echarts.init(document.getElementById('main'));
  // myChart.setOption(option);
  buying_quantity.value=globalData.shoppingCart.getItemById(route.query.id).quantity
  console.log('购物车中的对象：'+globalData.shoppingCart.getItemById(route.query.id))
  console.log(globalData.shoppingCart.items)
  axios.get('api/com/commoditydetail', {
    params: {
      com_ID: route.query.id    // TODO: replace with router's params
    }
  })
      .then(response => {
        console.log(response.data);
        convert(response.data);
      })

  axios.get('api/com/searchCommodity', {
    params: {
      content: "",
      com_type: "",
      sort_by: 1,
      sort_order: 0,
      page_size: recommendationInfo.page_size,
      page_num: ++recommendationInfo.page_num
    }
  }).then((res) => {
    for(let i=0;i<res.data.length;i++){
      res.data[i].commodityImage = "https://007-food.obs.cn-east-3.myhuaweicloud.com/"+res.data[i].commodityImage;
    }
    recommendationList.value = recommendationList.value.concat(res.data);
    if (res.data.length < recommendationInfo.page_size)
      hasMore.value = false;
  })
}
</script>

<style scoped>
.info-tag{
  background-color: #eaf3e8;
  border-radius:  0 0 20px 20px;
  position: absolute;
  bottom: -1%;
  width: 100%;
  height: 30%;
  font-size: 20px;
  color: #131212;
  padding-left: 5%;
  font-weight: 400;
  display: flex;
  justify-content: center;
  flex-direction: column;
}

.rcmd-block {
  background-size: 100%;
  border-radius: 20px;
  height: 200px;
  margin: 20px;
  position: relative;
}

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

.commodity-card {
  width: 100%;
  margin-bottom: 20px;
}

.price-tag {
  padding: 5px 10px 5px 10px;
  background-color: #93B090;
  border-top-right-radius: 10px;
  box-shadow: 1px -1px 3.9px -1px rgba(0, 0, 0, 0.25);
  color: #FFF;
  text-shadow: 0px 1px 4px rgba(0, 0, 0, 0.25);
  font-family: "Source Han Sans C";
  font-size: 13px;
  font-style: normal;
  font-weight: 900;
  line-height: 13px;
  z-index: 10;
  position: relative;
  border: 1px solid #93B090;
}

.left-tag {
  border-top-right-radius: 10px;
  background: #FFF;
  box-shadow: 3px 7px 3.9px -1px rgba(0, 0, 0, 0.25);
  padding: 5px 10px 5px 10px;
  font-size: 13px;
  font-style: normal;
  font-weight: 900;
  line-height: 13px;
}

.rate-container {
  padding: 0 10px;
  border-radius: 23px;
  background: #FFF;
  font-size: 11px;
  box-shadow: 0px 1px 4px 0px rgba(0, 0, 0, 0.25);
  color: #969696;
  font-weight: 500;
  text-align: center;
  position: absolute;
  right: 10px;
  top: 20%
}

.distance {
  color: #25522c;
  font-size: 13px;
  position: absolute;
  right: 15px;
  bottom: 10%;
  font-weight: bolder;
}

.rate::before {
  content: url(../../assets/star.svg);
  margin-right: 4px;
}
</style>
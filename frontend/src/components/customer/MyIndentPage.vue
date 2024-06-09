<template>
  <nut-config-provider :theme-vars="themeVars">
    <nut-navbar left-show @click-back="clickBack">
      <template #content> 我的订单 </template>
    </nut-navbar>
    <nut-tabs v-model="state" :animated-time="0" @click="changeType">
      <nut-tab-pane title="全部" pane-key="1">
        <nut-row v-for="indent in indentData" :key="indent.ind_id" >
          <nut-cell style="background-color:white;display:flex;flex-direction:column;position:relative">
            <div style="height: 30px;"></div>
            <div v-for="(commodity, index) in indent.commodity" :key="index" class="com-item-container">
              <div class="com-item">
                <img :src="'https://007-food.obs.cn-east-3.myhuaweicloud.com/' + commodity.com_image"
                  style="width: 50px;height:50px;border-radius: 5px;flex:2" />
                <div class="com-name">{{ commodity.com_name }}</div>
                <div style="display: flex;flex-direction:column;flex:3;text-align:right">
                  <div class="com-price">{{ '¥' + commodity.com_price }}</div>
                  <div style="font-size:11px;color:grey;">{{ '共 ' + commodity.com_quantity + '件' }}</div>
                </div>
              </div>
            </div>
            <div style="font-size:11px;">
              {{ '地址： '+indent.com_position }}
            </div>
            <div style="font-size:11px;margin-bottom:10px">
              {{ '时间： '+indent.order_time }}
            </div>
            <div color="#EBF5EA" style="position:absolute;right: 10px; top:10px;font-size:10px;" class="status-tag"
              :class="defineTagClass(indent)"> {{ indent.ind_state }} </div>
            <div style="    display: flex;
          flex-direction: row;
          gap: 10px;
          flex-flow: row-reverse;">
              <div color="#EBF5EA" style="color: black;" class="order-button" @click="goDetail(indent.ind_id)"> 查看详情
              </div>
              <div color="#EBF5EA" style="color: black;" class="order-button" @click="gradeIndent(indent.ind_id)"
                v-if="indent.ind_state === '确认收货' || indent.ind_state === '已核销'"> 评价 </div>
              <div color="#EBF5EA" style="color: black;" class="order-button" @click="newIndent(indent.sto_id)"> 再来一单
              </div>
            </div>
          </nut-cell>
        </nut-row>
      </nut-tab-pane>

      <nut-tab-pane title="已评价" pane-key="2" style="background-color: #FBFCFA;">
        <nut-row v-for="indent in indentData" :key="indent.ind_id">
          <nut-cell style="background-color: white;display:flex;flex-direction:column;position:relative">
            <div style="height: 30px;"></div>
            <div v-for="(commodity, index) in indent.commodity" :key="index" class="com-item-container">
              <div class="com-item">
                <img :src="'https://007-food.obs.cn-east-3.myhuaweicloud.com/' + commodity.com_image"
                  style="width: 50px;height:50px;border-radius: 5px;flex:2" />
                <div class="com-name">{{ commodity.com_name }}</div>
                <div style="display: flex;flex-direction:column;flex:3;text-align:right">
                  <div class="com-price">{{ '¥' + commodity.com_price }}</div>
                  <div style="font-size:11px;color:grey;">{{ '共 ' + commodity.com_quantity + '件' }}</div>
                </div>
              </div>
            </div>
            <div style="font-size:11px;">
              {{ '地址： '+indent.com_position }}
            </div>
            <div style="font-size:11px;margin-bottom:10px">
              {{ '时间： '+indent.order_time }}
            </div>
            <div color="#EBF5EA" style="position:absolute;right: 10px; top:10px;font-size:10px;" class="status-tag"
              :class="defineTagClass(indent)"> {{ indent.ind_state }} </div>
            <div style="    display: flex;
          flex-direction: row;
          gap: 10px;
          flex-flow: row-reverse;">
              <div color="#EBF5EA" style="color: black;" class="order-button" @click="goDetail(indent.ind_id)"> 查看详情
              </div>
              <div color="#EBF5EA" style="color: black;" class="order-button" @click="gradeIndent(indent.ind_id)"
                v-if="indent.ind_state === '确认收货' || indent.ind_state === '已核销'"> 评价 </div>
              <div color="#EBF5EA" style="color: black;" class="order-button" @click="newIndent(indent.sto_id)"> 再来一单
              </div>
            </div>
          </nut-cell>
        </nut-row>
      </nut-tab-pane>
    </nut-tabs>
  </nut-config-provider>
</template>


<script setup>
import axios from 'axios';
import { onMounted, ref } from 'vue';
import { useRouter } from 'vue-router';
import globalData from "../../global.js"

const BaseUrl = globalData.BaseUrl
const state = ref('1');
const router = useRouter();
// const route=useRoute();
// const cus_ID=route.query.cus_ID;
// import globalData from"../../global.js"
// import {stat} from "@babel/core/lib/gensync-utils/fs";
const cus_ID = sessionStorage.getItem("user_id");

const defineTagClass = (item) => {
  if (item.ind_state === '待取货') return 'daiquhuo';
  else if (item.ind_state === '未收货') return 'weishouhuo';
  else if (item.ind_state === '确认收货') return 'querenshouhuo';
  else if (item.ind_state === '已评价') return 'yipingjia';
}

const transformTimeString = (timeStr)=> {
    // Parse the input time string into a Date object
    const date = new Date(timeStr);

    // Get the components of the date
    const year = date.getFullYear();
    const month = String(date.getMonth() + 1).padStart(2, '0'); // Months are zero-based
    const day = String(date.getDate()).padStart(2, '0');
    const hours = String(date.getHours()).padStart(2, '0');
    const minutes = String(date.getMinutes()).padStart(2, '0');
    const seconds = String(date.getSeconds()).padStart(2, '0');

    // Format the date and time components into the desired string format
    return `${year}-${month}-${day} ${hours}:${minutes}:${seconds}`;
}

const themeVars = ref({
  primaryColor: '#99af93',
  categoryListLeftBgColor: '#f9fbf7'
});

const indentData = ref([
  {
    ind_id: "1",
    commodity: [{
      com_name: "PEANUTS奶油贝果",
      com_quantity: "2"
    }],
    com_position: "吉事花生鞍山新村二店",
    ind_state: "1",
    ind_money: "1"
  }
])

onMounted(() => {
  console.log("!!!" + cus_ID)
  indentData.value.pop();
  searchIndent(state.value);
})
const indState = ["未收货", "确认收货", "待取货", "已核销", "过期未取", "已评价"]
const convert = (data) => {
  for (let i = 0; i < data.length; ++i) {
    var now = data[i];
    var tempData = {
      ind_id: "1",
      commodity: [{
        com_name: "PEANUTS奶油贝果",
        com_quantity: "2"
      }],
      com_position: "吉事花生鞍山新村二店",
      ind_state: "1",
      ind_money: "1",
      sto_id: "",
    };

    tempData.ind_id = now.ind_ID;
    tempData.com_position = now.com_position;
    tempData.ind_state = indState[now.ind_state];
    tempData.sto_id = now.sto_ID;
    tempData.order_time = transformTimeString(now.ind_creationTime);
    tempData.ind_money = now.ind_money;
    tempData.commodity.pop();
    console.log(now);
    for (let j = 0; j < now.commodities.length; ++j) {
      tempData.commodity.push({
        com_name: now.commodities[j].com_name,
        com_quantity: now.commodities[j].ind_quantity,
        com_image: now.commodities[j].com_image,
        com_price: now.commodities[j].commodity_money
      })
    }

    indentData.value.push(tempData);
  }
}

const searchIndent = (state) => {
  console.log(state.toString())
  axios.get(BaseUrl + '/api/cus/getInd', {
    params: {
      cus_ID: parseInt(cus_ID),
      state: (state.paneKey == '1' || state == '1' ? null : 5)
    }
  }).then(response => {
    var data = response.data;

    data.sort((a, b) => a.ind_creationTime > b.ind_creationTime ? -1 : 1);

    console.log(data)

    convert(data);
  })
}


const clickBack = () => {
  router.go(-1);
}

const changeType = (state) => {
  console.log(state.paneKey);
  indentData.value = [];
  searchIndent(state);
}

const gradeIndent = (ind_id) => {
  console.log(ind_id)
  router.push({
    path: '/comment',
    query: {
      ind_id: ind_id
    }

  })

}
const newIndent = (sto_id) => {
  router.push({
    path: '/storeDetail',
    query: {
      sto_id: sto_id
    }
  })
}

const goDetail = (ind_id) => {
  router.push({
    path: '/indentdetail',
    query: {
      ind_id: ind_id
    }
  })
}
</script>

<style scoped>
.order-button {
  background-color: white;
  border-radius: 10px;
  min-width: 20px;
  padding: 1px 10px;
  border-color: #e4e4e485;
  border-width: 1px;
  font-size: small;
  border-style: solid;
}

.daiquhuo {
  background-color: rgb(255 174 0 / 15%);
  color: rgba(255, 174, 0, 0.95);
}

.weishouhuo {
  background-color: rgba(62, 62, 62, 0.15);
  color: rgba(62, 62, 62, 0.95);
}

.querenshouhuo {
  background-color: rgb(2 184 255 / 15%);
  color: rgb(2 184 255 / 95%);
}

.yipingjia {
  background-color: rgb(0 186 0 / 15%);
  color: rgb(0 128 0 / 95%);
}

.status-tag {
  border-radius: 10px;
  padding-left: 10px;
  padding-right: 10px;
  text-align: center;
}

.com-item-container {
  display: flex;
  flex-direction: column;
  gap: 10px
}

.com-item {
  display: flex;
  flex-direction: row;
  align-items: center;
  color: black;
  margin-bottom: 10px;
}

.com-name {
  margin-left: 10px;
  flex: 10;
}

.com-price {
  font-family: monospace;
  font-size: large;
}

.nut-tab-pane{
  background-color: #FBFCFA;
}
</style>
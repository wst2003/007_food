<template>
  <nut-navbar :title="mysteryBoxInfo.com_name" left-show @click-back="goBackPage">
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
          <nut-tag type="primary" style="
            background-color: #93B090;
            border-radius: 30px;
            ">
            {{ "当前" }} </nut-tag>
          <div style="font-size: 30px; font-weight: 500;margin-left: 20px;color: black;">
            {{ "¥"+mysteryBoxInfo.com_oriPrice }}
          </div>
        </div>
        </nut-col>
        <nut-col :span="12" >
          <nut-input-number style="float: right;" v-model="buying_quantity" min="0" :max="mysteryBoxInfo.com_left" />
        </nut-col>
      </nut-row>
      <nut-row style="display: flex;align-items: center;">
        <Shop style="margin-right: 15px;"/>
        <span>
          <span>
        {{ " " + mysteryBoxInfo.sto_name }}</span>
        <span>{{  }}</span>
        </span>
      </nut-row>
      <nut-row style="display: flex;align-items: center;">
        <Clock style="margin-right: 15px;"/>
        {{ mysteryBoxInfo.sto_openingTime + " - " + mysteryBoxInfo.sto_closingTime }}
      </nut-row>
      <nut-row style="display: flex;align-items: center;">
        <Ask style="margin-right: 15px;"/>
        {{ mysteryBoxInfo.com_introduction }}
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
    {{ "更多推荐" }}
  </div>

  <!-- Auto recommendation block -->
  <nut-infinite-loading v-model="ifLoading" :has-more="hasMore" @load-more="loadMore">
      <div v-for="(item, index) in recommendationList" :key="index" style="display: flex;" >
            <div class="commodity-card"
              @click="showDetail(item.mystery_box_ID)">
              <div style="height: 150px;position: relative;">
                <img :src="'https://007-food.obs.cn-east-3.myhuaweicloud.com/'+item.contain_images[0].com_image" style="width:100%;height: 150px;border-radius: 20px 0 0 0;" />
                <div style="position:absolute;bottom: 0;display: flex;height: fit-content;">
                  <div style="background-color: white;">
                    <div class="price-tag">
                      {{ '¥' + item.com_oriPrice }}
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
                style="background-color:#e9f2e7;border-radius: 0 0 0 20px;display: flex;justify-content: center;flex-direction: column;padding-left: 10px;position: relative;padding:10px">
                <div style="font-size: 14.5px;width:70%;">
                  {{ item.com_name }}
                </div>
                <div
                  style="font-size: 11px;color:#808080;text-overflow: ellipsis;white-space: nowrap;overflow: hidden;width: 80%;">
                  {{ item.sto_name }}
                </div>
                <div class="rate-container">
                  <div class="rate">
                    {{ item.praise_rate }}
                  </div>
                </div>
<!--                <div class="distance">-->
<!--                  {{ item.com_dist + 'km' }}-->
<!--                </div>-->
              </div>
            </div>
            <div style="background-color: #808080;width: 20%;margin-bottom: 20px;border-radius: 0 20px 20px 0;background: #93B090;box-shadow: -3px 0px 4px 0px rgba(0, 0, 0, 0.25);z-index: 10;
            display: flex;flex-direction: column;align-items: center;justify-content: center;">
              <div v-for="(item2, index2) in item.contain_images" :key='index1 + "_" +index2'>
                <img :src="'https://007-food.obs.cn-east-3.myhuaweicloud.com/'+item2.com_image"  style="border-radius: 50%;width:50px;height: 50px;border: 3px solid #FFF;box-shadow: 0px 0px 4px 0px rgba(0, 0, 0, 0.25);"  />
              </div>
            </div>

    </div>
  </nut-infinite-loading>

  <!--Fixed block for shopping cart-->
  <div style="width: 90%; height: 44px; position:fixed;bottom: 70px; margin: auto;left:0;right:0;z-index:10 ;">
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
import { Shop, Clock, Ask } from '@nutui/icons-vue';
import {useRoute, useRouter} from 'vue-router';

import globalData from "../../global.js"
import axios from 'axios';

const buying_quantity = ref(0);// quantity in shopping cart
// Whenever quantity is modified, synchronize with shopping cart
watch(buying_quantity, () => {
  globalData.shoppingCart.modify(mysteryBoxInfo.value.mystery_box_ID, Number(buying_quantity.value), Number((buying_quantity.value * mysteryBoxInfo.value.com_oriPrice).toFixed(2)))
  console.log(globalData.shoppingCart.items)
})

const router = useRouter();
const route=useRoute();
const hasMore = ref(true);

const recommendationInfo = {
  page_num: 0,
  page_size: 5,
}

// restore recommendation info
const recommendationList = ref([]);
// restore mystery box info
const mysteryBoxInfo = ref({});

const ifLoading = ref(false);

const list = ref([]);

const swiperRef = ref();

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
      recommendationList.value = recommendationList.value.concat(res.data);
      ifLoading.value = false;
      if (res.data.length < recommendationInfo.page_size)
        hasMore.value = false;
    })
  }, 1000)

}

onMounted(() => {

  axios.get('api/mys/getmysterybox', {
    params: {
      mystery_box_ID: route.query.mystery_box_id,   // TODO: modify mystery_box_ID
    }
  }).then(response => {
    console.log(response.data[0])
    mysteryBoxInfo.value = response.data[0];
    const images = mysteryBoxInfo.value.contain_images;
    for (let i = 0; i < images.length; ++i)
      list.value.push("https://007-food.obs.cn-east-3.myhuaweicloud.com/" + images[i].com_image)
  }).then(()=>{
    buying_quantity.value=globalData.shoppingCart.getItemById(mysteryBoxInfo.value.mystery_box_ID).quantity
  })

  axios.get('api/mys/getmysterybox', {
    params: {
      page_size: recommendationInfo.page_size,
      page_num: ++recommendationInfo.page_num
    }
  }).then((res) => {
    recommendationList.value = recommendationList.value.concat(res.data);
    if (res.data.length < recommendationInfo.page_size)
      hasMore.value = false;
  })
})






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

const showDetail = (id) => {
  axios.get('api/mys/getmysterybox', {
    params: {
      mystery_box_ID: id,   // TODO: modify mystery_box_ID
    }
  }).then(response => {
    console.log(response.data[0])
    mysteryBoxInfo.value = response.data[0];
    const images = mysteryBoxInfo.value.contain_images;
    list.value=[]
    for (let i = 0; i < images.length; ++i)
      list.value.push("https://007-food.obs.cn-east-3.myhuaweicloud.com/" + images[i].com_image)
  }).then(()=>{
    buying_quantity.value=globalData.shoppingCart.getItemById(mysteryBoxInfo.value.mystery_box_ID).quantity
  })
}
</script>

<style>
.info-tag {
  background-color: #eaf3e8;
  border-radius: 0 0 20px 20px;
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
</style>
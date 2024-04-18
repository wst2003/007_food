<template>
  <div v-if="!loading">
    <BaiduMap />
    <nut-searchbar v-model="query_content">
      <template #rightin>
        <Voice @click="translationStart" v-if="voiceState" />
        <Voice @click="translationEnd" v-else />
        <!--      <VoiceInput @click="getResult"/>-->
      </template>
      <template #rightout>
        <Search2 @click="userSearch" />
      </template>
    </nut-searchbar>
    <div class="sort-bar">
      <div  @click="sortOptionClick(1)" :class="!sortOptionStatus['s1']? 'sort-tag':'sort-tag-clicked'">按价格排序</div>
      <div  @click="sortOptionClick(2)" :class="!sortOptionStatus['s2']? 'sort-tag':'sort-tag-clicked'" >按评分排序</div>
    </div>
    <nut-infinite-loading v-model="infinityValue" :has-more="hasMore" @load-more="loadMore">
      <nut-config-provider :theme-vars="themeVars">
        <div class="container">
          <div v-for="(item, index) in commodityList" :key="index" style="display: flex;" >
            <div class="commodity-card"
              @click="showDetail(item.mystery_box_ID)">
              <div style="height: 150px;position: relative;">
                <img :src="item.contain_images[0].com_image" style="width:100%;height: 150px;border-radius: 20px 0 0 0;" />
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
                <div class="distance">
                  {{ item.com_dist + 'km' }}
                </div>
              </div>
            </div>
            <div style="background-color: #808080;width: 20%;margin-bottom: 20px;border-radius: 0 20px 20px 0;background: #93B090;box-shadow: -3px 0px 4px 0px rgba(0, 0, 0, 0.25);z-index: 10;
            display: flex;flex-direction: column;align-items: center;justify-content: center;">
              <div v-for="(item2, index2) in item.contain_images" :key='index1 + "_" +index2'>
                <img :src="item2.com_image"  style="border-radius: 50%;width:50px;height: 50px;border: 3px solid #FFF;box-shadow: 0px 0px 4px 0px rgba(0, 0, 0, 0.25);"  />
              </div>
            </div>

          </div>
        </div>
      </nut-config-provider>

      <!-- <div class="test" v-for="(item, index) in sum" :key="index">{{ index }}</div> -->
    </nut-infinite-loading>
  </div>


</template>



<script setup>
import { ref } from "vue";
import {
  Search2,
  Voice,
} from "@nutui/icons-vue";
import { useRouter } from "vue-router";
import axios from "axios";
import BaiduMap from '../BaiduMap.vue'
import IatRecorder from '@/assets/js/IatRecorder.js'
import { reactive } from "vue";
const iatRecorder = new IatRecorder('en_us', 'mandarin', '5f27b6a9')

// import VoiceInput from "@/components/VoiceInput.vue";
const pageSize = ref(6);
const pageNum = ref(0);         
const sortBy = ref(0);
const loading = ref(true)

// const route=useRoute();
const router = useRouter();

const sortOptionStatus = reactive({
  s1: false,
  s2: false,
})

const sortName = {
  s1: 0,
  s2: 1,
}


const themeVars = ref({
  cellPadding: "0px 0px",
  primaryColor: "#acc2a9",
  primaryColorEnd: "#acc2a9",
});
const query_content = ref("");

const commodityData = ref([]);

const commodityList = ref([]);

const showDetail = (id) => {
  router.push({
    path: '/mysteryBoxDetail',
    query: {
      mystery_box_id:id
    }
  })
}

const sortOptionClick = (num)=>{
  pageNum.value = 0;
  commodityList.value = [];
  let key = 's'+num;
  if(sortOptionStatus[key]){
    sortOptionStatus[key] = false;
    sortBy.value = '';
    searchBox();
    return;
  }
  for(let i=1;i<2;i++){
    sortOptionStatus['s'+i] = false;
  }
  sortOptionStatus[key] = true;
  sortBy.value = sortName[key];
  searchBox();
} 


const searchBox = () => {
  pageNum.value++;
  console.log(sortBy.value)
  axios.get('/api/mys/getmysterybox', {
    params: {
      sort_by: sortBy.value,
      page_size: pageSize.value,
      page_num: pageNum.value
    }  
  })
    .then(response => {
      console.log(response.data)
      for(let i=0;i<response.data.length;i++){
        response.data[i].commodityImage = "https://007-food.obs.cn-east-3.myhuaweicloud.com/"+response.data[i].item_image;
        for(let j=0;j<response.data[i].contain_images.length;j++){
          response.data[i].contain_images[j].com_image = "https://007-food.obs.cn-east-3.myhuaweicloud.com/" + response.data[i].contain_images[j].com_image;
        }
      }
      commodityList.value = commodityList.value.concat(response.data);
      console.log(commodityList.value)
      loading.value = false;
      if (response.data.length < pageSize.value) {
        hasMore.value = false;
      }
    })
}


searchBox();

const userSearch = () => {
  commodityData.value = [];
  pageNum.value = 0;
  hasMore.value = true;
  searchBox();
}

// const tabsValue = ref(0);
const sum = ref(24);
const infinityValue = ref(false);
const hasMore = ref(true);
const loadMore = () => {
  setTimeout(() => {
    sum.value = sum.value + 24;
    searchBox();
    infinityValue.value = false;
  }, 1000);
};



const voiceState = ref(true);
const voiceResult = ref("");
const translationStart = () => {
  iatRecorder.start()
  voiceState.value = false;
}

const translationEnd = () => {
  iatRecorder.stop()
  voiceState.value = true;
  voiceResult.value = iatRecorder.resultText
  console.log(voiceResult.value)
  // axios.post('http://119.8.11.44:6000/api/test/gpt', {
  //   words: voiceResult.value
  // }).then(response => {
  //   console.log("上传完成")
  //   console.log(response.data)
  // })
}

</script>

<style scoped>
.type-title{
  color: #6D6868;
text-align: center;
font-family: "Source Han Sans SC";
font-size: 10px;
font-style: normal;
font-weight: 500;
line-height: 16px; /* 133.333% */
letter-spacing: 1px;
position: absolute;
bottom: 0;
transform: translateY(100%);
}

.sort2-circle{
  border-radius: 50%;
  background-color: #F2F2F2;
  display:flex;
  justify-content: center;
  align-items: center;
  width: 40px;
  height: 40px;
  position: relative;

}

.sort2-circle-clicked{
  border-radius: 50%;
  background-color: #93B090;
  display:flex;
  justify-content: center;
  align-items: center;
  width: 40px;
  height: 40px;
  position: relative;
}

.sort-tag {
  background-color: white;
  border: 1px solid #E6E6E6;
  border-radius: 20px;
  color: #969696;
  font-family: "Source Han Sans SC";
  font-size: 13px;
  font-style: normal;
  font-weight: 400;
  padding: 2px 10px;
}
.sort-tag-clicked {
  background-color: #93B090;
  color:white;
  border-radius: 20px;
  font-family: "Source Han Sans SC";
  font-size: 13px;
  font-style: normal;
  font-weight: 400;
  padding: 2px 10px;
  border: 1px solid #93B090;
}

.container {
  padding: 20px;
  flex-direction: row;
  flex-wrap: wrap;
  gap: 15px;
  justify-content: center;
}

.commodity-card {
  width: 80%;
  margin-bottom: 20px;


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

.rate::before {
  content: url(../../assets/star.svg);
  margin-right: 4px;
}

.distance {
  color: #25522c;
  font-size: 10px;
  position: absolute;
  right: 10px;
  bottom: 10%;
  font-weight: bolder;
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

.sort-bar {
  background-color: #F8FBF7;
  height: 20%;
  display: flex;
  justify-content: center;
  gap: 10px;
  padding:15px
}

.sort-bar2{
  display: flex;
  justify-content: center;
  gap: 30px;
  padding: 15px 0 30px 0;

}
</style>
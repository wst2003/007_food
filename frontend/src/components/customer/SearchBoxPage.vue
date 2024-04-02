<template>
  <BaiduMap />
  <nut-searchbar v-model="query_content">
    <template #rightin>
      <Voice />
    </template>
    <template #rightout>
      <Search2 @click="userSearch"/>
    </template>
  </nut-searchbar>
  <nut-infinite-loading v-model="infinityValue" :has-more="hasMore" @load-more="loadMore">
    <nut-row v-for="box in boxes" :key="box.id">
      <nut-col :span="19">
        <nut-config-provider :theme-vars="themeVars">
          <nut-cell style="height: 200px;background-color: #C7E1C487">
            <div style="width: 100%">
              <nut-cell style="width: 15%;height: 15%;position: absolute;z-index: 100;margin-top: 110px;background-color: #93B090;--nut-cell-box-shadow: v-bind(0px 0px 0px 0px)">
                <div style="font-family: Source Han Sans C;
                  font-size: 15px;
                  font-weight: 900;
                  line-height: 13px;
                  text-align: left;
                  color:#FFFFFF;
                  margin:auto
               ">
                <span style="font-family: Source Han Sans C;
                  font-size: 12px;
                  font-weight: 900;
                  line-height: 13px;
                  text-align: left;
                  margin-right: 5px;
                 ">{{"¥"}}</span>{{box.price}}
                </div>
              </nut-cell>
              <nut-cell style="width: 30%;height: 15%;position: absolute;z-index: 99;margin-top: 110px;margin-left:10%;--nut-cell-box-shadow: v-bind(0px 0px 0px 0px);">
                <div style="margin:auto">
                <span style="font-family: Source Han Sans SC;
                  font-size: 10px;
                  font-weight: 700;
                  line-height: 13px;
                  text-align: left;
                  color: #969696;
                ">
                  {{"剩余"}}
                </span>
                  <span style="font-family: Source Han Sans SC;
                  font-size: 14px;
                  font-weight: 700;
                  line-height: 13px;
                  text-align: left;
                  color: #93B090;
                 ">
                  {{box.left}}
                </span>
                  <span style="font-family: Source Han Sans SC;
                  font-size: 10px;
                  font-weight: 700;
                  line-height: 13px;
                  text-align: left;
                  color: #969696;
                ">
                  {{"份"}}
                </span>
                </div>
              </nut-cell>
              <nut-image :src="box.main_url" height="70%" width="100%" :round="true" :radius="6"/>
              <div style="width: 100%">
                <nut-ellipsis style="font-family: Source Han Sans SC;
                      font-size: 14px;
                      font-weight: 400;
                      line-height: 16px;
                      letter-spacing: 1px;
                      text-align: left;
                      color:#131212;
                      margin-left: 15px;
                      margin-top: 5px;
                      width:100%" :content="box.name"
                              direction="end"
                ></nut-ellipsis>
              </div>

              <!--            <div style="width: 70%">-->
              <nut-row >
                <nut-col :span="14">
                  <nut-ellipsis style="font-family: Source Han Sans SC;
                        font-size: 10px;
                        font-weight: 400;
                        line-height: 16px;
                        text-align: left;
                        margin-left: 15px;
                        margin-top: 10px;
                        width:100%" :content="box.address"
                                direction="end"
                  ></nut-ellipsis>
                </nut-col>
                <nut-col :span="6">
                  <nut-ellipsis style="font-family: Source Han Sans SC;
                        font-size: 10px;
                        font-weight: 400;
                        line-height: 16px;
                        text-align: left;
                        margin-left: 15px;
                        margin-top: 10px;
                        width:100%" :content="box.introduction"
                                direction="end"
                  ></nut-ellipsis>
                </nut-col>
              </nut-row>
              <!--            </div>-->

            </div>

          </nut-cell>
        </nut-config-provider>

      </nut-col>
      <nut-col :span="5">
        <nut-cell style="height: 200px;background-color: #93B090;display: block;">
          <nut-image :src="box.url[0]" width="60" height="60" fit="cover" round style="margin: auto" />
          <nut-image :src="box.url[1]" width="60" height="60" fit="cover" round style="margin: auto" />
          <nut-image :src="box.url[2]" width="60" height="60" fit="cover" round style="margin: auto" />
        </nut-cell>
      </nut-col>
    </nut-row>
    <!-- <div class="test" v-for="(item, index) in sum" :key="index">{{ index }}</div> -->
  </nut-infinite-loading>


</template>



<script setup>
import { onMounted,ref } from "vue";
import {
  Search2,
  Voice,
} from "@nutui/icons-vue";
// import { useRouter } from "vue-router";
import axios from "axios";
import BaiduMap from '../BaiduMap.vue'
const pageSize=ref(6);
const pageNum=ref(0);
// const sortBy=ref(0);
// const sortOrder=ref(0);

// const route=useRoute();
// const router=useRouter();

const themeVars = ref({
  cellPadding:"0px 0px",
  primaryColor: "#acc2a9",
  primaryColorEnd: "#acc2a9",
});
const query_content = ref("");

const boxes=ref([]);


const convert=(response)=>{

  for(let i=0;i<response.length;i++) {
    var data = response[i];
    var tempData = {
      id: "",
      name: "PEANUTS奶油盲盒系列",
      address: "吉事花生鞍山新村二店",
      url: ["url", "url", "url"],
      main_url:"",
      introduction: "好，真好，太好了",
      price: 1.1,
      left: 2
    };

    tempData.id=String(data.mystery_box_ID);
    tempData.name=data.com_name;
    tempData.address=data.user_address;
    for(let i=0;i<3;++i){
      tempData.url[i]='https://007-food.obs.cn-east-3.myhuaweicloud.com/'+data.contain_images[i].com_image
    }
    tempData.main_url='https://007-food.obs.cn-east-3.myhuaweicloud.com/'+data.item_image;
    tempData.introduction=data.com_introduction;
    tempData.price=data.com_oriPrice;
    tempData.left=data.com_left;

    boxes.value.push(tempData);
  }
}

const searchCommodity=()=>{
  pageNum.value++;
  axios.get('/api/mys/getmysterybox',{
    params:{
      mystery_box_ID:null,
      sto_ID:null,
      page_size:pageSize.value,
      page_num:pageNum.value,
      sort_by:0,
      sort_order:1
    }
  })
      .then(response=>{
        convert(response.data);
        console.log(response.data);
        if(response.data.length<pageSize.value){
          hasMore.value=false;
        }
      })
}

onMounted(()=>{
  // console.log("888");
  searchCommodity();
})

const userSearch=()=>{
  boxes.value=[];
  pageNum.value=0;
  hasMore.value=true;
  searchCommodity();
}

// const tabsValue = ref(0);
const sum = ref(24);
const infinityValue = ref(false);
const hasMore = ref(true);
const loadMore = () => {
  setTimeout(() => {
    sum.value = sum.value + 24;
    searchCommodity();
    infinityValue.value = false;
  }, 1000);
};


</script>

<style scoped>
</style>
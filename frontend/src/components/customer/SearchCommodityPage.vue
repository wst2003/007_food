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

    <nut-config-provider :theme-vars="themeVars">
      <nut-cell style="background: #f8fbf7;">
        <nut-popover v-model:visible="typeShow" location="right">
          <template #reference>
            <nut-button type="primary" shape="square"> 种类筛选 </nut-button>
          </template>

          <template #content>
            <nut-checkbox-group ref="group" v-model="state.checkboxgroup3" @change="changeBox4">
              <nut-checkbox v-for="item in state.checkboxsource" :key="item.label" :label="item.label">{{
                item.value
              }}</nut-checkbox>
            </nut-checkbox-group>
          </template>
        </nut-popover>
        <nut-button type="primary" shape="square" style="margin-left: 10px;" @click="showActionSheet=true"> 排序方式 </nut-button>
        
        <nut-action-sheet v-model:visible="showActionSheet" title="Title">
          <nut-picker v-model="value" :columns="columns" title="请选择时间" @confirm="confirm" />
        </nut-action-sheet>
      </nut-cell>



      <nut-row :gutter="5" v-for="commodity in commodityData" :key="commodity.id1">
        <nut-col :span="12">
          <nut-cell style="background: #e5f1e3;height:165px" @click="showDetail(commodity.id1,commodity.position1,commodity.distance1,commodity.price1,commodity.name1)">
            <div>
              <div>
                <nut-image style="margin-bottom: 5px;" :round="true" :radius="8" src="https://img10.360buyimg.com/ling/jfs/t1/181258/24/10385/53029/60d04978Ef21f2d42/92baeb21f907cd24.jpg" width="100%" height="100px" :fit="contain" />
                <nut-ellipsis style="font-family: Source Han Sans SC;
                  font-size: 14px;
                  font-weight: 400;
                  line-height: 16px;
                  letter-spacing: 1px;
                  text-align: left;
                  color:#131212;
                  margin-left: 15px;
                  width:120px"
                  :content="commodity.name1"
                  ></nut-ellipsis> 
                <span style="margin-left: 15px">
                  {{ "￥"+commodity.price1 }}
                </span>
                <!-- {{commodity.name1}}</span> -->
                <!-- <nut-ellipsis direction="start" :content="content" style="width:10px"></nut-ellipsis> -->
                <span style="
                  margin-left: 20px;
                  font-family: Source Han Sans SC;
                  font-size: 10px;
                  font-weight: 700;
                  line-height: 13px;
                  letter-spacing: 0px;
                  text-align: left;
                  ">
                {{ "剩余"+commodity.left1+"份" }}</span>
              </div>
              
              <span style="
                font-family: Source Han Sans SC;
                font-size: 10px;
                font-weight: 400;
                line-height: 16px;
                letter-spacing: 0px;
                text-align: left;
                margin-left: 15px;
                ">
              {{ commodity.position1 }}  </span>
              <nut-tag color="#8BA789" plain style="font-family: Source Han Sans SC;
                font-size: 11px;
                font-weight: 500;
                line-height: 13px;
                letter-spacing: 0px;
                text-align: left;
                margin-left: 10px;
                "> 
              {{ commodity.rating1 }} </nut-tag>
            </div>
            
          </nut-cell>
        </nut-col>

        <nut-col :span="12" v-if="commodity.id2!=-1">
          <nut-cell style="background: #e5f1e3;height:165px" @click="showDetail(commodity.id2,commodity.position2,commodity.distance2,commodity.price2,commodity.name2)">
            <div>
              <div>
                <nut-image style="margin-bottom: 5px;" :round="true" :radius="8" src="https://img10.360buyimg.com/ling/jfs/t1/181258/24/10385/53029/60d04978Ef21f2d42/92baeb21f907cd24.jpg" width="100%" height="100px" :fit="contain" />
                <nut-ellipsis style="font-family: Source Han Sans SC;
                  font-size: 14px;
                  font-weight: 400;
                  line-height: 16px;
                  letter-spacing: 1px;
                  text-align: left;
                  color:#131212;
                  margin-left: 15px;
                  width:120px"
                  :content="commodity.name2"
                  ></nut-ellipsis> 
                <!-- {{commodity.name1}}</span> -->
                <!-- <nut-ellipsis direction="start" :content="content" style="width:10px"></nut-ellipsis> -->

                <span style="margin-left: 15px">
                  {{ "￥"+commodity.price2 }}
                </span>
                <span style="
                  margin-left: 10px;
                  font-family: Source Han Sans SC;
                  font-size: 10px;
                  font-weight: 700;
                  line-height: 13px;
                  letter-spacing: 0px;
                  text-align: left;
                  ">
                {{ "剩余"+commodity.left2+"份" }}</span>
              </div>
              
              <span style="
                font-family: Source Han Sans SC;
                font-size: 10px;
                font-weight: 400;
                line-height: 16px;
                letter-spacing: 0px;
                text-align: left;
                margin-left: 15px;
                ">
              {{ commodity.position2 }}  </span>
              <nut-tag color="#8BA789" plain style="font-family: Source Han Sans SC;
                font-size: 11px;
                font-weight: 500;
                line-height: 13px;
                letter-spacing: 0px;
                text-align: left;
                margin-left: 10px;
                "> 
              {{ commodity.rating2 }} </nut-tag>
            </div>
            
          </nut-cell>
        </nut-col>
      </nut-row>
    </nut-config-provider>

    <!-- <div class="test" v-for="(item, index) in sum" :key="index">{{ index }}</div> -->
  </nut-infinite-loading>

  
</template>



<script setup>
import { onMounted, reactive,ref } from "vue";
import {
  Search2,
  Voice,
} from "@nutui/icons-vue";
import { useRouter } from "vue-router";
import axios from "axios";
import BaiduMap from '../BaiduMap.vue'
const pageSize=ref(6);
const pageNum=ref(0);
const sortBy=ref(0);
const sortOrder=ref(0);
const state = reactive({
  checkboxgroup3: ['2'],
  checkboxsource: [
    { label: '1', value: 'A' },
    { label: '2', value: 'B' },
    { label: '3', value: 'C' },
    { label: '4', value: 'D' },
    { label: '5', value: 'E' },
    { label: '6', value: 'F' },
    { label: '7', value: 'G' },
    { label: '8', value: 'H' }
  ]
});
const showActionSheet=ref(false);
const group = ref(null);
const changeBox4 = (label) => {
  console.log(`${label.length === state.checkboxsource.length ? '全选' : '取消全选'}`);
};

// const route=useRoute();
const router=useRouter();

const themeVars = ref({
  cellPadding:"0px 0px",
  primaryColor: "#acc2a9",
  primaryColorEnd: "#acc2a9",
});
const typeShow=ref(false);
const query_content = ref("");

const commodityData=ref([]);

const showDetail=(id,position,distance,price,name)=>{
  console.log(name)
  router.push({
    path:'/commodityDetail',
    query:{
      id:id,
      position:position,
      distance:distance,
      price:price,
      name:name
    }
  })
}

const convert=(response)=>{

  for(let i=0;i<response.length;i++){
    var tempData = {
      id1:"1",
      name1:"蓝莓燕麦曲奇",
      position1:"苹果花园鞍山新村二店",
      distance1:"2.2km",
      rating1:"4.9",
      price1:"5.2",
      left1:"2",
      id2:"-1",
      name2:"-1",
      position2:"-1",
      distance2:"-1",
      rating2:"-1",
      price2:"-1",
      left2:"-1"
    }

    tempData.id1=String(response[i].com_ID);
    tempData.name1=response[i].com_name;
    tempData.distance1=String(response[i].com_dist);
    tempData.left1=String(response[i].com_left);
    tempData.position1=response[i].com_position;
    tempData.price1=String(response[i].com_price);
    tempData.rating1=String(response[i].praise_rate);
    
    i+=1;
    if(i!=response.length){
      tempData.id2=String(response[i].com_ID);
      tempData.name2=response[i].com_name;
      tempData.distance2=String(response[i].com_dist);
      tempData.left2=String(response[i].com_left);
      tempData.position2=response[i].com_position;
      tempData.price2=String(response[i].com_price);
      tempData.rating2=String(response[i].praise_rate);
    }
    commodityData.value.push(tempData);
  }
}

const searchCommodity=()=>{
  pageNum.value++;
  axios.get('/api/com/searchCommodity',{
    params:{
      content:query_content.value,
      com_type:"",
      sort_by:sortBy.value,
      sort_order:sortOrder.value,
      page_size:pageSize.value,
      page_num:pageNum.value
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
  searchCommodity();
})

const userSearch=()=>{
  commodityData.value=[];
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


const value = ref(['Wednesday', 'Afternoon']);
const columns = ref([
  [
    { text: '价格', value: '0' },
    { text: '评分', value: '1' },
    { text: '上架时间', value: '2' },
  ],
  [
    { text: '升序', value: '0' },
    { text: '降序', value: '1' },
  ]
]);

const confirm = ({ selectedValue, selectedOptions }) => {
  console.log(selectedValue, selectedOptions);
  sortBy.value=value.value[0];
  sortOrder.value=value.value[1];
  userSearch();

  showActionSheet.value=false;
};
</script>

<style scoped>
</style>
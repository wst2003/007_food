<template>
  <nut-config-provider :theme-vars="themeVars">
    <nut-navbar title="商店库存管理" left-show @click-back="onClickBack"></nut-navbar>
    <div style="display: flex;justify-content: center;margin-top: 3%">
      <nut-radio-group v-model="date" direction="horizontal" @change="onDateChange">
        <nut-radio label="1" shape="button" size="mini">所有</nut-radio>
        <nut-radio label="2" shape="button" size="mini">保质期小于 1/2</nut-radio>
        <nut-radio label="3" shape="button" size="mini">保质期小于 1/4</nut-radio>
        <nut-radio label="4" shape="button" size="mini">已清库存</nut-radio>
      </nut-radio-group>
    </div>

    <nut-searchbar v-model="query">
      <template #rightout> <div @click="clearQuery">清空</div> </template>
      <template #rightin >
        <Search2 @click="clear();search()"/>
      </template>
    </nut-searchbar>

    <nut-tabs v-model="type" title-scroll title-gutter="15" name="tabName" @change="onTabChange" >
      <nut-tab-pane v-for="item in categoryList" :key="item" :title="`${item}`" :pane-key="item" v-show="false">

      </nut-tab-pane>
    </nut-tabs>
    <nut-infinite-loading v-model="infinityValue" :has-more="hasMore" @load-more="loadMore">
      <nut-row v-for="commodity in commodityList" :key="commodity.id">
        <nut-col :span="8" style="margin-top: 3%">
          <nut-image :src="commodity.image" width="100" height="100" fit="fill" />
        </nut-col>
        <nut-col :span="16" style="margin-top: 3%">
          <div v-if="date!=='4'">
            <div style="
                  font-family: 'Source Han Sans CN';
                  font-style: normal;
                  font-weight: 500;
                  font-size: 16px;
                  line-height: 24px;
                  color: #000000;
                  ">
              {{commodity.name}}
            </div>
            <div>
                  <span style="
                    font-family: 'Source Han Sans CN';
                    font-style: normal;
                    font-weight: 400;
                    font-size: 12px;
                    line-height: 24px;
                    color: #969696;
                    ">
                    {{"保质期剩余"}}
                  </span>
              <span style="
                    font-family: 'Source Han Sans CN';
                    font-style: normal;
                    font-weight: 400;
                    font-size: 12px;
                    line-height: 24px;
                    color: black;
                    ">
                    {{commodity.left_day}}
                  </span>
              <span style="
                    font-family: 'Source Han Sans CN';
                    font-style: normal;
                    font-weight: 400;
                    font-size: 12px;
                    line-height: 24px;
                    color: #969696;
                    ">
                    {{"天"}}
                  </span>

              <span style="
                    font-family: 'Source Han Sans CN';
                    font-style: normal;
                    font-weight: 400;
                    font-size: 12px;
                    line-height: 24px;
                    color: #969696;
                    margin-left: 5%;
                    ">
                    {{"库存剩余"}}
                  </span>

              <span style="
                    font-family: 'Source Han Sans CN';
                    font-style: normal;
                    font-weight: 400;
                    font-size: 12px;
                    line-height: 24px;
                    color: black;
                    ">
                    {{commodity.left}}
                  </span>

              <span style="
                    font-family: 'Source Han Sans CN';
                    font-style: normal;
                    font-weight: 400;
                    font-size: 12px;
                    line-height: 24px;
                    color: #969696;
                    ">
                    {{"件"}}
                  </span>
            </div>

            <div>
                  <span style="
                    font-family: 'Source Han Sans CN';
                    font-style: normal;
                    font-weight: 500;
                    font-size: 10px;
                    line-height: 24px;
                    color: #646464;
                    ">
                    {{"库存补充"}}
                  </span>
              <nut-input-number v-model="commodity.left" style="position:absolute;margin-top: 1%;margin-left: 3%" @add="onLeftChange(commodity.id,parseInt(commodity.left)+1)" @reduce="onLeftChange(commodity.id,parseInt(commodity.left)+1)" :min="0"/>
              <nut-button shape="square" type="primary" size="mini" style="position: absolute;margin-left: 30%" @click="onClear(commodity.id)">全部下架</nut-button>
            </div>
<!--            <div>-->
<!--                  <span style="-->
<!--                    font-family: 'Source Han Sans CN';-->
<!--                    font-style: normal;-->
<!--                    font-weight: 500;-->
<!--                    font-size: 10px;-->
<!--                    line-height: 24px;-->
<!--                    color: #646464;-->
<!--                    ">-->
<!--                      {{"价格调整"}}-->
<!--                  </span>-->
<!--              <nut-input-number v-model="commodity.price" style="position:absolute;margin-top: 1%;margin-left: 7.7%" @blur="onPriceChange(commodity.id,parseFloat(commodity.price))" decimal-places="2" disabled>-->
<!--                <template #left-icon>-->
<!--                  {{}}-->
<!--                </template>-->
<!--                <template #right-icon>-->
<!--                  {{}}-->
<!--                </template>-->
<!--              </nut-input-number>-->
<!--            </div>-->
          </div>
          <div v-else>
            <div style="
                  font-family: 'Source Han Sans CN';
                  font-style: normal;
                  font-weight: 500;
                  font-size: 16px;
                  line-height: 24px;
                  color: #000000;
                  ">
              {{commodity.name}}
            </div>
            <div>
              <span style="
                    font-family: 'Source Han Sans CN';
                    font-style: normal;
                    font-weight: 400;
                    font-size: 12px;
                    line-height: 24px;
                    color: #969696;
                    ">
                    {{"库存清理"}}
                  </span>

              <span style="
                    font-family: 'Source Han Sans CN';
                    font-style: normal;
                    font-weight: 400;
                    font-size: 12px;
                    line-height: 24px;
                    color: black;
                    ">
                    {{commodity.left}}
                  </span>

              <span style="
                    font-family: 'Source Han Sans CN';
                    font-style: normal;
                    font-weight: 400;
                    font-size: 12px;
                    line-height: 24px;
                    color: #969696;
                    ">
                    {{"件"}}
                  </span>
            </div>

<!--            <div>-->
<!--              <nut-button shape="square" type="primary" size="mini" style="position: absolute;margin-left: 0%" @click="onClear(commodity.id)">重新上架</nut-button>-->
<!--            </div>-->

          </div>
        </nut-col>
      </nut-row>
    </nut-infinite-loading>
  </nut-config-provider>
</template>


<script setup>
import {onMounted, ref} from "vue";
import { Search2 } from '@nutui/icons-vue'
import axios from "axios";
import {useRoute, useRouter} from "vue-router";
const BaseUrl = "http://localhost:8000"
const sto_id=sessionStorage.getItem('user_id');
const onClickBack = () => {
  router.go(-1);
}
const categoryList=ref(["全部","便当","水果","肉类","零食","饮品"])
const route=useRoute();
const router=useRouter();
// const tab=ref("面包");
const date=ref(route.query.date)
const query=ref('')
const type=ref("全部")
const pageSize=10;
const pageNum=ref(0);

const infinityValue = ref(false);
const hasMore = ref(true);
const loadMore = () => {
  setTimeout(() => {
    search();
    infinityValue.value = false;
  }, 1000);
};

const themeVars = ref({
  cellPadding:"0px 0px",
  primaryColor: "#93B090",
  primaryColorEnd: "#93B090",
});

const commodityList=ref([
  {
    id:0,
    name:"洽洽香瓜子",
    left:2,
    left_day:20,
    price:10.0,
    image:'https://img10.360buyimg.com/ling/jfs/t1/181258/24/10385/53029/60d04978Ef21f2d42/92baeb21f907cd24.jpg'
  },
  {
    id:0,
    name:"洽洽香瓜子",
    left:2,
    left_day:20,
    price:10.0,
    image:'https://img10.360buyimg.com/ling/jfs/t1/181258/24/10385/53029/60d04978Ef21f2d42/92baeb21f907cd24.jpg'
  }
])
const onDateChange=(nowDate)=>{
  console.log(nowDate);
  clear();
  search();
}

const onTabChange=(nowTab)=>{
  type.value=nowTab.title;
  console.log(type.value)
  clear();
  search()
}
const clearQuery=()=>{
  query.value=''
}

const convert=(datas)=>{
  console.log(datas)

  // console.log(datas)
  for(let i=0;i<datas.length;++i) {
    var data=datas[i];
    // console.log(data);
    let tempData = {
      id: 0,
      name: "洽洽香瓜子",
      left: 2,
      left_day: 20,
      price: 10.0,
      image: 'https://img10.360buyimg.com/ling/jfs/t1/181258/24/10385/53029/60d04978Ef21f2d42/92baeb21f907cd24.jpg'
    };

    tempData.id = data.com_ID;
    tempData.name = data.com_name;
    tempData.left = data.com_left;
    tempData.image = "https://007-food.obs.cn-east-3.myhuaweicloud.com/"+data.commodityImage;
    tempData.price = data.com_price;
    tempData.left_day = data.left_day;

    commodityList.value.push(tempData);
  }
  console.log(commodityList.value)
}

const search=()=>{
  console.log("search:"+sto_id);
  let proportion;
  if (date.value==='1')
    proportion=1.0;
  else if(date.value==='2')
    proportion=0.5;
  else if(date.value==='3')
    proportion=0.25;
  else
    proportion=0.0;

  let nowType=type.value;
  if(nowType==="全部")
    nowType=null;
  pageNum.value++;
  axios.get(BaseUrl+'/api/com/searchCommodity',{
    params:{
      content:query.value,
      com_type:nowType,
      sort_by:2,
      sort_order:0,
      page_size:pageSize,
      page_num:pageNum.value,
      sto_ID:sto_id,
      remaining_proportion:proportion
    }
    // params:{
    //   content:"",
    //   com_type:null,
    //   sort_by:2,
    //   sort_order:0,
    //   page_size:pageSize,
    //   page_num:pageNum.value,
    //   sto_ID:sto_id,
    //   remaining_proportion:proportion
    // }
  })
      .then(response=>{
        convert(response.data);
        if(response.data.length<pageSize){
          hasMore.value=false;
        }
      })
}

const onLeftChange=(com_id,com_left)=>{
  console.log(com_id,com_left);
  axios.post(BaseUrl+'/api/com/editCommodity',{
    "com_ID": com_id,
    "com_left": com_left,
    "off_shelf": false
  }).then(response=>{
    console.log(response.data)
  })
}

// const onPriceChange=(com_id,com_price)=>{
//   console.log(com_id,com_price);
// }

const onClear=(com_id)=>{
  axios.post(BaseUrl+'/api/com/editCommodity',{
    "com_ID": com_id,
    "com_left": 0,
    "off_shelf": true
  }).then(response=>{
    console.log(response.data)
    clear();
    search();
  })
}

onMounted(()=>{
  clear();
  search();
})

const clear=()=>{
  commodityList.value=[];
  pageNum.value=0;
  hasMore.value=true;
}
</script>


<style scoped>

</style>
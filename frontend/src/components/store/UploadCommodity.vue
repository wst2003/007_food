<template>
    <nut-navbar title="临期商品上传" left-show @click-back="goBack"></nut-navbar>
    <nut-form style="
    position:relative;
    width: 100vw;
    height: 180vh;
    flex-shrink: 0;
    border-radius: 4px;
    background: #FBFCFA;">
        <nut-form-item label="商品名称">
            <nut-input v-model="formData.com_name" placeholder="请输入商品名称"></nut-input>
        </nut-form-item>
        <nut-form-item label="商品类别">
            <nut-button style="
            display: flex;
            width: 200px;
            height: 35px;
            flex-direction: column;
            justify-content: center;
            flex-shrink: 0;"
            @click="showType=true">{{commodityType}}</nut-button>
        </nut-form-item>
        <nut-form-item label="商品数量">
            <nut-input-number v-model="formData.com_left" />
        </nut-form-item>
        <nut-form-item label="商品简介">
            <div>
                <nut-textarea style="
                border-radius: 4px;
                background: #F8FBF7;" 
                placeholder="请输入商品简介" 
                v-model="formData.introduction" 
                limit-show :max-length="200" :rows="5" />
            </div>
        </nut-form-item>
        <nut-form-item label="商品生产日期">
            <nut-button @click="show_produceDate_pick = true">{{pickProduceDate}}</nut-button>
        </nut-form-item>
        <nut-form-item label="商品保质期">
            <nut-button @click="show_expirationDate_pick = true">{{pickexpirationDate}}</nut-button>
        </nut-form-item>
        <nut-form-item label="商品图片">
            <nut-uploader
              v-model:file-list="formData.images"
              accept="image/*"
              multiple
              :auto-upload="false"
              maximum="5"
              url='/api/sto/uploadImage'
              :data="addPicData"
              ref="uploadRef"
              name='images'
            >
            </nut-uploader>
          </nut-form-item>
        <nut-form-item label="原始价格">
            <nut-input v-model="formData.com_oriPrice" placeholder="请输入原始价格" type="number" />
        </nut-form-item>
        <nut-form-item label="价格节点设置">
            <nut-space v-for="(row, index) in formData.price_curve" :key="index">
                <nut-button
                style="
                width:120px;height:30px;
                font-size:xx-small;"
                @click="showPriceNodePick(index)"
                @blur="convert"
                >
                {{row.com_pc_time.getFullYear().toString()+'-'+(row.com_pc_time.getMonth()+1).toString()+'-'+row.com_pc_time.getDate().toString()}}
                </nut-button>
                <nut-input  v-model="row.com_pc_price" placeholder="请输入当前节点价格" type="number" @blur="convert"></nut-input>
            </nut-space>
            <nut-space :gutter="20">
                <nut-button @click="addRow" style="
                width:100px;">添加节点</nut-button>
                <nut-button @click="removeRow" style="
                width:100px;" @blur="convert">移除节点</nut-button>
            </nut-space>
        </nut-form-item>
        <div id="main" style="width:auto;height: 300px;"></div>
        <nut-space style="
        position:relative;margin-top:0vh;margin-left:5vw;">
            <nut-button style="
            width: 168px;
            height: 40px;
            flex-shrink: 0;
            border-radius: 5px;
            border: 0.5px solid #E6E6E6;
            background: #FFF;">
            快速批量发布</nut-button>
            <nut-button style="
            width: 168px;
            height: 40px;
            flex-shrink: 0;
            border-radius: 5px;
            background: #93B090;
            color:white;" @click="addCommodity">
            确定添加</nut-button>
        </nut-space>
    </nut-form>


    <nut-popup v-model:visible="showType" @close="selectTypeClose">
        <nut-checkbox-group v-model="pickType" style="display:flex;flex-direction:column;">
            <nut-checkbox v-for="(item,index) in categoryType" :key='index' :label=item> {{ item }} </nut-checkbox>
        </nut-checkbox-group>
    </nut-popup>

    <nut-popup v-model:visible="show_produceDate_pick" position="bottom">
        <nut-date-picker
          v-model="formData.com_producedDate"
          :three-dimensional="false"
          @confirm="confirm_produceDate_pick"
        ></nut-date-picker>
    </nut-popup>

    <nut-popup v-model:visible="show_expirationDate_pick" position="bottom">
        <nut-date-picker
          v-model="formData.com_expirationDate"
          :three-dimensional="false"
          @confirm="confirm_expirationDate_pick"
        ></nut-date-picker>
    </nut-popup>

    <nut-popup v-model:visible="show_priceNode_pick" position="bottom">
        <nut-date-picker
          v-model="temp_node"
          :three-dimensional="false"
          @confirm="confirm_priceNode_pick"
        ></nut-date-picker>
    </nut-popup>
</template>

<script lang="js" setup>

import {ref} from 'vue';
import { useRouter } from 'vue-router';
import * as echarts from 'echarts';
import { onMounted } from 'vue';
// import globalData from"../../global.js"
import axios from 'axios';
const uploadRef = ref(null);
const categories=ref([])
const router=useRouter();
const addPicData=ref({com_id:0})
const formData=ref({
    com_name:' ',
    com_type:0,
    com_left:0,
    com_expirationDate:new Date(),
    com_oriPrice:0.00,
    com_producedDate:new Date(),
    categories:[],
    images:[],
    price_curve:[{com_pc_time:new Date(),com_pc_price:0.0}],
    introduction:''

})
const categoryType=ref(["水果",'饼干','熟食'])
const temp_node=ref(new Date())
const temp_index=ref()

const pickType=ref([])
const pickProduceDate=ref('请选择生产日期')
const pickexpirationDate=ref('请选择保质日期')

const commodityType=ref('请选择商品类别');
const goBack=()=>{
        router.go(-1);
    }

const showType=ref(false)
const show_produceDate_pick=ref(false)
const show_expirationDate_pick=ref(false)
const show_priceNode_pick=ref(false)

const transformDateString=(date)=>{
    if(date.getMonth().toString()==0)
    return date.getFullYear().toString()+'-12-'+date.getDate().toString()
    else if(date.getMonth().toString()<10)
    return date.getFullYear().toString()+'-0'+date.getMonth().toString()+'-'+date.getDate().toString()
    else
    return date.getFullYear().toString()+'-'+date.getMonth().toString()+'-'+date.getDate().toString()
}
const selectTypeClose=()=>{
    console.log(pickType.value)
    commodityType.value='';
    for(let i in pickType.value){
        console.log(i)
        commodityType.value+=pickType.value[i]+' '
    }
    for(let i in pickType.value){
        categories.value.push({'com_category':pickType.value[i]})
    }
    if(commodityType.value===''){
        commodityType.value='请选择商品类别'
    }
}

const confirm_produceDate_pick=()=>{
    let pickDay=formData.value.com_producedDate
    pickDay.setMonth(pickDay.getMonth()+1)
    pickProduceDate.value=transformDateString(pickDay)
    show_produceDate_pick.value=false
}

const confirm_expirationDate_pick=()=>{
    let pickDay=formData.value.com_expirationDate
    pickDay.setMonth(pickDay.getMonth()+1)
    pickexpirationDate.value=transformDateString(pickDay)
    show_expirationDate_pick.value=false
}

const confirm_priceNode_pick=()=>{
    console.log(formData.value.price_curve[temp_index.value])
    formData.value.price_curve[temp_index.value].com_pc_time=temp_node.value;
    show_priceNode_pick.value=false;
}

const addRow=()=> {
           formData.value.price_curve.push({com_pc_time:new Date(),com_pc_price:0.0});
        }
const removeRow=()=> {
            if (formData.value.price_curve.length > 1) {
                formData.value.price_curve.pop();
            }
        }

const showPriceNodePick=(index)=>{
    console.log(index)
    temp_index.value=index;
    show_priceNode_pick.value=true;
}

var myChart;
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

const convert=()=>{
  
  option.xAxis.data=[];
  option.series[0].data=[];
  for(let i=0;i<formData.value.price_curve.length;++i){
    var nowCurve=formData.value.price_curve[i];
    console.log(nowCurve.com_pc_time.getDate())
    var date=nowCurve.com_pc_time.getFullYear().toString()+'-'+nowCurve.com_pc_time.getMonth().toString()+'-'+nowCurve.com_pc_time.getDay().toString()
    option.xAxis.data.push(date);
    option.series[0].data.push(nowCurve.com_pc_price);
  
  }
  myChart.setOption(option);
}

onMounted(()=>{
    myChart = echarts.init(document.getElementById('main'));
})

const addCommodity=()=>{
    var stoID=sessionStorage.getItem("user_id")
    var com_expirationDate=transformDateString(formData.value.com_expirationDate)
    var com_producedDate=transformDateString(formData.value.com_producedDate)
    var price_curve=[]
    for(let i=0;i<formData.value.price_curve.length;++i){
        var nowCurve=formData.value.price_curve[i];
        var date=transformDateString(nowCurve.com_pc_time)+' 00:00:00'
        price_curve.push({com_pc_time:date,com_pc_price:nowCurve.com_pc_price});
    }
    
    console.log(categories.value)
    axios.post('/api/sto/uploadRegularCommodity',  JSON.stringify({ 
            com_name:formData.value.com_name,
            com_introduction:formData.value.introduction,
            com_left:formData.value.com_left,
            sto_ID:stoID,
            com_type:0,
            com_oriPrice:formData.value.com_oriPrice,
            praise_rate:1,
            com_expirationDate:com_expirationDate,
            com_producedDate:com_producedDate,
            price_curve:price_curve,
            categories:categories.value
          }), {
          headers: {
              'Content-Type': 'application/json'
          }
          })
          .then(response => {
            addPicData.value.com_id=response.data.com_Id
            console.log(addPicData.value.com_id)
            uploadRef.value.submit();
          })
          .catch((error) => {
              console.log('An error occurred:', error);
          });
          
}
</script>

<style scoped>
:root {
    --nut-uploader-picture-width: 20px; 
    --nut-uploader-picture-height: 20px; 
  }
</style>
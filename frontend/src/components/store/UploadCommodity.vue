<template>
    <nut-navbar title="临期商品上传" left-show @click-back="goBack"></nut-navbar>
    <nut-form style="
    position:relative;
    width: 100vw;
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
                {{row.com_pc_time.getFullYear().toString()+'-'+row.com_pc_time.getMonth().toString()+'-'+row.com_pc_time.getDate().toString()}}
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


    <nut-popup style="border-radius: 5px;" v-model:visible="showType" @close="selectTypeClose">
        <nut-checkbox-group v-model="pickType" style="display:flex;flex-direction:column;">
            <nut-checkbox style="margin-top:1vh;margin-left:2vw;" v-for="(item,index) in categoryType" :key='index' :label=item> {{ item }} </nut-checkbox>
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
    <nut-popup v-model:visible="showBottom" round position="bottom" style="justify-content: center;align-items: center;" :style="{ height: '20%' }">
        <div style="position:absolute;top:30%;left:30%;">{{ mess }}</div>
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
const addPicData=ref({'com_id':'0'})
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

const showBottom=ref(false);
const mess=ref('')

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
    formData.value.price_curve[temp_index.value].com_pc_time.setMonth(formData.value.price_curve[temp_index.value].com_pc_time.getMonth()+1)
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
    var date=transformDateString(nowCurve.com_pc_time)
    option.xAxis.data.push(date);
    option.series[0].data.push(nowCurve.com_pc_price);
  
  }
  myChart.setOption(option);
}

onMounted(()=>{
    myChart = echarts.init(document.getElementById('main'));
    axios.get('/api/com/getCategories', {
    }).then((res) => {
      categoryType.value=res.data
    })
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
            const formDataPic = new FormData();
            var count=0;
            formData.value.images.forEach((file) => {
                console.log(file.url);
                const pic=dataURLtoFile(file.url,'pic'+count.toString()+'.jpg')
                formDataPic.append('images', pic); // 将文件添加到FormData中
                count++;
            });
            formDataPic.append('com_id',response.data.com_Id)
            axios.post('/api/sto/uploadImage',  formDataPic, {
            headers: {
                'Content-Type': 'multipart/form-data'
                }
                })
                .then(response => {
                    console.log(response.data)
                    if(response.data==='成功上传图片'){
                        baseClick("成功上传商品")
                        goBack()
                    }else{
                        baseClick("未成功上传商品！")
                    }
                })
          })
          .catch((error) => {
              console.log('An error occurred:', error);
          });
          
}

function dataURLtoFile(dataurl, filename) {
    // 获取到base64编码
    const arr = dataurl.split(',')
    // 将base64编码转为字符串
    const bstr = window.atob(arr[1])
    let n = bstr.length
    const u8arr = new Uint8Array(n) // 创建初始化为0的，包含length个元素的无符号整型数组
    while (n--) {
        u8arr[n] = bstr.charCodeAt(n)
    }
    return new File([u8arr], filename, {
        type: 'image/jpeg',
    })
}

const baseClick = (message) => {

    showBottom.value=true;
    mess.value=message
};
</script>

<style scoped>
:root {
    --nut-uploader-picture-width: 20px; 
    --nut-uploader-picture-height: 20px; 
  }
</style>
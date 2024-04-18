<template>
    <nut-navbar title="商品盲盒上传" left-show @click-back="goBack"></nut-navbar>
    <nut-form style="
    position:relative;
    width: 100vw;
    flex-shrink: 0;
    border-radius: 4px;
    background: #FBFCFA;">
        <nut-form-item label="盲盒名称">
            <nut-input v-model="formData.com_name" placeholder="请输入盲盒名称"></nut-input>
        </nut-form-item>
        <nut-form-item label="盲盒类别">
            <nut-button style="
            display: flex;
            width: 200px;
            height: 35px;
            flex-direction: column;
            justify-content: center;
            flex-shrink: 0;"
            @click="showType=true">{{commodityType}}</nut-button>
        </nut-form-item>
        <nut-form-item label="盲盒数量">
            <nut-input-number v-model="formData.com_left" />
        </nut-form-item>
        <nut-form-item label="盲盒简介">
            <div>
                <nut-textarea style="
                border-radius: 4px;
                background: #F8FBF7;" 
                placeholder="请输入盲盒简介" 
                v-model="formData.introduction" 
                limit-show :max-length="200" :rows="5" />
            </div>
        </nut-form-item>
        <nut-form-item label="盲盒上架日期日期">
            <nut-button @click="show_produceDate_pick = true">{{pickProduceDate}}</nut-button>
        </nut-form-item>
        <nut-form-item label="盲盒图片">
            <nut-uploader
              v-model:file-list="formData.images"
              accept="image/*"
              multiple
              :auto-upload="false"
              maximum="5"
              url='/api/sto/uploadImage'
              :data="addPicData"
              :ref="uploadRef"
              name='images'
            >
            </nut-uploader>
          </nut-form-item>
        <nut-form-item label="盲盒价格">
            <nut-input v-model="formData.com_oriPrice" placeholder="请输入盲盒价格" type="number" />
        </nut-form-item>
        <nut-form-item label="盲盒最高价格">
            <nut-input v-model="formData.highest_price" placeholder="请输入盲盒最高价格" type="number" />
        </nut-form-item>
        <nut-form-item label="盲盒最低价格">
            <nut-input v-model="formData.lowest_price" placeholder="请输入盲盒最低价格" type="number" />
        </nut-form-item>
        <nut-form-item label="盲盒包含的商品数量">
            <nut-input-number v-model="formData.category_count" />
        </nut-form-item>
        <nut-button style="
            position:relative;
            margin-left:30vw;
            width: 168px;
            height: 40px;
            flex-shrink: 0;
            border-radius: 5px;
            background: #93B090;
            color:white;" @click="addCommodity">
            确定添加</nut-button>
    </nut-form>


    <nut-popup style="border-radius: 5px;" v-model:visible="showType" @close="selectTypeClose">
        <nut-checkbox-group v-model="pickType" style="display:flex;flex-direction:column;height:25vh;border-radius: 5px;">
            <nut-checkbox style="margin-top:1vh;margin-left:2vw;" v-for="(item,index) in categoryType" :key='index' :label=item> {{ item }} </nut-checkbox>
        </nut-checkbox-group>
    </nut-popup>

    <nut-popup v-model:visible="show_produceDate_pick" position="bottom">
        <nut-date-picker
          v-model="formData.com_uploadDate"
          :three-dimensional="false"
          @confirm="confirm_produceDate_pick"
        ></nut-date-picker>
    </nut-popup>
    <nut-popup v-model:visible="showBottom" round position="bottom" style="justify-content: center;align-items: center;" :style="{ height: '20%' }">
        <div style="position:absolute;top:30%;left:30%;">{{ mess }}</div>
    </nut-popup>
</template>

<script lang="js" setup>

import {ref} from 'vue';
import { useRouter } from 'vue-router';
// import globalData from"../../global.js"
import axios from 'axios';
import { onMounted } from 'vue';
const router=useRouter();
const formData=ref({
    com_name:' ',
    com_type:1,
    com_left:0,
    com_oriPrice:0.00,
    com_uploadDate:new Date(),
    categories:[],
    images:[],
    introduction:'',
    highest_price:0,
    lowest_price:0,
    category_count:0
})
const addPicData={com_id:'0'}
const uploadRef = ref(null);
const categoryType=ref(["水果",'饼干','熟食'])

const pickType=ref([])
const pickProduceDate=ref('请选择上架日期')

const commodityType=ref('请选择盲盒类别');

const showBottom=ref(false);
const mess=ref('')

onMounted(()=>{
    axios.get('/api/com/getCategories', {
    }).then((res) => {
      categoryType.value=res.data
    })
})

const goBack=()=>{
        router.go(-1);
    }

const transformDateString=(date)=>{
    if(date.getMonth().toString()<9)
    return date.getFullYear().toString()+'-0'+(date.getMonth()+1).toString()+'-'+date.getDate().toString()
    else
    return date.getFullYear().toString()+'-'+(date.getMonth()+1).toString()+'-'+date.getDate().toString()
}
const showType=ref(false)
const show_produceDate_pick=ref(false)
const categories=ref([])
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
    let pickDay=formData.value.com_uploadDate
    pickProduceDate.value=transformDateString(pickDay)
    show_produceDate_pick.value=false
}

const addCommodity=()=>{
    var stoID=sessionStorage.getItem("user_id")
    var com_uploadDate=transformDateString(formData.value.com_uploadDate)
    axios.post('/api/sto/uploadMysteryBox',  JSON.stringify({ 
            com_name:formData.value.com_name,
            com_introduction:formData.value.introduction,
            com_left:formData.value.com_left,
            sto_ID:stoID,
            com_type:1,
            com_oriPrice:formData.value.com_oriPrice,
            praise_rate:1,
            com_uploadDate:com_uploadDate,
            highest_price:formData.value.highest_price,
            lowest_price:formData.value.lowest_price,
            category_count:formData.value.category_count,
            categories:categories.value
          }), {
          headers: {
              'Content-Type': 'application/json'
          }
          })
          .then(response => {
            // addPicData.value.com_id=response.data.com_Id
            // console.log(addPicData.value.com_id)
            const formDataPic = new FormData();
            var count=0;
            formData.value.images.forEach((file) => {
                console.log(file.url);
                const pic=dataURLtoFile(file.url,'pic'+count.toString()+'.jpg')
                formDataPic.append('images', pic); // 将文件添加到FormData中
                count++;
            });
            formDataPic.append('com_id',response.data.com_ID)
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
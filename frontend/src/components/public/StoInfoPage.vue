<template>
  <nut-config-provider :theme-vars="themeVars">
    <div class="background"></div>
    <nut-navbar title="个人信息" left-show @click-back="goBack"></nut-navbar>
    <nut-avatar size="large" style="margin-left:40vw;margin-top:5vh;">
      <img :src="formData.user_logo" />
    </nut-avatar>
      <nut-form style="margin-top:10vh;opacity:0.9;">
          <nut-form-item label="手机号">
            <nut-input v-model="formData.user_phone" placeholder="请输入姓名" type="text" :disabled="true" />
          </nut-form-item>
          <nut-form-item label="用户地址">
            <nut-input v-model="formData.user_address" placeholder="请输入年龄" type="text" :disabled="true"/>
          </nut-form-item>
          <nut-form-item label="营业时间">
            <nut-input v-model="openTime" placeholder="请输入地址" type="text" :disabled="true"/>
          </nut-form-item>
          <nut-form-item label="商店介绍">
            <nut-textarea v-model="formData.sto_introduction" placeholder="请输入备注" type="text" :disabled="true"/>
          </nut-form-item>
        </nut-form>
        <nut-button class="confirm-button" @click="quit" primary>
          退出登录
      </nut-button>
  </nut-config-provider>
</template>
<script lang="js" setup>
import axios from 'axios';
const BaseUrl = "http://localhost:8000"
import { onMounted } from 'vue';
import { ref } from 'vue'
const formData = ref({
  user_phone: '',
  user_address: '',
  sto_introduction: '',
  sto_openingTime:'',
  sto_closingTime:''
})

const themeVars = ref({
      avatarLargeHeight:'90px',
      avatarLargeWidth:'90px',
    });

const openTime=ref('')

import {  useRouter } from 'vue-router';
// import globalData from"../../global.js"
const router=useRouter();
const goBack=()=>{
  router.go(-1);
}
onMounted(()=>{
  axios.get(BaseUrl+'/api/sto/informationdetail',{
      params: {
        sto_ID:sessionStorage.getItem("user_id")
      }
    }, {
          headers: {
              'Content-Type': 'application/json'
          }
        })
        .then(response=>{
            formData.value.user_phone=response.data[0].user_phone
            formData.value.user_address=response.data[0].user_address
            formData.value.sto_introduction=response.data[0].sto_introduction
            var sto_openingTime=new Date(response.data[0].sto_openingTime)
            var sto_closingTime=new Date(response.data[0].sto_closingTime)
            openTime.value=sto_openingTime.getHours().toString()+' : '+sto_openingTime.getMinutes().toString()+' - '
            +sto_closingTime.getHours().toString()+' : '+sto_closingTime.getMinutes().toString()
        })
})
const quit=()=>{
  axios.post(BaseUrl+'/api/pub/login/quit',  JSON.stringify({ 
              user_id:sessionStorage.getItem("user_id")
            }), {
            headers: {
                'Content-Type': 'application/json'
            }
            })
            .then(response => {
                console.log('Login submitted successfully.');
                if(response.data.msg==='用户退出') {
                    router.push({
                        path: '/login',
                    });
                  }
            })    
            .catch((error) => {
                console.log('An error occurred:', error);
            });
}
</script>
<style scoped>
.background{
  position:absolute;
  top:0%;
  left:0%;
  flex-shrink: 0;
  background: linear-gradient(180deg,#FFFFFF 0%, #748865a8 100%),url('../../assets/images/image-49.png'), rgba(255, 255, 255, 1) 0px -126.926px / 100% 139.564% no-repeat;
  opacity: 60%;
  background-blend-mode: darken;
  background-size:contain;
  background-position:40,center;
  height: 100vh;
  width: 100vw;
  padding-top: 70px; /* 添加顶部填充以避免标题栏遮挡内容 */
  -webkit-user-drag: none;
}
.confirm-button{
  position: absolute;
  margin-top:10vh;
  left:10%;
  width: 305px;
  height: 50px;
  border-radius: 4px;
  background: #db0808;
  font-family: "Source Han Sans SC";
  font-size: 18px;
  font-style: normal;
  font-weight: 700;
  color: #FFF;
  line-height: normal;
  letter-spacing: 2px;
}
</style>
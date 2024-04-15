<template>
    <div class="total-layout">
      <div class="hello-bar">
        Hello !
      </div>
      <div class="select-bar">
        请选择你的身份进行注册~
      </div>
      <nut-button plain class="button-bar-1" @click="click_cus">我是买家</nut-button>
      <nut-button plain class="button-bar-2" @click="click_sto">我是卖家</nut-button>
    </div>
  </template>
  <script setup lang="js">
import router from '../../../router/index.js';

    const click_cus=()=>{
      router.push({name:'cusregister'});
    } 

    const click_sto=()=>{
      router.push({name:'storegister'});
    }
  // import { ref } from 'vue';
  // const show = ref(false);
  // const click = () => {
  //   show.value = true;
  // };
  // import {ref,shallowRef} from 'vue';
  // import { useRouter } from 'vue-router';
  // import codelogin from './CodeLoginPage.vue'
  // import phonelogin from './PhoneLoginPage.vue'
  
  // const dialogVisible=ref(false);
  // // const text=ref('');
  //   const router=useRouter()
  //   const label_default=ref('ID登录')
  //   const tabs=[
  //         { name: 'ID登录', component: codelogin },
  //         { name: '电话号码登录', component: phonelogin },
  //   ]
  //   const selectedTab=shallowRef(codelogin)
    
  //   const selectTab=(index)=> {
  //       console.log(index)
  //       selectedTab.value = tabs[index].component;
  //       console.log(selectedTab.value)
  //     }
    
  //   const toRegister=()=>{
  //       //router.push({name:'register'})
  //       /*-------------------- */
  //       /*跳转到注册界面修改此处 */
  //       router.push({name: 'userregister'}); // 跳转到注册界面
  //       /*-------------------- */
  //   }
  
  //   const toFindPassword=()=>{
  //       router.push({name:'findpassword'})
  //   }
  </script>
  
  <style scoped>
  .hello-bar{
    display: flex;
    width: 308px;
    height: 34px;
    margin-top:60%;
    flex-direction: column;
    justify-content: center;
    flex-shrink: 0;
    color: #FFF;
    font-family: "Abhaya Libre";
    font-size: 64px;
    font-style: normal;
    font-weight: 700;
    line-height: 21px;
  }
  .select-bar{
    display: flex;
    width: 308px;
    height: 70px;
    flex-direction: column;
    justify-content: center;
    flex-shrink: 0;
    color: #FFF;
    font-family: "Source Han Sans SC";
    font-size: 20px;
    font-style: normal;
    font-weight: 500;
    line-height: 21px;
    letter-spacing: 2px;
  }
  .button-bar-1{
    width: 308px;
    height: 50px;
    top: 80%;
    border-radius: 4px;
    background: #F7F7F7;
    flex-shrink: 0;
    color: #000;

    text-align: center;
    font-family: "Source Han Sans SC";
    font-size: 18px;
    font-style: normal;
    font-weight: 700;
    line-height: normal;
    letter-spacing: 3px;
  }
  .button-bar-2{
    width: 308px;
    height: 50px;
    margin-top: 10%;
    border-radius: 4px;
    background: #F7F7F7;
    flex-shrink: 0;

    text-align: center;
    font-family: "Source Han Sans SC";
    font-size: 18px;
    font-style: normal;
    font-weight: 700;
    line-height: normal;
    letter-spacing: 3px;
  }
  
  .total-layout {
    flex-shrink: 0;
    background: linear-gradient(180deg, rgba(196, 196, 196, 0.00) 0%, #748865 105%),url('../../assets/images/image-49.png'), lightgray 0px -126.926px / 100% 139.564% no-repeat;
    background-blend-mode: darken;
    
    background-size: cover;
    background-position: center center;
    min-height: 90vh;
    min-width: 50vh;
    display: flex;
    flex-direction: column;
    align-items: center;
    justify-content: center;
    padding-top: 70px; /* 添加顶部填充以避免标题栏遮挡内容 */
    -webkit-user-drag: none;
  }
  </style>
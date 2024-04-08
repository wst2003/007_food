<template>
  <router-view v-slot="{ Component,route }">
    <keep-alive >
      <component v-if="route.meta.keepAlive" :is="Component" :key="currentActiveIndex"/>
    </keep-alive>
    <component v-if="!route.meta.keepAlive" :is="Component" :key="currentActiveIndex"/>
  </router-view>
  <nut-config-provider :theme-vars="themeVars">
    <nut-tabbar class="tabbar" v-model="active" @tab-switch="tabSwitch" bottom safe-area-inset-bottom placeholder>
      <nut-tabbar-item
          v-for="(item, index) in List"
          :key="index"
          :tab-title="item.title"
          :icon="item.icon"
      >
      </nut-tabbar-item>
    </nut-tabbar>
  </nut-config-provider>
<!-- UploaderSelector-->
  <nut-popup v-model:visible="show" position="bottom" closeable round style=" height: 40%;background-color:#93B090 ;">
        <div style="width: 100%; height: 100%; position: relative">
            <div style="width: 100%; height: 45%; position: relative;" @click="uploadCom"> 
                <div style="width: 100%; height: 100%;  left: 20px; top: 0px; position: absolute; justify-content: flex-start; align-items: center; gap: 20px; display: inline-flex">
                    <img style="width: 60px; height: 60px; border-radius: 9999px" src="../../assets\store_uploadcommodity_logo.png" />
                    <div style="width: 60%; align-self: stretch; padding-right: 28px; flex-direction: column; justify-content: center; align-items: flex-start; gap: 7px; display: inline-flex">
                        <div><span style="color: black; font-size: 18px; font-family: Source Han Sans CN; font-weight: 700; line-height: 24px; word-wrap: break-word">上传</span><span style="color: white; font-size: 18px; font-family: Source Han Sans CN; font-weight: 700; line-height: 24px; word-wrap: break-word">临期商品</span></div>
                        <div style="color: white; font-size: 14px; font-family: Source Han Sans CN; font-weight: 400; word-wrap: break-word">预包装食品请在这里上传</div>
                    </div>
                </div>
            </div>
            <div style="width: 95%; height: 1px; margin:auto; border-radius: 0.12px; border: 1px #E6E6E6 solid"></div>
            <div style="width: 100%; height: 45%; position: relative;" @click="uploadMys">
                <div style="width: 100%; height: 100%; left: 20px; top: 0px; position: absolute;justify-content: flex-start; align-items: center; gap: 20px; display: inline-flex">
                    <img style="width: 60px; height: 60px; border-radius: 9999px" src="../../assets\store_uploadmystery_logo.png" />
                    <div style="width: 60%; align-self: stretch; padding-right: 28px; flex-direction: column; justify-content: center; align-items: flex-start; gap: 7px; display: inline-flex">
                        <div><span style="color: black; font-size: 18px; font-family: Source Han Sans CN; font-weight: 700; line-height: 24px; word-wrap: break-word">上传</span><span style="color: white; font-size: 18px; font-family: Source Han Sans CN; font-weight: 700; line-height: 24px; word-wrap: break-word">盲盒商品</span></div>
                        <div style=" color: white; font-size: 14px; font-family: Source Han Sans CN; font-weight: 400; word-wrap: break-word">面包熟食等非预包装食品请在这里上传</div>
                    </div>
                </div>
            </div>
            
            <!-- <div style="width: 95%; height: 1px; left: 16px; right: 16px;top: 45%; position: absolute; border-radius: 0.12px; border: 1px #E6E6E6 solid"></div>
            <div style="width: 95%; height: 1px; left: 16px; right: 16px;top: 95%; position: absolute; border-radius: 0.12px; border: 1px #E6E6E6 solid"></div> -->
        </div>
    </nut-popup>
</template>
<script setup lang="js">
import { h,ref } from 'vue';
import {
  Find,
  Cart,
  My, Home,
} from "@nutui/icons-vue";
import { useRouter } from 'vue-router';
const active = ref(null);
const show = ref(false);// To control visibility state of overlay
const List = [
  {
    title: "订单",
    icon: h(Cart),
    name: "indent",
  },
  {
    title: "商店",
    icon: h(Home),
    name: "home",
  },
  {
    title: "上传商品",
    icon: h(Find),
    name: "upload",
  },
  {
    title: "我的",
    icon: h(My),
    name: "my",
  },
];

const tabSwitch = (item, index) => {
  console.log(item, index);
  if(index === 0){
    router.push({
      path:'/indentmanagepage'
    })
  }
  if(index===1){
    // router.push({
    //   path:'/commodityManage',
    //   query:{
    //     date:'1'
    //   }
    // })
    router.push({
      path:'/storemanage'
    })
  }
  if(index === 2){
    // router.push({
    //   path:'/uploadCommodity'
    // })
    show.value=true
  }
  if(index===3){
    router.push({
      path:'/storemanage'
    })
  }
};

function uploadCom(){
    router.push('/uploadCommodity')
    console.log('选择上传商品')
    show.value=false
}

function uploadMys(){
    router.push('/uploadblindbox')
    console.log('选择上传盲盒')
    show.value=false
}
const themeVars = ref({
  primaryColor: "#008000",
  primaryColorEnd: "#008000",
});

const router=useRouter();
// onMounted(()=>{
//   router.push({
//     path:'/searchCommodity'
//   })
// })
</script>

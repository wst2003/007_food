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
  </template>
  <script setup lang="js">
  import { h,onMounted,ref } from 'vue';
  import {
    Home,
    Category,
    Find,
    Cart,
    My,
  } from "@nutui/icons-vue";
  import { useRouter } from 'vue-router';
  const active = ref(2);
  const List = [
    {
      title: "店铺",
      icon: h(Home),
      name: "home",
    },
    {
      title: "盲盒",
      icon: h(Category),
      name: "category",
    },
    {
      title: "首页",
      icon: h(Find),
      name: "find",
    },
    {
      title: "订单",
      icon: h(Cart),
      name: "cart",
    },
    {
      title: "我的",
      icon: h(My),
      name: "my",
    },
  ];
  
  const tabSwitch = (item, index) => {
    console.log(item, index);
    if(index === 2){
        router.push({
        path:'/searchCommodity'
      })
    }
    if(index === 0){
    router.push({
      path:'/storeDetail'
    })
  }
    if(index===1){
      router.push({
        path:'/searchBox'
      })
    }
    if(index===3){
      router.push({
        path:'/myIndent'
      })
    }
  };
  
  
  const themeVars = ref({
    primaryColor: "#008000",
    primaryColorEnd: "#008000",
  });
  
  const router=useRouter();
  onMounted(()=>{
    router.push({
      path:'/searchBox'
    })
  })
  </script>
  
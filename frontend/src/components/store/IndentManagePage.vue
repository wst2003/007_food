<template>
<nut-navbar title="临期商品上传" left-show @click-back="goBack"></nut-navbar>
<nut-config-provider :theme-vars="themeVars">
<nut-tabs v-model="value" align="left">
    <nut-tab-pane title="订单" pane-key="1">
        <nut-collapse>
            <nut-collapse-item name="状态" title="状态"> 
                
            </nut-collapse-item>
          </nut-collapse>
    </nut-tab-pane>
    <nut-tab-pane title="售后" pane-key="2"> 售后 </nut-tab-pane>
</nut-tabs>
</nut-config-provider>
</template>

<script setup>
import { useRouter } from 'vue-router';
import { ref } from 'vue';
const router=useRouter();
const value = ref('1');
const goBack=()=>{
        router.go(-1);
    }
const themeVars = ref({
    tabsHorizontalTabLineColor:'#93B090',
    tabsHorizontalTitlesHeight:'80px'
});
</script>

<style scoped>
</style>
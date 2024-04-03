<template>
    <nut-navbar :title="commodity_name" left-show @click-back="goBack"></nut-navbar>
    <div style="
        width: 310px;
        height: 113px;
        flex-shrink: 0;
        position:relative;
        top:7vh;
        left:10vw;
        border-radius: 17px;
        background: #BCBCBC;
        ">
        <div style="
            position:absolute;
            top:10%;
            left:30%;
            width: 115px;
            height: 19px;
            flex-shrink: 0;
            border-radius: 6px;
            background: #DCDCDC;
            color: #000;

            font-family: 'Source Han Sans SC';
            font-size: 14px;
            font-style: normal;
            font-weight: 400;
            line-height: normal;
            letter-spacing: 0.42px;
            text-align: center;">
            感谢
        </div>
        <div style="
            position:absolute;
            top:35%;
            left:10%;
            width: 253px;
            height: 26px;
            flex-shrink: 0;
            border-radius: 12px;
            background: #EEE;
            color: #000;
            font-family: 'Source Han Sans SC';
            font-size: 14px;
            font-style: normal;
            font-weight: 400;
            line-height: normal;
            letter-spacing: 0.42px;
            text-align: center;">
            对可持续的帮助
        </div>
        <div style="
            position:absolute;
            top:65%;
            left:20%;
            width: 168px;
            height: 15px;
            flex-shrink: 0;
            border-radius: 12px;
            background: #A1A1A1;
            color: #000;
            font-family: 'Source Han Sans SC';
            font-size: 14px;
            font-style: normal;
            font-weight: 400;
            line-height: normal;
            letter-spacing: 0.42px;
            text-align: center;">
            订单已下达
        </div>
    </div>
    <nut-space :gutter="20" style="position:relative;margin-top:10vh;margin-left:20vw;">
        <Clock size="60"></Clock>
        <div style="
            border-radius: 6px;
            background: #DCDCDC;
            width: 185px;
            height: 24px;
            flex-shrink: 0;
            color: #000;
            font-family: 'Source Han Sans SC';
            font-size: 14px;
            font-style: normal;
            font-weight: 400;
            line-height: normal;
            letter-spacing: 0.42px;
            text-align: center;">
            {{indentDetail.time}}</div>
    </nut-space>
    <nut-space :gutter="20" style="position:relative;margin-top:2vh;margin-left:20vw;">
        <Locationg3 size="60"></Locationg3>
        <div style="
            border-radius: 6px;
            background: #DCDCDC;
            width: 185px;
            height: 24px;
            flex-shrink: 0;
            color: #000;
            font-family: 'Source Han Sans SC';
            font-size: 14px;
            font-style: normal;
            font-weight: 400;
            line-height: normal;
            letter-spacing: 0.42px;
            text-align: center;">
            {{indentDetail.address}}</div>
    </nut-space>
    <nut-space :gutter="20" style="position:relative;margin-top:2vh;margin-left:20vw;">
        <Edit size="60"></Edit>
        <div style="
            border-radius: 6px;
            background: #DCDCDC;
            width: 185px;
            height: 24px;
            flex-shrink: 0;
            color: #000;
            font-family: 'Source Han Sans SC';
            font-size: 14px;
            font-style: normal;
            font-weight: 400;
            line-height: normal;
            letter-spacing: 0.42px;
            text-align: center;">
            {{indentDetail.identNumber}}</div>
    </nut-space>
    <div style="
    position:relative;
    margin-top:2vh;
    width: 396px;
    height: 447px;
    flex-shrink: 0;
    border-radius: 10px;
    background: #FBFCFA;
    box-shadow: 0px 0px 7.1px 0px rgba(0, 0, 0, 0.10);">
        <nut-steps direction="vertical" :current="2" 
        style="
        position:absolute;
        top:10%;
        left:10%;">
            <nut-step title="未开始" content="您的订单已经打包完成，商品已发出">1</nut-step>
            <nut-step title="进行中" content="您的订单正在配送途中">2</nut-step>
            <nut-step title="已完成" content="收货地址为：北京市经济技术开发区科创十一街18号院京东大厦">3</nut-step>
          </nut-steps>
    </div>
</template>

<script lang="js" setup>
import {ref} from 'vue';
import { Clock ,Locationg3,Edit} from '@nutui/icons-vue';
import {  useRouter } from 'vue-router';
const router=useRouter();
const commodity_name=ref('商品名称')
const indentDetail=ref({
    time:'时间',
    address:'地点',
    identNumber:'期货码'
})
const goBack=()=>{
  router.go(-1);
}
</script>

<style scoped>
</style>
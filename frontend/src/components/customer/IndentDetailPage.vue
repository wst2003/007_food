<template>
    <nut-config-provider :theme-vars="themeVars">
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
        <div v-if="indentDetail.type===0" style="
        position:relative;
        margin-top:2vh;
        width: 100vw;
        height: 447px;
        flex-shrink: 0;
        border-radius: 10px;
        background: #FBFCFA;
        box-shadow: 0px 0px 7.1px 0px rgba(0, 0, 0, 0.10);">
            <nut-steps direction="vertical" :current="indentDetail.status" 
            style="
            position:absolute;
            top:5%;
            left:20vw;
            width:100vw;">
                <nut-step title="待取货" content="您的订单待取货">2</nut-step>    
                <nut-step title="已核销" content="您的订单已核销">3</nut-step>
                <nut-step title="超期未取" content="您的订单待评价">4</nut-step>

            </nut-steps>
        </div>
        <div v-else style="
        position:relative;
        margin-top:2vh;
        width: 100vw;
        height: 447px;
        flex-shrink: 0;
        border-radius: 10px;
        background: #FBFCFA;
        box-shadow: 0px 0px 7.1px 0px rgba(0, 0, 0, 0.10);">
            <nut-steps direction="vertical" :current="indentDetail.status" 
            style="
            position:absolute;
            top:5%;
            left:20vw;
            width:100vw;">
                <nut-step title="未收货" content="您的订单还未收货">1</nut-step>
                <nut-step title="确认取货" content="您的订单确认取货">2</nut-step>            
            </nut-steps>
        </div>
    </nut-config-provider>
</template>

<script lang="js" setup>
import axios from 'axios'
import {onMounted, ref} from 'vue';
import { Clock ,Locationg3,Edit} from '@nutui/icons-vue';
import {  useRouter,useRoute } from 'vue-router';
const router=useRouter();
const route=useRoute();
const commodity_name=ref('商品名称')
const indentDetail=ref({
    time:'时间',
    address:'地点',
    identNumber:'期货码',
    status:'0',
    type:"0"
})

const themeVars = ref({
    primaryColor: '#99af93',
});

const goBack=()=>{
  router.go(-1);
}
onMounted(()=>{
    console.log( route.query.ind_id)
    axios.get('/api/cus/getIndById', {
      params: {
        ind_id: route.query.ind_id,
      }
    }).then(response => {
        indentDetail.value.time=response.data[0].ind_creationTime;
        indentDetail.value.address=response.data[0].delivery_address;
        indentDetail.value.identNumber=response.data[0].ind_verificationCode;
        indentDetail.value.type=response.data[0].delivery_method
        if(response.data[0].ind_state==0||response.data[0].ind_state==2)
            indentDetail.value.status=1
        else if(response.data[0].ind_state==1||response.data[0].ind_state==3)
            indentDetail.value.status=2
        else
            indentDetail.value.status=3
    })
    console.log("ind type: "+indentDetail.value.status)
})
</script>

<style scoped>
</style>
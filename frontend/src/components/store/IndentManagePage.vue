<template>
<nut-navbar title="临期商品上传" left-show @click-back="goBack"></nut-navbar>
<nut-config-provider :theme-vars="themeVars">
<nut-tabs v-model="value" align="left">
    <nut-tab-pane title="订单" pane-key="1" style="
    background: #F7F7F7;">
        <nut-cell title="请选择订单状态" :desc="reflectStatusToString(status_val)" @click="show = true" style="margin-top:-2vh;"></nut-cell>
        <nut-radio-group v-model="sendOrTake" direction="horizontal">
            <nut-radio label="0" shape="button">自取订单</nut-radio>
            <nut-radio label="1" shape="button">配送订单</nut-radio>
        </nut-radio-group>
        <div style="
        margin-top:2vh;
        border-radius: 4px;
        border: 10px  #000;
        background: #FFF;" 
        v-for="(item,index) in indInfo" :key="index">
            <div style="margin-left:2vw;margin-top:2vh;" v-if="item.delivery_method.toString()===sendOrTake && item.ind_state==status_val">
                <div style="margin-top:2vh;">
                    <nut-space>
                        <div style="
                        color: #323232;
                        font-family: 'Source Han Sans CN';
                        font-size: 16px;
                        font-style: normal;
                        font-weight: 700;
                        line-height: 24px; "
                        >取餐号</div>
                        <div style="
                        color: #323232;
                        font-family: 'Source Han Sans CN';
                        font-size: 24px;
                        font-style: normal;
                        font-weight: 700;
                        line-height: 24px;"
                        >{{item.ind_verificationCode}}</div>
                    </nut-space>
                    <br>
                    <nut-space style="margin-top:5%;">
                        <div style="
                        color: #646464;
                        font-family: 'Source Han Sans CN';
                        font-size: 12px;
                        font-style: normal;
                        font-weight: 400;
                        line-height: normal;
                        " >
                            拿取时间
                        </div>
                        <div style="
                        color: #93B090;
                        text-align: center;
                        font-family: 'Source Han Sans CN';
                        font-size: 9px;
                        font-style: normal;
                        font-weight: 400;
                        line-height: normal;
                        ">
                            {{ item.ind_creationTime }}
                        </div>
                    </nut-space>
                </div>
                <div>
                    <nut-space style="margin-top:5%;">
                        <div style="
                        display: flex;
                        width: 51px;
                        height: 22px;
                        flex-direction: column;
                        justify-content: center;
                        flex-shrink: 0;">
                            {{ item.cus_name }}
                        </div>
                        <div style="
                        color: #969696;
                        font-family: 'Source Han Sans CN';
                        font-size: 10px;
                        font-style: normal;
                        font-weight: 400;
                        line-height: 22px; " >
                            {{ item.cus_phone }}
                        </div>
                    </nut-space>
                    <div style="color: #969696;
                    font-family: 'Source Han Sans CN';
                    font-size: 10px;
                    font-style: normal;
                    font-weight: 400;
                    line-height: 22px;">
                        {{ item.delivery_address }}
                    </div>
                </div>
                <div>
                    <div style="
                    color: #323232;
                    font-family: 'Source Han Sans CN';
                    font-size: 12px;
                    font-style: normal;
                    font-weight: 700;
                    line-height: 22px;">
                        {{ item.cusTotalBuy }}
                    </div>
                    <nut-space>
                        <div>
                            预计收入
                        </div>
                        <div>
                            {{ item.totalMoney }}
                        </div>
                    </nut-space>
                </div>
                <nut-collapse>
                    <nut-collapse-item title="展开完整信息">
                        <div v-for="(com,index) in item.commodities" :key="index">
                            <nut-space>
                                <div>
                                    {{ com.com_name }}
                                </div>
                                <div>
                                    {{ '￥'+com.commodity_money }}
                                </div>
                            </nut-space>
                        </div>
                    </nut-collapse-item>
                </nut-collapse>
                <nut-divider dashed></nut-divider>
                
            </div>
        </div>
    </nut-tab-pane>
    <nut-tab-pane title="售后" pane-key="2"> 售后 </nut-tab-pane>
</nut-tabs>
</nut-config-provider>

<nut-popup v-model:visible="show" position="bottom">
    <nut-picker v-model="status_val" :columns="columns" title="请选择城市" @confirm="show=false" @cancel="show = false" />
</nut-popup>
</template>

<script setup>
import { useRouter } from 'vue-router';
import { ref } from 'vue';
import { onMounted } from 'vue';
// import globalData from"../../global.js"
// import axios from 'axios';
const router=useRouter();
const show=ref(false)
// const indList=ref([])
const indInfo=ref([])
const sendOrTake=ref('0')
const value = ref('1');
const goBack=()=>{
        router.go(-1);
    }
const themeVars = ref({
    tabsHorizontalTabLineColor:'#93B090',
    tabsHorizontalTitlesHeight:'80px',
    radioLabelFontActiveColor:'#93B090',
    radioLabelButtonBackground:'#93B090',
    radioLabelButtonBorderColor:'#93B090',
    radioIconDisableColor2:'#E6E6E6',
    collapseItemLineHeight:'18px'

});
const status_val=ref([0])

const columns = ref([
  { text: '未收货', value: 0 },
  { text: '确认收货', value: 1 },
  { text: '待取货', value: 2 },
  { text: '已核销', value: 3},
  { text: '超期未取', value: 4},
  { text: '待评价', value: 5}
]);

const reflectStatusToString=(val)=>{
    if(val==0)
        return '未收货'
    if(val==1)
        return '确认收货'
    if(val==2)
        return '待取货'
    if(val==3)
        return '已核销'
    if(val==4)
        return '超期未取'
    if(val==5)
        return '待评价'
}

onMounted(()=>{
//     axios.get('/api/sto/getIndentList',{
//     params:{
//         sto_id:parseInt(globalData.userInfo.user_id),
//     }
//   }).then(response=>{
//     indList.value=response.data
//     axios.get('/api/cus/getIndById',{
//     params:{
//         ind_id:indList.value,
//     }
//   }).then(response=>{
//     indInfo.value=response.data
    

//   })
//   })
indInfo.value=[
  {
    
    "ind_ID": 0,
    "cus_ID": 0,
    "ind_money": 0,
    "ind_creationTime": "2019-08-24T14:15:22Z",
    "ind_verificationCode": "1191",
    "ind_notes": "string",
    "ind_state": 0,
    "food_quality_score": 0,
    "service_quality_score": 0,
    "price_performance_ratio": 0,
    "delivery_method": 0,
    "delivery_address": "string",
    "delivery_altitude": 0,
    "delivery_longitude": 0,
    "commodities": [
      {
        "com_ID": 0,
        "ind_quantity": 0,
        "commodity_money": 10,
        "rating_type": 0,
        "com_name": "饼干"
      },
      {
        "com_ID": 1,
        "ind_quantity": 0,
        "commodity_money": 20,
        "rating_type": 0,
        "com_name": "月饼"
      }
    ],
    "com_position": "string"
  },
  {
    
    "ind_ID": 0,
    "cus_ID": 0,
    "ind_money": 0,
    "ind_creationTime": "2019-08-24T14:15:22Z",
    "ind_verificationCode": "1190",
    "ind_notes": "string",
    "ind_state": 0,
    "food_quality_score": 0,
    "service_quality_score": 0,
    "price_performance_ratio": 0,
    "delivery_method": 1,
    "delivery_address": "string",
    "delivery_altitude": 0,
    "delivery_longitude": 0,
    "commodities": [
      {
        "com_ID": 0,
        "ind_quantity": 0,
        "commodity_money": 0,
        "rating_type": 0,
        "com_name": "string"
      }
    ],
    "com_position": "string"
  }
]
indInfo.value.forEach(element => {
    element.cus_name="Mark";
    element.cus_phone='18392932921';
    var cusTotalBuy=''
    var totalNum=0
    var totalMoney=0
    element.commodities.forEach(com=>{
        cusTotalBuy+=com.com_name+','
        totalNum++;
        totalMoney+=com.commodity_money;
    }
    )   
    element.cusTotalBuy=cusTotalBuy+'共'+totalNum.toString()+'件';
    element.totalMoney='￥'+totalMoney.toString()
});
})
</script>

<style scoped>
</style>
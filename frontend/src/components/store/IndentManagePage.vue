<template>
<nut-navbar title="我的订单" left-show @click-back="goBack"></nut-navbar>
<nut-config-provider :theme-vars="themeVars">
        <nut-cell title="请选择订单状态" :desc="reflectStatusToString(status_val)" @click="show = true" style="margin-top:2vh;"></nut-cell>
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
                    <nut-space v-if="item.ind_state!=2" >
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
                    <br>
                    <nut-space v-if="sendOrTake==1">
                        <div @click="pathPlanClick">配送规划 &gt;</div>
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
                        margin-left:10vw;
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
                <nut-space v-if="item.ind_state==2">
                    <div>核销码： </div>
                    <nut-input v-model="verCode" style="background-color:#93B090;opacity:0.7;"></nut-input>
                    <nut-button @click="confirmVer(item.ind_verificationCode,item.ind_id)">确定</nut-button>
                </nut-space>
                <nut-divider dashed></nut-divider>
                
            </div>
        </div>
</nut-config-provider>

<nut-popup v-model:visible="show" position="bottom">
    <nut-picker v-model="status_val" :columns="columns" title="请选择订单状态" @confirm="show=false" @cancel="show = false" />
</nut-popup>
<nut-popup v-model:visible="showBottom" round position="bottom" style="justify-content: center;align-items: center;" :style="{ height: '20%' }">
    <div style="position:absolute;top:30%;left:30%;">{{ mess }}</div>
</nut-popup>
</template>

<script setup>
import axios from 'axios';
import { useRouter } from 'vue-router';
import { ref } from 'vue';
import { onMounted } from 'vue';
// import globalData from"../../global.js"
// import axios from 'axios';
const router=useRouter();
const show=ref(false)
const verCode=ref('')
// const indList=ref([])
const indInfo=ref([])
const sendOrTake=ref('0')
const showBottom=ref(false)

const mess=ref('')
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
    collapseItemLineHeight:'18px',

});
const status_val=ref([0])

const columns = ref([
  { text: '未收货', value: 0 },
  { text: '确认收货', value: 1 },
  { text: '待取货', value: 2 },
  { text: '已核销', value: 3},
  { text: '超期未取', value: 4},
  { text: '已评价', value: 5}
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
    axios.get('/api/sto/getIndentList',{
    params:{
        sto_id:parseInt(sessionStorage.getItem("user_id")),
    }
  }).then(response=>{
    console.log(response.data)
    var indList=response.data.map(num => Number(num));
    axios.get('/api/cus/getIndById', {
      params: {
        ind_id: decodeURIComponent(indList)
      }
    }).then(res => {
        res.data.forEach(indDetail=>{
        axios.get('/api/cus/getInfo',{
            params: {
                cus_id:indDetail.cus_id
            }
        }, {
          headers: {
              'Content-Type': 'application/json'
          }
        })
        .then(re=>{
            indDetail.cus_name=re.data.cus_nickname;
            indDetail.cus_phone=re.data.user_phone;
            var cusTotalBuy=''
            var totalNum=0
            var totalMoney=0
            indDetail.commodities.forEach(com=>{
                cusTotalBuy+=com.com_name+','
                totalNum++;
                totalMoney+=com.commodity_money;
            }
            )   
            indDetail.cusTotalBuy=cusTotalBuy+'共'+totalNum.toString()+'件';
            indDetail.totalMoney='￥'+totalMoney.toString()
            indInfo.value.push(indDetail)
        })

        })
    })

})
})
function pathPlanClick(){
    router.push({
        path:'/pathPlanningPage'
    })
}

const confirmVer=(ind_verificationCode,ind_id)=>{
    if(verCode.value===ind_verificationCode){
        axios.post('/api/cus/changeIndentState',  JSON.stringify({ 
            ind_id:ind_id,
            ind_state:3
            }), {
            headers: {
                'Content-Type': 'application/json'
            }
            })
            .then(response => {
                console.log(response.data.msg);
                baseClick("商品已成功核销")
            })    
            .catch((error) => {
                console.log('An error occurred:', error);
            });
    }
}

const baseClick = (message) => {

showBottom.value=true;
mess.value=message
};
</script>

<style scoped>
</style>
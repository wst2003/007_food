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
          <nut-space>
            <nut-form-item label="用户余额">
              <nut-input v-model="formData.user_balance" placeholder="请输入联系电话" type="text" :disabled="true"/>
            </nut-form-item>
            <nut-button @click="recharge" style="margin-left:-25vw;">充值</nut-button>
            <nut-button @click="show_withdraw=true" style="margin-left:-7vw;">提现</nut-button>
          </nut-space>
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
      <nut-popup position="bottom" round v-model:visible="show_recharge" 
      :style="{ width: '100%', height: '40%' }">
        <div style="margin-top:10%;text-align: center;">请选择充值的数额</div>
        <nut-radio-group v-model="charge_num" style="margin-top:7%;margin-left:25%;">
          <nut-space>
            <nut-radio label="50" shape="button">50</nut-radio>
            <nut-radio label="100" shape="button">100</nut-radio>
            <nut-radio label="200" shape="button">200</nut-radio>
          </nut-space>
          <nut-radio style="margin-top:5%;" label="0" shape="button">其他数额
          </nut-radio> 
          <nut-input-number v-model="charge_num_input"></nut-input-number>
        </nut-radio-group>
        <nut-button style="
            margin-top:5%;
            position:relative;
            margin-left:30vw;
            width: 168px;
            height: 40px;
            flex-shrink: 0;
            border-radius: 5px;
            background: #93B090;
            color:white;" @click="confirm_recharge">
            确定</nut-button>
      </nut-popup>
      <nut-popup position="bottom" round v-model:visible="show_withdraw" 
      :style="{ width: '100%', height: '30%' }">
        <div style="margin-top:10%;text-align: center;">请选择提现的数额</div>
        <nut-input-number :max="formData.user_balance" v-model="withdraw_num"></nut-input-number>
        <nut-button style="
            margin-top:5%;
            position:relative;
            margin-left:30vw;
            width: 168px;
            height: 40px;
            flex-shrink: 0;
            border-radius: 5px;
            background: #93B090;
            color:white;" @click="confirm_withdraw">
            确定</nut-button>
      </nut-popup>
      <nut-popup v-model:visible="showBottom" round position="bottom" style="justify-content: center;align-items: center;" :style="{ height: '20%' }">
        <div style="position:absolute;top:30%;left:30%;">{{ mess }}</div>
    </nut-popup>
  </nut-config-provider>
</template>
<script lang="js" setup>
import axios from 'axios';
import globalData from "../../global.js"
const BaseUrl = globalData.BaseUrl
import { onMounted } from 'vue';
import { ref } from 'vue'
const show_recharge=ref(false)
const show_withdraw=ref(false)
const charge_num=ref(50)
const charge_num_input=ref(0)
const withdraw_num=ref(0)
const formData = ref({
  user_phone: '',
  user_address: '',
  sto_introduction: '',
  sto_openingTime:'',
  sto_closingTime:'',
  user_balance:''
})
const showBottom=ref(false)
const mess=ref('')
const themeVars = ref({
      avatarLargeHeight:'90px',
      avatarLargeWidth:'90px',
    });

const openTime=ref('')

import {  useRouter } from 'vue-router';
// import globalData from"../../global.js"
const router=useRouter();
const recharge=()=>{
  show_recharge.value=true
}
const confirm_recharge=()=>{
  if(charge_num.value==0)
    charge_num.value=charge_num_input.value
  charge_num.value=parseFloat(charge_num.value)
  console.log(parseFloat(formData.value.user_balance)+charge_num.value)
  const orderId = generateOrderId();
  axios.post('http://localhost:3000/api/payment', {
      userType:1,
      orderId: orderId,
      price: charge_num.value,
      name: '充值',
      cus_id: sessionStorage.getItem("user_id"),
    })
    .then((res) => {
      window.location.href = res.data.data.paymentUrl;  
      axios.post(BaseUrl+'/api/pub/balanceChange',  JSON.stringify({ 
            new_balance:parseFloat(formData.value.user_balance)+charge_num.value,
            id:sessionStorage.getItem("user_id")
          }), {
      headers: {
        'Content-Type': 'application/json'
        }
        })
          .then(response => {
            console.log(response.data.msg)
            if(response.data.msg=="成功"){
              baseClick("充值成功")
              axios.get(BaseUrl+'/api/cus/getInfo',{
              params: {
                cus_id:sessionStorage.getItem("user_id")
              }
              }, {
                    headers: {
                        'Content-Type': 'application/json'
                    }
                  })
                  .then(response=>{
                      formData.value.user_balance=response.data.user_balance
                  })
              location.reload();
            }
          })
    });

}
const confirm_withdraw=()=>{
  const outBizNo = generateOrderId(); // 这需要是一个动态生成的唯一值
    const orderTitle = '提现';
    const payeeInfo = { 
      identity: '2088722008198443', // 支付宝用户ID
      identity_type: 'ALIPAY_USER_ID' // 支付宝的会员ID
    };
    axios.post('http://localhost:3000/api/withdraw', {
        out_biz_no: outBizNo,
        trans_amount: parseFloat(withdraw_num.value).toFixed(2),
        biz_scene: 'DIRECT_TRANSFER',
        product_code: 'TRANS_ACCOUNT_NO_PWD',
        order_title: orderTitle,
        payee_info: payeeInfo,
    })
    .then((res) => {
        if (res.data.success) {
          axios.post(BaseUrl+'/api/pub/balanceChange',  JSON.stringify({ 
            new_balance:parseFloat(formData.value.user_balance)-withdraw_num.value,
            id:sessionStorage.getItem("user_id")
          }), {
      headers: {
        'Content-Type': 'application/json'
        }
        })
          .then(response => {
            console.log(response.msg)
            if(response.data.msg==="成功")
            {
              baseClick("提现成功")
              axios.get(BaseUrl+'/api/cus/getInfo',{
              params: {
                cus_id:sessionStorage.getItem("user_id")
              }
              }, {
                    headers: {
                        'Content-Type': 'application/json'
                    }
                  })
                  .then(response=>{
                      formData.value.user_balance=response.data.user_balance
                      location.reload();
                  })
              //location.reload();
            }
            
          })
        } else {
          alert('提现失败: ' + res.data.errorMessage);
        }
    })
    .catch((error) => {
        console.error(error);
        alert('提现请求失败');
    });

  
}
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
            formData.value.user_balance=response.data[0].user_balance
            var sto_openingTime=response.data[0].sto_openingTime
            var sto_closingTime=response.data[0].sto_closingTime
            openTime.value=sto_openingTime.substring(0, sto_openingTime.length - 3)+' - '
            + sto_closingTime.substring(0, sto_closingTime.length - 3)
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
const baseClick = (message) => {

showBottom.value=true;
mess.value=message
};
function generateOrderId() {
  const timestamp = Math.floor(Date.now() / 1000);
  const randomDigits = Math.floor(Math.random() * 9000) + 1000;
  return `${timestamp}${randomDigits}`;
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
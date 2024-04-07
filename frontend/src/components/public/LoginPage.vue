<template>
    <nut-popup v-model:visible="showBottom" round position="bottom" style="justify-content: center;align-items: center;" :style="{ height: '20%' }">
        <div style="position:absolute;top:30%;left:30%;">{{ mess }}</div>
    </nut-popup>
    <div class="total-layout">
        <div class="logo-bar">
        </div>
        <div class="login-bar">
            <nut-space :gutter="1" style="position:absolute;left:30%;top:10px;">
                <div class="login-word">登录</div>
                <div class="login-pic"></div>
            </nut-space>
            <nut-input style="position:absoulte;left:10%;top:100px;border-radius: 18px;
            background: #F7F7F7;width:305px;" v-model="phoneNumber" @blur="validatePhone" placeholder="请输入电话号码" />
            <nut-input style="position:absoulte;left:10%;top:120px;border-radius: 18px;
            background: #F7F7F7;width:305px;" v-model="codeNumber" type="password" placeholder="请输入密码">
            </nut-input>
            <nut-cell style="position:absolute;margin-top:125px;left:10%;width:305px;">
                <nut-checkbox v-model="agree">我已阅读同意《临期食品守则》
                    <template #checkedIcon> <Checklist color="green" /> </template>
                </nut-checkbox>
            </nut-cell>
            <nut-button class="confirm-button" :disabled="!agree" @click="login" primary>
                确认
            </nut-button>
            <nut-space :gutter="20" style="position:relative;margin-top:39vh;left:0%;">
                <nut-button class="bottom-button" @click="register">新用户注册</nut-button>
                <nut-button class="bottom-button">短信验证码登录</nut-button>
                <nut-button class="bottom-button">忘记密码</nut-button>
            </nut-space>
        </div>
    </div>
</template>
<script setup lang="js">
    import { onMounted } from 'vue';
    import axios from 'axios';
    import { ref } from 'vue';
    import { Checklist } from '@nutui/icons-vue';
    import { useRouter } from 'vue-router';
    import globalData from"../../global.js"
    const phoneNumber = ref('');
    const codeNumber=ref('')
    const size=ref(0);
    const agree=ref(false);
    const phoneReg = /^1[3-9]\d{9}$/;
    const phoneStatus=ref('')
    const phoneError=ref('')
    const showBottom=ref(false)
    const mess=ref('')
    const router = useRouter();
    onMounted(()=>{
        size.value=window.innerHeight;
        window.addEventListener("resize", () => {
        document.documentElement.style.fontSize = size.value;
    });
    })

    const validatePhone = () => {
        if (!phoneReg.test(phoneNumber.value)) {
            phoneStatus.value = 'error';
            phoneError.value = '电话号码格式不正确';
        } else {
            phoneStatus.value = 'success';
            phoneError.value = '';
        }
    };

    const login=async ()=> {
        if(phoneStatus.value=='error'){
            baseClick(phoneError.value);
        }else{
          axios.post('/api/pub/login',  JSON.stringify({ 
            user_phone:phoneNumber.value,
            user_password:codeNumber.value
          }), {
          headers: {
              'Content-Type': 'application/json'
          }
          })
          .then(response => {
              console.log('Login submitted successfully.');
              console.log(response.data);
              
              if(response.data.msg==='登录成功') {
                /*------------------------*/
                /*登录成功后编辑此处跳转界面*/
                /*------------------------*/
                localStorage.setItem("user_phone",phoneNumber.value );
                globalData.userInfo.user_id= response.data.user_id ;
                console.log(globalData.userInfo.user_id)
                // console.log(globalData.userInfo.user_id)
                if (response.data.user_type==1){
                  sessionStorage.removeItem("user_type");
                  sessionStorage.setItem("user_type", response.data.user_type);
                  router.push({
                        path: '/storemanage',
                  });
                }
                else if (response.data.user_type==0){
                  sessionStorage.removeItem("user_type");
                  sessionStorage.setItem("user_type", response.data.user_type);
                  router.push({
                        // path: '/home',
                        path:'cusinfopage'
                  });
                }
            } else{
                baseClick(response.data.msg);
                console.log(response.data.msg);
              }
          })
          .catch((error) => {
              console.log('An error occurred:', error);
          });
      }
    };

    const baseClick = (message) => {

        showBottom.value=true;
        mess.value=message
    };

    const register=()=>{
        router.push({
            path: '/SelectIdentity',
        });
    }
</script>
<style scoped>
    .total-layout{
        width:100vw;
        height: 100vh;
        overflow: hidden;
        box-shadow: 0px 4px 4px 0px rgba(0, 0, 0, 0.25);
        background: rgba(202, 211, 191, 0.74);
    }
    .logo-bar{
        position:absolute;
        top:10vh;
        left:35%;
        width: 139px;
        height: 140px;
        flex-shrink: 0;
        border-radius: 30px;
        box-shadow: 1px 0px 94.9px 10px rgba(0, 0, 0, 0.25);
        background: url('../../assets/images/image-login-logo.png'), lightgray 0px -24.992px / 100% 139.564% no-repeat;
        background-position:center,center;
        background-blend-mode: darken;
    }
    .login-bar{
        position: relative;
        width: 99vw;
        height: 65vh;
        margin-top:35vh;
        border-radius: 30px;
        /*max-height: 80vh;*/
        background: #FFF;
        flex-shrink: 0;
    }
    .login-word{
        position:absolute;
        top:30px;
        left:5vw;
        width: 133px;
        
        flex-shrink: 0;
        color: #93B090;
        font-family: "Abhaya Libre";
        font-size: 30px;
        font-style: normal;
        font-weight: 700;
        line-height: 21px;
        letter-spacing: 2px;
    }
    .login-pic{
        position: absolute;
        left:23vw;
        top:5px;
        width: 68px;
        height: 55px;
        background: url('../../assets/images/image-50.png'), lightgray 50% / cover no-repeat;
        flex-shrink: 0;
    }
    .confirm-button{
        position: absolute;
        margin-top:180px;
        left:10%;
        width: 305px;
        height: 50px;
        border-radius: 4px;
        background: #93B090;
        font-family: "Source Han Sans SC";
        font-size: 18px;
        font-style: normal;
        font-weight: 700;
        color: #FFF;
        line-height: normal;
        letter-spacing: 2px;
    }
    .bottom-button{
        border-radius: 23px;
        border: 1px solid #E6E6E6;
        background: #FFF;
        color: #969696;
        font-family: "Source Han Sans SC";
        font-size: 13px;
        font-style: normal;
        font-weight: 20%;
        line-height: 13px; /* 100% */
    }
</style>
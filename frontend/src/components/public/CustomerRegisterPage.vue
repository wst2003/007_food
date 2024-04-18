<template>
    <div class="background"></div>
    <nut-space :gutter="1" style="position:absolute;left:30%;top:10px;">
        <div class="login-word">顾客注册</div>
        <div class="login-pic"></div>
    </nut-space>
    <div style="position:relative;margin-top:15vh;">
        <nut-form>
            <nut-form-item style="position:absolute;left:10vw;margin-top:0vh;width:305px;border-radius: 30px;opacity: 70%;">
                <nut-input v-model="formData.user_phone" @blur="validatePhone" placeholder="请输入电话号码" type="number" />
            </nut-form-item>
            
            <nut-form-item style="position:absolute;left:10vw;margin-top:8vh;width:305px;border-radius: 30px;opacity: 70%;">
                <nut-input v-model="formData.user_password" placeholder="请输入密码" type="text" />
            </nut-form-item>
            <nut-space :gutter="20" style="position:absolute;left:10vw;margin-top:16vh;opacity: 70%;">
                <nut-uploader style="border-radius: 80px;">
                    <nut-avatar size="large"> <img src="../../assets/images/image-login-logo.png" /> </nut-avatar>
                </nut-uploader>
                <nut-form-item style="border-radius: 18px;width: 208.459px;">
                    <nut-input v-model="formData.cus_nickname" placeholder="请输入昵称" type="text" />
                </nut-form-item>
            </nut-space>
            <nut-space style="position:absolute;left:10vw;margin-top:26vh;opacity: 70%;">
                <nut-radio-group v-model="formData.user_gender" direction="horizontal">
                    <nut-radio label="0" shape="button" size="small" style="width:50px;">男</nut-radio>
                    <nut-radio label="1" shape="button" size="small" style="width:50px;">女</nut-radio>
                </nut-radio-group>
                <nut-button @click="show = true" style="width:200px;">{{'生日: '+birth_str}}</nut-button>
            </nut-space>
            <nut-form-item style="position:absolute;left:10vw;margin-top:35vh;width:305px;border-radius: 30px;opacity: 70%;">
                <nut-input v-model="formData.cus_payPassword" placeholder="请输入支付密码" type="text" />
            </nut-form-item>
            <nut-form-item style="position:absolute;left:10vw;margin-top:43vh;width:305px;border-radius: 30px;opacity: 70%;">
                <nut-input v-model="cus_payPassword_val" placeholder="请再次输入支付密码" type="text" />
            </nut-form-item>
            <nut-form-item style="position:absolute;left:10vw;margin-top:51vh;width:305px;border-radius: 30px;opacity: 70%;">
                <nut-input type="textarea" v-model="addressInput" style="width: 100%;" placeholder="请输入地址" rows="2"></nut-input>
        
                <!-- 搜索按钮放在新的一行，并添加上边距以增加间距 -->
                <div style="margin-top: 10px;">
                  <nut-button @click="searchLocation" color="#748865a8" type="primary">搜索</nut-button>
                </div>
        
                <!-- 地址的地图显示 -->
                <div id="baiduMap" style="width:100%; height: 300px; margin-top:10px;"></div>
            </nut-form-item>
        </nut-form>
        <nut-popup v-model:visible="show" position="bottom">
            <nut-date-picker
            v-model="birth"
            :three-dimensional="false"
            @confirm="confirm"
            ></nut-date-picker>
        </nut-popup>
        <nut-popup v-model:visible="showBottom" round position="bottom" style="justify-content: center;align-items: center;" :style="{ height: '20%' }">
            <div style="position:absolute;top:30%;left:30%;">{{ mess }}</div>
        </nut-popup>
        <nut-cell style="position:absolute;margin-top:112vh;left:10%;width:305px;height:30px;">
            <nut-checkbox v-model="agree">我已阅读同意《临期食品守则》
                <template #checkedIcon> <Checklist color="green" /> </template>
            </nut-checkbox>
        </nut-cell>
        <nut-button class="confirm-button" :disabled="!agree" @click="submit" primary>
            确认
        </nut-button>
    </div>


</template>
<script lang="js" setup>
    // 引入百度地图组件
    //import BaiduMap from 'BaiduMap';

    let map={};
    let geoc={};
    let BMap={};
    let BMAP_STATUS_SUCCESS;
    const addressInput = ref(''); 
    // const BaseUrl = "http://100.80.74.33:8000"
    import {ref,onMounted} from 'vue';
    import { Checklist } from '@nutui/icons-vue';
    const formData = ref({
        user_phone: '',
        user_password: '',
        user_gender: '',
        user_address: '请输入地址',
        cus_nickname: '',
        cus_payPassword:''
        });
    const birth=ref(new Date(2023,9,30))
    const birth_str=ref(birth.value.getFullYear().toString()+'-'+(birth.value.getMonth()+1).toString()+'-'+birth.value.getDate().toString())
    
    const agree=ref(false);
    const phoneReg = /^1[3-9]\d{9}$/;
    const phoneStatus=ref('');
    const phoneError=ref('');

    /* 监控popup是否显示的参数*/
    const show=ref(false);
    const showBottom=ref(false);
    
    const mess=ref('')
    const cus_payPassword_val=ref('')
    import axios from 'axios';
    import { useRouter } from 'vue-router';
    const router = useRouter();
    
    onMounted(() => {
        BMap = window.BMapGL;
        BMAP_STATUS_SUCCESS=window.BMapGL.BMAP_STATUS_SUCCESS;
        map = new BMap.Map("baiduMap"); 
        geoc = new BMap.Geocoder();
        //const map = new BMapGL.Map("baiduMap"); 
        const point = new BMap.Point(116.404, 39.915);  // 创建点坐标
        map.centerAndZoom(point, 15);                     // 初始化地图，设置中心点坐标和地图级别
        map.enableScrollWheelZoom(true);                  // 开启鼠标滚轮缩放

        //const geoc = new BMapGL.Geocoder();  

        // 添加地图点击事件监听
        map.addEventListener("click", function (e) {
            const pt = e.latlng;
            //alert('点击的经纬度：' + e.latlng.lng + ', ' + e.latlng.lat);
            geoc.getLocation(pt, function (rs) {
                console.log('Complete geocoding result:', rs);
                const addComp = rs.addressComponents;
                formData.value.user_address = addComp.province + addComp.city + addComp.district + addComp.street + addComp.streetNumber;
                // alert(form.user_address);
                // formData.sto_lng = pt.lng.toString();
                // form.sto_lat = pt.lat.toString();
                // alert('点击的经纬度：' + pt.lng.toString() + ', ' + pt.lat.toString());
                addressInput.value = formData.value.user_address;   // 更新输入框的值
                var marker = new BMap.Marker(pt);  // 创建标注
                map.clearOverlays(); // 清除所有覆盖物
                map.addOverlay(marker); // 将标注添加到地图中
                //marker.enableDragging(); // 可拖拽
            });
        });
    });

    const searchLocation = () => { // 搜索地点
        const local = new BMap.LocalSearch(map, {
            renderOptions: { map: map, autoViewport: true, selectFirstResult: false },
            pageCapacity: 8,
        });

        // 搜索回调
        local.setSearchCompleteCallback(results => {
            console.log(local.getStatus())
            console.log(map)
            console.log(geoc)
            if (local.getStatus() === BMAP_STATUS_SUCCESS) {
            const pt = results.getPoi(0).point;
            //form.sto_locationPoint = `${pt.lng},${pt.lat}`;

            // 更新输入框地址
            geoc.getLocation(pt, rs => {
                const addComp = rs.addressComponents;
                formData.value.user_address = addComp.province + addComp.city + addComp.district + addComp.street + addComp.streetNumber;
                addressInput.value = formData.value.user_address;
                //form.sto_lng = pt.lng.toString();
                //form.sto_lat = pt.lat.toString();
            });

            } else {
                console.log('未找到相关地址！');
            }
        });

        local.search(addressInput.value);
    };

    const confirm = ({ selectedValue }) => {
        console.log(selectedValue);
        console.log(birth.value.getMonth())
        birth_str.value=birth.value.getFullYear().toString()+'-'+(birth.value.getMonth()+1).toString()+'-'+birth.value.getDate().toString()
        show.value = false;
    };

    const submit=()=>{
        if(phoneStatus.value=='error'){
            baseClick(phoneError.value);
        }else if(formData.value.user_password===''){
            baseClick('请输入密码');
        }else if(formData.value.cus_nickname===''){
            baseClick('请输入昵称');
        }else if(formData.value.user_gender===''){
            baseClick('请选择性别');
        }else if(formData.value.cus_payPassword!=cus_payPassword_val.value){
            baseClick('两次输入的支付密码不一致！')
        }else if(formData.value.user_address==='请输入地址'){
            baseClick('请输入地址')
        }else{
            console.log(formData.value.user_address)
            axios.post('/api/pub/register/customer',  JSON.stringify({ 
                user_phone:formData.value.user_phone,
                user_password:formData.value.user_password,
                user_address:formData.value.user_address,
                user_gender:formData.value.user_gender,
                cus_nickname:formData.value.cus_nickname,
                cus_payPassword:formData.value.cus_payPassword
            }), {
            headers: {
                'Content-Type': 'application/json'
            }
            })
            .then(response => {
                console.log('Login submitted successfully.');
                console.log(response.data);
                if(response.data.msg==='顾客成功注册') {
                    baseClick('注册成功！前往登录界面！')
                    /*------------------------*/
                    /*登录成功后编辑此处跳转界面*/
                    /*------------------------*/
                    router.push({
                        path: '/login',
                    });
                } else{
                    baseClick(response.data.msg);
                    console.log(response.data.msg);
                }
            })    
            .catch((error) => {
                console.log('An error occurred:', error);
            });
        }
    }
    const validatePhone = () => {
        if (!phoneReg.test(formData.value.user_phone)) {
            phoneStatus.value = 'error';
            phoneError.value = '电话号码格式不正确';
        } else {
            phoneStatus.value = 'success';
            phoneError.value = '';
        }
    };

    const baseClick = (message) => {

        showBottom.value=true;
        mess.value=message
    };
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
        height: 145vh;
        width: 100vw;
        padding-top: 70px; /* 添加顶部填充以避免标题栏遮挡内容 */
        -webkit-user-drag: none;
    }
    .login-word{
        position:absolute;
        top:30px;
        left:0vw;
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
        left:34vw;
        top:5px;
        width: 68px;
        height: 55px;
        background: url('../../assets/images/image-50.png'),rgb(246,247,244) 0px -147.456px / 100% 139.564% no-repeat;

        background-blend-mode: darken;
        
        flex-shrink: 0;
    }
    .confirm-button{
        position: absolute;
        margin-top:118vh;
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
</style>
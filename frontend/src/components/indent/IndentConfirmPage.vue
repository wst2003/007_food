<template>
    <div style="margin-top:30px;margin-bottom: 30px;">
        <nut-row>
            <nut-col :span="12"> 
                <div :class="classObject1" @click="toggleMethod()">
                    <div style="text-align: center; color: white; font-size: 1.2rem; font-family: 'Source Han Sans SC'; font-weight: 700; word-wrap: break-word">到店自提</div>
                    <div style="display:flex;margin-top: 3%;margin-left:5% ;">
                        <StarFillN color="white" />
                        <div style="color:black;font-family: 'Source Han Sans SC';font-weight:1000;margin-left: 5%;letter-spacing: 0.36">拯救 </div>
                        <div style="color:white;">临期食品</div>
                        <div style="color:white;position: absolute;right:55%">+{{ globalData.shoppingCart.items.length }} </div>
                    </div>
                    <div style="display:flex;margin-top: 4%;margin-left:5% ;">
                        <StarFillN color="white" />
                        <div style="color:black;font-family: 'Source Han Sans SC';font-weight:1000;margin-left: 5%;letter-spacing: 0.36">省去 </div>
                        <div style="color:white;">配送费</div>
                        <div style="color:white;position: absolute;right:55%">-￥2</div>
                    </div>
                    <div style="margin-left: 10%;margin-top: 3%;">
                        <nut-tag round plain color="#E9E9E9" text-color="#999999"> 1.2km  ></nut-tag>
                    </div>
                </div>
            </nut-col>
            <nut-col :span="12"> 
                <div :class="classObject2" @click="toggleMethod()">
                    <div style="text-align: center; color: white; font-size: 1.2rem; font-family: 'Source Han Sans SC'; font-weight: 700; word-wrap: break-word">外卖配送</div>
                    <div style="display:flex;margin-top: 3%;margin-left:5% ;">
                        <StarFillN color="white" />
                        <div style="color:black;font-family: 'Source Han Sans SC';font-weight:1000;margin-left: 5%;letter-spacing: 0.36">拯救 </div>
                        <div style="color:white;">临期食品</div>
                        <div style="color:white;position: absolute;right:5%">+{{ globalData.shoppingCart.items.length }} </div>
                    </div>
                    <div style="display:flex;margin-top: 4%;margin-left:5% ;">
                        <StarFillN color="white" />
                        <div style="color:black;font-family: 'Source Han Sans SC';font-weight:1000;margin-left: 5%;letter-spacing: 0.36">碳排放 </div>
                        <div style="color:white;position: absolute;right:5%">+0.68kg</div>
                    </div>
                    <div style="margin-left: 10%;margin-top: 3%;">
                        <nut-tag round plain color="#E9E9E9" text-color="#999999"> 30min  ></nut-tag>
                    </div>
                </div>
            </nut-col>
        </nut-row>
    </div>
    <div>
        <hr/>
    </div>
    <div style="font-size: 14; font-family: 'Source Han Sans SC'; font-weight: 700;margin-left: 25px;margin-top:5px;margin-bottom: 5px;" >订单详情</div>
    <div v-for=" (item) in indent_items.items " :key=" item.com_id ">
        <nut-cell class="nutcell">
            <!-- <img style="width:20%; height: auto; border-radius: 10px" :src=" item.image "/>
            <div style="text-align: center; color: #131212; font-size: 14; font-family: 'Source Han Sans SC'; font-weight: 400; line-height: 16; letter-spacing: 1; word-wrap: break-word">{{ item.com_name }}</div>
            <div style="width: 16px; height: 16px; color: #666666; font-size: 12; font-family: 'Source Han Sans SC'; font-weight: 400; line-height: 16; word-wrap: break-word">x{{ item.ind_quantity }}</div>
        -->
        <div class="container">
            <div class="item">
                <img :src=" item.image " alt="Bagel">
                    <div class="item-info">
                    <div>{{ item.com_name }}</div>
                    <div class="price">¥{{ item.commodity_money }}</div>
                    <div class="distance">1.2km from delivery</div>
                </div>
            </div>
            <!-- <div class="buttons">
                <button type="button">Not spicy</button>
                <button type="button">Change Order</button>
                <button type="button">Arriving in 10 mins</button>
            </div> -->
        </div>
        

        </nut-cell>
    </div>


    <!--Fixed block for shopping cart-->
  <div style="width: 90%; height: 44px; position:fixed;bottom: 70px; margin: auto;left:0;right:0">
    <div style="width: 100%; height: 44px; left: 0; top: 0; position: absolute; box-shadow: 0px 4px 25px rgba(0, 0, 0, 0.25); border-radius: 50px" />
    <div style="width: 70%; height: 44px; left: 0; top: 0; position: absolute; background: #FDFDFD; border-top-left-radius:50px;border-bottom-left-radius:50px;display: flex;align-items: end;" >
      <div style="width: 70%; height: 29.59px; left: 5%;bottom:5px;  position: absolute;display: flex;align-items: center;">
        <div style="height: 16px;  color: #666666; font-size: 10; font-family: 'Source Han Sans SC'; font-weight: 400; word-wrap: break-word">合计：</div>
        <span style="color: black; font-size: 18.49; font-family: 'Source Han Sans SC'; font-weight: 500;  ">￥</span>
        <span style="color: black; font-size: 2rem; font-family: 'Source Han Sans SC'; font-weight: 500;vertical-align:top  ">{{ globalData.shoppingCart.getTotalPrice()}}</span>
      </div>
      <div style="width: 30%; height: 16px; right: 5px; bottom: 10px; position: absolute; color: #666666; font-size: 10; font-family: 'Source Han Sans SC'; font-weight: 400;  word-wrap: break-word">共{{globalData.shoppingCart.getTotalQuantity()}}件商品</div>
    </div>
    <div style="width:30%;height:44px;right:0;top:0;position:absolute;background: black; display: flex;align-items: center;justify-content: center;border-top-right-radius: 50px; border-bottom-right-radius: 50px; "
      @click="generateIndent()"> 
      <div style="text-align: center; color: white; font-size: 16; font-family: 'Source Han Sans SC'; font-weight: 700; word-wrap: break-word">提交订单</div>
    </div>
  </div>
</template>

<script setup>
import { onMounted, reactive,ref,computed} from 'vue';
// import { useRoute,useRouter } from 'vue-router';
import globalData from"../../global.js"
import axios from 'axios';
import { StarFillN } from '@nutui/icons-vue';

const ind_note=ref("") //TODO: note block
const delivery_method=ref(0)
const delivery_address=globalData.userPosition.address 
const cus_Id=sessionStorage.getItem("user_id")//TODO: confirmity

function toggleMethod(){
    if(delivery_method.value==0)delivery_method.value=1;
    else delivery_method.value=0;
}

function indent_item(response_obj){
    this.com_id=response_obj.com_ID
    this.com_name=response_obj.com_name
    this.com_type=response_obj.com_type
    console.log(response_obj.com_ID)
    console.log(globalData.shoppingCart.getItemById(response_obj.com_ID))
    this.ind_quantity=globalData.shoppingCart.getItemById(response_obj.com_ID).quantity
    this.commodity_money=globalData.shoppingCart.getItemById(response_obj.com_ID).price
    this.image="https://007-food.obs.cn-east-3.myhuaweicloud.com/"+response_obj.commodityImage[0].com_image
    
}

const classObject1 = computed(() => ({
    delivery_method_block_chosen: delivery_method.value==0,
    delivery_method_block: delivery_method.value==1,
}))

const classObject2 = computed(() => ({
    delivery_method_block_chosen: delivery_method.value==1,
    delivery_method_block: delivery_method.value==0,
}))

const indent_items=reactive({
    items:[], 
    getTotalPrice(){
        var total=0
        this.items.forEach(ele=>{
            total+=ele.price
        })
        return total
    }
})

onMounted(()=>{
    for(let i=0;i<globalData.shoppingCart.items.length;++i){
        axios.get('/api/com/commoditydetail',{
        params:{
            com_ID:globalData.shoppingCart.items[i].id
        }
        })
        .then(response=>{
            
            indent_items.items.push(new indent_item(response.data))
            console.log(indent_items.items)
        })
    }
})

const generateIndent=()=>{
    console.log('当前用户ID'+sessionStorage.getItem("user_id"))
    axios.post('/api/cus/generateIndent',JSON.stringify({ 
            cus_Id:cus_Id,
            com_arr:indent_items.items,
            delivery_method:delivery_method.value,
            delivery_address:delivery_address.value,
            delivery_altitude:globalData.userPosition.latitude,
            delivery_longitude:globalData.userPosition.longitude, 
            ind_notes:ind_note.value,
            ind_money:globalData.shoppingCart.getTotalPrice(),
          }), {
          headers: {
              'Content-Type': 'application/json'
          }
        })
        .then(response=>{
            console.log('订单生成情况：')
            console.log(response)
        })
}
</script>

<style scoped>
.delivery_method_block{
    width: 90%; 
    height: 116px; 
    margin: auto; 
    background: #CDCDCD; 
    border-radius: 15px
}
.delivery_method_block_chosen{
    width: 90%;
    height: 116px;
    margin: auto; 
    background: #93B090; 
    box-shadow: 0px 0px 33.400001525878906px #93B090; 
    border-radius: 15px
}
.nutcell{
    margin: auto;
    width: 90%;
}
.container {
    display: flex;
    flex-direction: column;
    width: 90%;
    font-family: Arial, sans-serif;
    padding: 10px;
}
.item {
    display: flex;
    align-items: center;
    margin-bottom: 10px;
}
.item img {
    width: 25%;
    height: auto;
    margin-right: 10px;
}
.item-info {
    display: flex;
    flex-direction: column;
}
.price {
    color: #f00;
    font-weight: bold;
}
.distance {
    font-size: 0.8em;
    color: #666;
}
.buttons {
    display: flex;
    justify-content: space-between;
    margin-top: 10px;
}
button {
    border: 1px solid #ccc;
    background-color: #fff;
    padding: 5px 10px;
    cursor: pointer;
}
button:hover {
    background-color: #f7f7f7;
}
</style>
<template>
    <nut-config-provider :theme-vars="themeVars">
        <div v-if="!loading">
            <nut-navbar title="商店管理" left-show @click-back="onClick"></nut-navbar>
            <div class="container">
                <img src="../../assets/cat.svg" style="position:absolute;right: 0%;top:0%;transform: translateY(-100%);z-index: 1000;" />
                <div style="color: #93B090;font-weight: 700;font-size: 16px;">智能库存管理助手</div>
                <div style="display: flex;flex-direction: row;">
                    <div style="display: flex; flex-direction: column;gap:20px">
                        <span>保质期<b style="margin: 4px;">≤1/2</b>食品</span>
                        <span>保质期<b style="margin: 4px;">≤1/4</b>食品</span>
                        <span>已清库存</span>
                    </div>
                    <div style="display: flex; flex-direction: column;gap:20px;margin-left: 15px;">
                        <span style="color:#93B090">{{'共'+statistics.half_num+'件' }}</span>
                        <span style="color:#93B090;">{{'共'+statistics.quater_num+'件' }}</span>
                        <span style="color:#93B090;">{{'共'+statistics.out_num+'件' }}</span>
                    </div>
                    <div style="display: flex; flex-direction: column;gap:12px;margin-left: 20px;">
                        <span class="click-button" @click="goToManagePage">快速管理</span>
                        <span class="click-button" @click="goToManagePage">快速管理</span>
                        <span class="click-button" @click="goToManagePage">查看</span>
                    </div>
                </div>

            </div>
            <div>
                <nut-tabs v-model="tabValue" :animated-time="0" @click="changeTabCb">
                    <nut-tab-pane style="padding: 0 0;" title="全部" pane-key="1">
                        <nut-category :category="categoryData.category" @change="change">
                            <nut-infinite-loading v-model="ifLoading" :has-more="hasMore" @load-more="loadMore"
                                style="margin-left: 20px;">
                                <div v-for="(item, index) in curPageCommodity" :key="item.com_ID"
                                    @click="goToDetailPage(item.com_ID,item.com_oriPrice)">
                                    <div class="com-container">
                                        <img src="../../assets/store_goto.svg"
                                            style="position:absolute; right: 5%;top:5%" />
                                        <div>
                                            <img :src="item.commodityImage ? 'https://007-food.obs.cn-east-3.myhuaweicloud.com/' + curPageCommodity[index].commodityImage[0].com_image : ''"
                                                style="width: 10vh;height: 10vh;" />
                                        </div>
                                        <div style="display: flex;flex-direction: column; margin-left: 20px;">
                                            <div style="font-size: large;">{{ item.com_name }}</div>
                                            <div>
                                                <span style="color: #979797; font-size: 12.295px;">库存</span>
                                                <span style="font-size: 12.295px;">{{ item.com_left }}</span>
                                            </div>
                                            <div style="color:#93B090;font-size: 14.5px; ">{{ "¥" +item.commodityPriceCurve[item.commodityPriceCurve.length -1].com_pc_price }}</div>
                                        </div>
                                    </div>
                                </div>
                            </nut-infinite-loading>
                        </nut-category>
                    </nut-tab-pane>
                    <nut-tab-pane style="padding: 0 0;" title="≤1/2" pane-key="2">
                        <nut-category :category="categoryData.category" @change="change">
                            <nut-infinite-loading v-model="ifLoading" :has-more="hasMore" @load-more="loadMore"
                                style="margin-left: 20px;">
                                <div v-for="(item, index) in curPageCommodity" :key="item.com_ID"
                                    @click="goToDetailPage(item.com_ID)">
                                    <div class="com-container">
                                        <img src="../../assets/store_goto.svg"
                                            style="position:absolute; right: 5%;top:5%" />
                                        <div>
                                            <img :src="item.commodityImage ? 'https://007-food.obs.cn-east-3.myhuaweicloud.com/' + curPageCommodity[index].commodityImage[0].com_image : ''"
                                                style="width: 10vh;height: 10vh;" />
                                        </div>
                                        <div style="display: flex;flex-direction: column; margin-left: 20px;">
                                            <div style="font-size: large;">{{ item.com_name }}</div>
                                            <div>
                                                <span style="color: #979797; font-size: 12.295px;">库存</span>
                                                <span style="font-size: 12.295px;">{{ item.com_left }}</span>
                                            </div>
                                            <div style="color:#93B090;font-size: 14.5px; ">{{ "¥" +item.commodityPriceCurve[item.commodityPriceCurve.length -1].com_pc_price }}</div>
                                        </div>
                                    </div>
                                </div>
                            </nut-infinite-loading>
                        </nut-category>
                    </nut-tab-pane>
                    <nut-tab-pane style="padding: 0 0;" title="≤1/4" pane-key="3">
                        <nut-category :category="categoryData.category" @change="change">
                            <nut-infinite-loading v-model="ifLoading" :has-more="hasMore" @load-more="loadMore"
                                style="margin-left: 20px;">
                                <div v-for="(item, index) in curPageCommodity" :key="item.com_ID"
                                    @click="goToDetailPage(item.com_ID)">
                                    <div class="com-container">
                                        <img src="../../assets/store_goto.svg"
                                            style="position:absolute; right: 5%;top:5%" />
                                        <div>
                                            <img :src="item.commodityImage ? 'https://007-food.obs.cn-east-3.myhuaweicloud.com/' + curPageCommodity[index].commodityImage[0].com_image : ''"
                                                style="width: 10vh;height: 10vh;" />
                                        </div>
                                        <div style="display: flex;flex-direction: column; margin-left: 20px;">
                                            <div style="font-size: large;">{{ item.com_name }}</div>
                                            <div>
                                                <span style="color: #979797; font-size: 12.295px;">库存</span>
                                                <span style="font-size: 12.295px;">{{ item.com_left }}</span>
                                            </div>
                                            <div style="color:#93B090;font-size: 14.5px; ">{{ "¥" +item.commodityPriceCurve[item.commodityPriceCurve.length -1].com_pc_price }}</div>
                                        </div>
                                    </div>
                                </div>
                            </nut-infinite-loading>
                        </nut-category>
                    </nut-tab-pane>
                    <nut-tab-pane style="padding: 0 0;" title="已售罄" pane-key="4">
                        <nut-category :category="categoryData.category" @change="change">
                            <nut-infinite-loading v-model="ifLoading" :has-more="hasMore" @load-more="loadMore"
                                style="margin-left: 20px;">
                                <div v-for="(item, index) in curPageCommodity" :key="item.com_ID"
                                    @click="goToDetailPage(item.com_ID)">
                                    <div class="com-container">
                                        <img src="../../assets/store_goto.svg"
                                            style="position:absolute; right: 5%;top:5%" />
                                        <div>
                                            <img :src="item.commodityImage ? 'https://007-food.obs.cn-east-3.myhuaweicloud.com/' + curPageCommodity[index].commodityImage[0].com_image : ''"
                                                style="width: 10vh;height: 10vh;" />
                                        </div>
                                        <div style="display: flex;flex-direction: column; margin-left: 20px;">
                                            <div style="font-size: large;">{{ item.com_name }}</div>
                                            <div>
                                                <span style="color: #979797; font-size: 12.295px;">库存</span>
                                                <span style="font-size: 12.295px;">{{ item.com_left }}</span>
                                            </div>
                                            <div style="color:#93B090;font-size: 14.5px; ">{{ "¥" +item.commodityPriceCurve[item.commodityPriceCurve.length -1].com_pc_price }}</div>
                                        </div>
                                    </div>
                                </div>
                            </nut-infinite-loading>
                        </nut-category>
                    </nut-tab-pane>
                </nut-tabs>
            </div>

            <nut-image-preview :show="licenceFlag" :images="licenceImage" :show-index="true"
                @close="licenceFlag = false" />
        </div>
    </nut-config-provider>
</template>

<script setup>
import { useRouter } from 'vue-router';
import axios from 'axios';
const BaseUrl = "http://119.8.11.44:8002"
import { ref, reactive } from 'vue';
import { onBeforeMount } from 'vue';
const router = useRouter();
const sto_info = ref({});
const licenceFlag = ref(false)
const loading = ref(true), ifLoading = ref(false);
const hasMore = ref(true);
const tabValue = ref('1');
const statistics = ref({})


const categoryData = reactive({
    categoryInfo: {},
    category: [],
    categoryChild: [{}]
});

const themeVars = ref({
    primaryColor: '#99af93',
    categoryListLeftBgColor: '#f9fbf7'
});

const curPageCommodity = ref([]);

const storeID = sessionStorage.getItem("user_id");
const pageInfo = {
    page_size: 100,
    page_num: 1,
    com_type: "",
    remainingProportion: 1
}

const licenceImage = ref([]);

// 返回操作响应函数
const onClick = () => {
    router.push({
        path: ''
    })
};

const changeTabCb = (title) => {
    hasMore.value = true;

    if(title.title === '≤1/2'){
        if(pageInfo.remainingProportion === 0.5)
            return;
        pageInfo.remainingProportion = 0.5;
    }
    else if(title.title === '≤1/4'){
        if(pageInfo.remainingProportion === 0.25)
            return;
        pageInfo.remainingProportion = 0.25;
    }
    else if(title.title === '已售罄'){
        if(pageInfo.remainingProportion === 0)
            return;
        pageInfo.remainingProportion = 0;
    }
    else if(title.title === '全部'){
        if(pageInfo.remainingProportion === 1)
            return;
        pageInfo.remainingProportion  =1;
    }
    curPageCommodity.value = [];
    pageInfo.page_num = 0;
    axios.get(BaseUrl+'/api/com/commoditylist',{
        params: {
            sto_ID: storeID,
            page_size: pageInfo.page_size,
            page_num: ++pageInfo.page_num,
            com_type: pageInfo.com_type,
            remaining_proportion: pageInfo.remainingProportion
        }
    }).then(res=>{
        curPageCommodity.value = curPageCommodity.value.concat(res.data);
        if (res.data.length < pageInfo.page_size)
                hasMore.value = false;
    })
    
}

import qs from 'qs'
const getStoreInfoData = () => {
    console.log(storeID)
    axios.get(BaseUrl+'/api/sto/informationdetail', {
        params: {
            sto_ID: [storeID]    // TODO: modify to real ID
        },
        paramsSerializer: params => {
            return qs.stringify(params, { indices: false })
        }
    }).then(res => {
        console.log(res.data[0])
        sto_info.value = res.data[0];
        for (const item of sto_info.value.storeLicense) {
            licenceImage.value.push({ src: 'https://007-food.obs.cn-east-3.myhuaweicloud.com/' + item.sto_license })
        }
        loading.value = false;
    })

    axios.get(BaseUrl+'/api/sto/stocategories', {
        params: {
            sto_ID: storeID
        }
    }).then(res => {
        for (const item of res.data.com_category) {
            categoryData.category.push({ catName: item });
        }
        pageInfo.com_type = res.data.com_category[0];
        loadMore();
    })
}

onBeforeMount(()=>{
    getStoreInfoData();
getStatistics();
});




const change = (index) => {
    ifLoading.value = false;
    hasMore.value = true;
    pageInfo.com_type = categoryData.category[index].catName;
    pageInfo.page_num = 1;
    axios.get(BaseUrl+'/api/com/commoditylist', {
        params: {
            sto_ID: storeID,
            page_size: pageInfo.page_size,
            page_num: pageInfo.page_num++,
            com_type: pageInfo.com_type,
            remaining_proportion: pageInfo.remainingProportion
        }
    }).then(res => {
        curPageCommodity.value = res.data;
        if (res.data.length < pageInfo.page_size)
                hasMore.value = false;
    })
};

const loadMore = () => {
    setTimeout(() => {
        axios.get(BaseUrl+'/api/com/commoditylist', {
            params: {
                sto_ID: storeID,
                page_size: pageInfo.page_size,
                page_num: pageInfo.page_num++,
                com_type: pageInfo.com_type,
                remaining_proportion: pageInfo.remainingProportion
            }
        }).then(res => {
            curPageCommodity.value = curPageCommodity.value.concat(res.data);
            ifLoading.value = false;
            if (res.data.length < pageInfo.page_size)
                hasMore.value = false;
        })
    }, 1000);
}


// TODO: callback function for commodity detail info page
const goToDetailPage = (id,price) => {
  router.push({
    path:'/storeCommodityDetail',
    query:{
      id:id,
      price:price
    }
  })
}

const goToManagePage = ()=>{
  router.push({
    path:'/commodityManage',
    query:{
      date:'1'
    }
  })
}

const getStatistics = ()=>{
    axios.get(BaseUrl+'/api/com/ProductStatistics',{
        params:{
            sto_id: storeID
        }
    }).then(res=>{
        statistics.value = res.data;
    })
}




</script>

<style scoped>
.click-button{
    background-color: #93B090;
    color: white;
    padding:3px 10px 3px 10px;
    border-radius: 4px;
text-align: center;

}


.container{
    background-color: #EFF5EE;
    border-radius: 20px;
    height: 40%;
    display: flex;
    flex-direction: column;
    gap: 20px;
    padding-left: 10%;
    padding-right: 10%;
    padding-top: 20px;
    padding-bottom: 20px;
    position: relative;
}

.sto-logo {
    filter: drop-shadow(0px 4px 25px #D8D8D8);
    height: 25vw;
    width: 25vw;
    margin: 20px 0 0 20px;
    border-radius: 50%;


}

.comment {
    margin: 0 0.053vw 20px 0.053vw;
    padding: 20px 0 20px 0;
    border-radius: 28px;
    background: #F7F7F7;
    box-shadow: 0px 0px 27.8px 0px rgba(0, 0, 0, 0.09);

}

.comment-avatar {
    width: 48px;
    border-radius: 50%;
    background-blend-mode: darken;
    box-shadow: 0px 0px 27.9px 0px rgba(147, 176, 144, 0.62);
    margin: 0 20px;
}

.comment-time {
    position: absolute;
    right: 0;
    color: #93B090;
    font-size: 13px;
}

.comment-container {
    display: flex;
    flex-direction: column;
}

.mark {
    color: #000;
    font-family: "Source Han Sans SC";
    font-size: 17.966px;
    font-style: normal;
    font-weight: 700;
    line-height: normal;
}

.rating-title {
    color: #646464;
    font-family: "Source Han Sans CN";
    font-size: 7.486px;
    font-style: normal;
    font-weight: 500;
    line-height: normal;
}

.com-container {
    display: flex;
    flex-direction: row;
    height: 10vh;
    margin-bottom: 4vh;
    margin-top: 4vh;
    position: relative;
}

.licence-popup {
    height: 50%;
    width: 50%;
    background-color: white;
}

.input {
    border-radius: 20px;
    border: 0.5px solid #808080a3;
}

.circle {
    border-radius: 50%;
    width: 80%;
    height: 80%;
    border: 5px solid green;
    transform-origin: center bottom;
}

.rate-container {
    width: 100px;
    height: inherit;
    position: absolute;
    right: 0;
    display: flex;
    flex-direction: column;
    justify-content: center;
    align-items: center;

}

.info {
    color: #808080;
    font-family: "Source Han Sans SC";
    font-size: 12px;
    font-style: normal;
    font-weight: 500;
    line-height: 16px;
    /* 133.333% */
    display: flex;
}

.licence {
    color: #747373aa;
    font-family: "Source Han Sans SC";
    font-size: 10px;
    font-style: normal;
    font-weight: 500;
    line-height: 16px;
    margin: 5px;
    margin-left: auto;
}

.info1::before {
    content: url('../../assets/store_info_storename.svg');
    margin-right: 0.5rem;

}

.info2::before {
    content: url('../../assets/store_info_time.svg');
    margin-right: 0.5rem;
}

.info3::before {
    content: url('../../assets/store_info_phone.svg');
    margin-right: 0.5rem;
}


.store-info-container {
    height: 25vh;
    position: relative;
    background-image: url('../../assets/store-background.png');
}

.store-info {
    height: fit-content;
    width: 100vw;
    background-color: #F8FBF7;
    position: absolute;
    bottom: 0;
    display: flex;
    flex-direction: row;
    padding-top: 10px;
    padding-bottom: 10px;
}

.store-info-list {
    display: flex;
    height: inherit;
    align-items: center;
    margin-left: 30px;

}
</style>
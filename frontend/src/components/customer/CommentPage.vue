<template>
    <nut-navbar title="写评价" left-show @click-back="goBack"></nut-navbar>
    <div class="pic-show">
        <nut-avatar size="100" style="position:absolute;left:12%;top:12%;"> 
            <img src="../../assets/temp/a-295602-f-77-d-111013-f-3745-bd-7-ee-41.png" /> 
        </nut-avatar>
        <div style="
        color: #FFF;
        text-align: center;
        font-family: 'Source Han Sans SC';
        font-size: 16px;
        font-style: normal;
        font-weight: 700;
        line-height: normal;
        position:absolute;
        top:12%;
        right:18%;">
            {{stoName}}
        </div>
    </div>
    <div style="position:relative;">
        <nut-space :gutter="40" style="
        color: #000;
        text-align: center;
        font-family: 'Source Han Sans SC';
        font-size: 16px;
        font-style: normal;
        font-weight: 500;
        font-weight:bold;
        line-height: normal;
        position:absolute;
        top:2vh;
        left:10%;">
            <div style="margin-top:5%;width:70px;">食品质量</div>
            <nut-rate size="30" active-color="rgba(116, 136, 101, 0.66)" v-model="commentData.food_quality" />
        </nut-space>
        <nut-space :gutter="40" style="
        color: #000;
        text-align: center;
        font-family: 'Source Han Sans SC';
        font-size: 16px;
        font-style: normal;
        font-weight: 500;
        font-weight:bold;
        line-height: normal;
        position:absolute;
        top:8vh;
        left:10%;">
            <div style="margin-top:5%;width:70px;">服务质量</div>
            <nut-rate size="30" active-color="rgba(116, 136, 101, 0.66)" v-model="commentData.service_quality" />
        </nut-space>
        <nut-space :gutter="40" style="
        color: #000;
        text-align: center;
        font-family: 'Source Han Sans SC';
        font-size: 16px;
        font-style: normal;
        font-weight: 500;
        font-weight:bold;
        line-height: normal;
        position:absolute;
        top:14vh;
        left:10%;">
            <div style="margin-top:5%;width:70px;">性价比</div>
            <nut-rate size="30" active-color="rgba(116, 136, 101, 0.66)" v-model="commentData.cost_performance" />
        </nut-space>
    </div>
    <div class="comment">
        <nut-textarea style="
        border-radius: 4px;
        background: #F8FBF7;" 
        placeholder="请输入评论" 
        v-model="commentData.comment" 
        limit-show :max-length="200" :rows="5" />
    </div>
    <nut-button style="
    position:fixed;
    margin-top:5vh;
    margin-left:60vw;
    width: 118px;
    height: 44px;
    flex-shrink: 0;
    background-color:#000000;" @click="submitComment">
        <div style="color: #FFF;
        text-align: center;
        text-shadow: 0px 0px 4px rgba(0, 0, 0, 0.25);
        font-family: 'Source Han Sans SC';
        font-size: 16px;
        font-style: normal;
        font-weight: 700;
        line-height: normal;">提交评价</div>
    </nut-button>
</template>

<script lang="js" setup>
    import axios from 'axios';
    import {  useRouter } from 'vue-router';
    //import axios from 'axios';
    import { ref } from 'vue';
    import globalData from"../../global.js"
    const router=useRouter();
    const stoName=ref('吉事花生鞍山新村二店');
    const commentData=ref({
        food_quality:5,
        service_quality:5,
        cost_performance:5,
        comment:' ',
        rating_type:1
    })
    const goBack=()=>{
        router.go(-1);
    }
    const submitComment=()=>{
        console.log(1)
        axios.post('/api/cus/rating',JSON.stringify({ 
            cus_id:globalData.userInfo.user_id,
            ind_id:5,
            food_quality_score:commentData.value.food_quality,
            service_quality_score:commentData.value.service_quality,
            price_performance_ratio:commentData.value.cost_performance,
            cmt_content:commentData.value.comment,
            rating_type:commentData.value.rating_type
          }), {
          headers: {
              'Content-Type': 'application/json'
          }
        })
        .then(response=>{
            console.log(response.data.msg)
        })
    }
</script>
    
<style scoped>
    .pic-show{
        position:relative;
        width: 100vw;
        height: 30vh;
        flex-shrink: 0;
        background: url(../../assets/temp/image-53.png) lightgray 50% / cover no-repeat;
    }
    .comment{
        position:relative;
        margin-top:22vh;
    }
</style>
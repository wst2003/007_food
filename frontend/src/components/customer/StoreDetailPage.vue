<template>
  <nut-config-provider :theme-vars="themeVars">
    <div v-if="!loading">
      <nut-navbar :title="sto_info.sto_name" left-show @click-back="onClick"></nut-navbar>
      <div class="store-info-container" :src="storeBackground">
        <img :src="'https://007-food.obs.cn-east-3.myhuaweicloud.com/' + sto_info.sto_logo" class="sto-logo">
        <div class="store-info">
          <div class="store-info-list">
            <div style="height: fit-content;">
              <div class="info info1">{{ sto_info.sto_name }}</div>
              <div class="info info2">{{ sto_info.sto_openingTime + " - " + sto_info.sto_closingTime }}</div>
              <div class="info info3">{{ sto_info.user_phone }}</div>
            </div>
          </div>
          <div class="rate-container">

            <div style="">
              <nut-circle-progress :progress="sto_info.sto_rating * 20" color="#93B090" stroke-width="7"
                path-color="rgba(0,0,0,0)" style="width:60px;height: 60px;">
                <div class="rating-title">店铺评分</div>
                <div class="mark">{{ sto_info.sto_rating }}</div>
              </nut-circle-progress>
            </div>
            <div class="licence" @click="showLicence">{{ "营业执照 >" }}</div>
          </div>
        </div>
      </div>
      <div>
        <nut-tabs v-model="tabValue" @click="changeTabCb">
          <nut-tab-pane style="padding: 0 0;" title="商品" pane-key="1">
            <nut-category :category="categoryData.category" @change="change">
              <nut-infinite-loading :has-more="hasMore" @load-more="loadMore" style="margin-left: 20px;">
                <div v-for="(item, index) in curPageCommodity" :key="item.com_ID" @click="goToDetailPage(item.com_ID)">
                  <div class="com-container">
                    <img src="../../assets/store_goto.svg" style="position:absolute; right: 5%;top:5%" />
                    <div>
                      <img
                        :src="'https://007-food.obs.cn-east-3.myhuaweicloud.com/' + curPageCommodity[index].commodityImage[0].com_image"
                        style="width: 10vh;height: 10vh;" />
                    </div>
                    <div style="display: flex;flex-direction: column; margin-left: 20px;">
                      <div style="font-size: large;">{{ item.com_name }}</div>
                      <div>
                        <span style="color: #979797; font-size: 12.295px;">库存</span>
                        <span style="font-size: 12.295px;">{{ item.com_left }}</span>
                      </div>
                      <div style="color:#93B090;font-size: 14.5px; ">{{ "¥" +
    item.commodityPriceCurve[item.commodityPriceCurve.length - 1].com_pc_price }}</div>
                    </div>
                  </div>
                </div>
              </nut-infinite-loading>
            </nut-category>
          </nut-tab-pane>
          <nut-tab-pane title="评论" pane-key="2">
            <nut-infinite-loading :has-more="cmtHasMore" @load-more="cmtLoadMore">
              <div v-for="(item, index) in commentList" :key="index" class="comment">
                <div
                  style="position: relative;margin-right: 20px;display: flex;flex-direction: row;  align-items: flex-start;">
                  <img :src="'https://007-food.obs.cn-east-3.myhuaweicloud.com/' + item.user_logo"
                    class="comment-avatar">
                  <div class="comment-container">
                    <div style="font-size: 15px;">
                      {{ item.user_name }}
                    </div>
                    <nut-ellipsis style="color: #646464;font-size: 13px;" :content="item.cmt_content" direction="end"
                      rows="5" expand-text="展开" collapse-text="收起">
                    </nut-ellipsis>
                    <div class="comment-time">{{ item.cmt_time }}</div>
                  </div>
                </div>
              </div>
            </nut-infinite-loading>
          </nut-tab-pane>
        </nut-tabs>
      </div>

      <nut-image-preview :show="licenceFlag" :images="licenceImage" :show-index="true" @close="licenceFlag = false" />
    </div>
  </nut-config-provider>
</template>

<script setup>
import { useRouter } from 'vue-router';
import storeBackground from '../../assets/store-background.png'
import axios from 'axios';
import { ref, reactive, onBeforeMount } from 'vue';
const router = useRouter();
const sto_info = ref({});
const licenceFlag = ref(false)
const loading = ref(true)
const hasMore = ref(true), cmtHasMore = ref(true);
const tabValue = ref('1')
const commentList = ref([]);

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

const pageInfo = {
  page_size: 10,
  page_num: 1,
  com_type: ""
}

const commentInfo = {
  page_size: 10,
  page_num: 1,
}

const licenceImage = ref([]);

// 返回操作响应函数
const onClick = () => {
  router.push({
    path: ''
  })
};

const changeTabCb = () => {
  if (tabValue.value == '1' && commentList.value.length === 0) {
    axios.get('/api/cus/getComment', {
      params: {
        sto_ID: 16,   // TODO: modify to real ID
        page_num: commentInfo.page_num,
        page_size: commentInfo.page_size
      }
    }).then(res => {
      commentList.value = res.data
    })
  }
}


const getStoreInfoData = () => {
  axios.get('/api/sto/informationdetail', {
    params: {
      sto_ID: 16    // TODO: modify to real ID
    }
  }).then(res => {
    sto_info.value = res.data[0];
    for (const item of sto_info.value.storeLicense) {
      licenceImage.value.push({ src: 'https://007-food.obs.cn-east-3.myhuaweicloud.com/' + item.sto_license })
    }
    loading.value = false;
  })

  axios.get('/api/sto/stocategories', {
    params: {
      sto_ID: 16
    }
  }).then(res => {
    for (const item of res.data.com_category) {
      categoryData.category.push({ catName: item });
    }
    pageInfo.com_type = res.data.com_category[0];
    loadMore();
  })
}

const showLicence = () => {
  licenceFlag.value = true;
}

const change = (index) => {
  pageInfo.com_type = categoryData.category[index].catName;
  pageInfo.page_num = 1;
  axios.get('/api/com/commoditylist', {
    params: {
      sto_ID: 16,
      page_size: pageInfo.page_size,
      page_num: pageInfo.page_num++,
      com_type: pageInfo.com_type
    }
  }).then(res => {
    curPageCommodity.value = res.data;
  })
};

const loadMore = () => {
  axios.get('/api/com/commoditylist', {
    params: {
      sto_ID: 16,
      page_size: pageInfo.page_size,
      page_num: pageInfo.page_num++,
      com_type: pageInfo.com_type
    }
  }).then(res => {
    curPageCommodity.value = res.data;  // TODO: add stop loading logic
  })
}

const cmtLoadMore = () => {
  axios.get('/api/cus/getComment', {
    params: {
      sto_ID: 16,
      page_num: commentInfo.page_num,
      page_size: commentInfo.page_size
    }
  }).then(res => {
    commentList.value.concat(res.data);    // TODO: add stop loading logic
  })
}


// TODO: callback function for commodity detail info page
const goToDetailPage = () => {

}


onBeforeMount(() => {
  getStoreInfoData();
})



</script>

<style scoped>
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
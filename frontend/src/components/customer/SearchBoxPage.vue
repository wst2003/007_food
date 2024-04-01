<template>
  <!--  <BaiduMap />-->
  <nut-searchbar v-model="query_content">
    <template #rightin>
      <Voice />
    </template>
    <template #rightout>
      <Search2 @click="userSearch"/>
    </template>
  </nut-searchbar>
  <!--  <nut-infinite-loading v-model="infinityValue" :has-more="hasMore" @load-more="loadMore">-->
  <!--  </nut-infinite-loading>-->
  <nut-row v-for="box in boxes" :key="box.id">
    <nut-col :span="19">
      <nut-config-provider :theme-vars="themeVars">
        <nut-cell style="height: 200px;background-color: #C7E1C487">
          <div style="width: 100%">
            <nut-cell style="width: 15%;height: 15%;position: absolute;z-index: 100;margin-top: 110px;background-color: #93B090;--nut-cell-box-shadow: v-bind(0px 0px 0px 0px)">
              <div style="font-family: Source Han Sans C;
                  font-size: 15px;
                  font-weight: 900;
                  line-height: 13px;
                  text-align: left;
                  color:#FFFFFF;
                  margin:auto
               ">
                <span style="font-family: Source Han Sans C;
                  font-size: 12px;
                  font-weight: 900;
                  line-height: 13px;
                  text-align: left;
                  margin-right: 5px;
                 ">{{"¥"}}</span>{{box.price}}
              </div>
            </nut-cell>
            <nut-cell style="width: 30%;height: 15%;position: absolute;z-index: 99;margin-top: 110px;margin-left:10%;--nut-cell-box-shadow: v-bind(0px 0px 0px 0px);">
              <div style="margin:auto">
                <span style="font-family: Source Han Sans SC;
                  font-size: 10px;
                  font-weight: 700;
                  line-height: 13px;
                  text-align: left;
                  color: #969696;
                ">
                  {{"剩余"}}
                </span>
                <span style="font-family: Source Han Sans SC;
                  font-size: 14px;
                  font-weight: 700;
                  line-height: 13px;
                  text-align: left;
                  color: #93B090;
                 ">
                  {{box.left}}
                </span>
                <span style="font-family: Source Han Sans SC;
                  font-size: 10px;
                  font-weight: 700;
                  line-height: 13px;
                  text-align: left;
                  color: #969696;
                ">
                  {{"份"}}
                </span>
              </div>
            </nut-cell>
            <nut-image :src="url" height="70%" width="100%" :round="true" :radius="6"/>
            <div style="width: 100%">
              <nut-ellipsis style="font-family: Source Han Sans SC;
                      font-size: 14px;
                      font-weight: 400;
                      line-height: 16px;
                      letter-spacing: 1px;
                      text-align: left;
                      color:#131212;
                      margin-left: 15px;
                      margin-top: 5px;
                      width:100%" :content="box.name"
                            direction="end"
              ></nut-ellipsis>
            </div>

            <!--            <div style="width: 70%">-->
            <nut-row >
              <nut-col :span="14">
                <nut-ellipsis style="font-family: Source Han Sans SC;
                        font-size: 10px;
                        font-weight: 400;
                        line-height: 16px;
                        text-align: left;
                        margin-left: 15px;
                        margin-top: 10px;
                        width:100%" :content="box.address"
                              direction="end"
                ></nut-ellipsis>
              </nut-col>
              <nut-col :span="6">
                <nut-ellipsis style="font-family: Source Han Sans SC;
                        font-size: 10px;
                        font-weight: 400;
                        line-height: 16px;
                        text-align: left;
                        margin-left: 15px;
                        margin-top: 10px;
                        width:100%" :content="box.introduction"
                              direction="end"
                ></nut-ellipsis>
              </nut-col>
            </nut-row>
            <!--            </div>-->

          </div>

        </nut-cell>
      </nut-config-provider>

    </nut-col>
    <nut-col :span="5">
      <nut-cell style="height: 200px;background-color: #93B090;display: block;">
        <nut-image :src="box.url[0]" width="60" height="60" fit="cover" round style="margin: auto" />
        <nut-image :src="box.url[1]" width="60" height="60" fit="cover" round style="margin: auto" />
        <nut-image :src="box.url[2]" width="60" height="60" fit="cover" round style="margin: auto" />
      </nut-cell>
    </nut-col>
  </nut-row>
</template>



<script setup>
import {ref} from "vue";

const url = 'https://img10.360buyimg.com/ling/jfs/t1/181258/24/10385/53029/60d04978Ef21f2d42/92baeb21f907cd24.jpg';

const themeVars = ref({
  cellPadding: "0px 0px",
  primaryColor: "#acc2a9",
  primaryColorEnd: "#acc2a9",
});

const boxes = ref([
  {
    id: "",
    name: "PEANUTS奶油盲盒系列",
    address: "吉事花生鞍山新村二店",
    url: [url, url, url],
    introduction: "好，真好，太好了",
    price: 1.1,
    left: 2
  }
])
</script>

<style scoped>
</style>
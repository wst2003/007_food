import { createRouter, createWebHistory } from 'vue-router';

import SearchCommodityPage from '@/components/customer/SearchCommodityPage.vue';
import HomePage from "@/components/public/HomePage.vue"
import CommodityDetailPage from "@/components/commodity/CommodityDetailPage.vue";
import LoginPage from '@/components/public/LoginPage.vue';
import SelectIdentityPage from '@/components/public/SelectIdentityPage.vue';
import StoreDetailPage from '@/components/customer/StoreDetailPage.vue';
import MyIndentPage from '@/components/customer/MyIndentPage.vue';
import CustomerRegisterPage from '@/components/public/CustomerRegisterPage.vue';
import StoreRegisterPage from '@/components/public/StoreRegisterPage.vue';
import CommentPage from '@/components/customer/CommentPage.vue';
import IndentDetailPage from '@/components/customer/IndentDetailPage.vue';
import IndentConfirmPage from '@/components/indent/IndentConfirmPage.vue';
import UploadCommodity from '@/components/store/UploadCommodity.vue';
import UploadBlindBoxPage from '@/components/store/UploadBlindBoxPage.vue';
import UploadSelector from '@/components/store/UploadSelector.vue';
import MysteryBoxDetailPage from '@/components/commodity/MysteryBoxDetailPage.vue'
import StoreManagePage from '@/components/store/StoreManagePage.vue'
import IndentManagePage from '@/components/store/IndentManagePage.vue';
import CommodityManagePage from "@/components/store/CommodityManagePage.vue";
import SearchBoxPage from "@/components/customer/SearchBoxPage.vue";
import PathPlanningPage from '@/components/store/PathPlanningPage.vue';
import StoreHomePage from "@/components/public/StoreHomePage.vue";
import CusInfoPage from '@/components/customer/CusInfoPage.vue';
import StoInfoPage from '@/components/public/StoInfoPage.vue';
import TestCopo from '@/components/TestCopo.vue';
const router = createRouter({
    history: createWebHistory(),
    routes: [
        {
            path: '/',
            redirect: "/login",
        },
        {
            path: '/home',
            name: 'home',
            component: HomePage,
            children: [
                {
                    path: '/mysteryBoxDetail',
                    name: 'mysteryBoxDetail',
                    component: MysteryBoxDetailPage
                },
                {
                    path: '/searchCommodity',
                    name: 'searchCommodity',
                    component: SearchCommodityPage,
                    meta: {
                        keepAlive: true //需要被缓存
                    }
                },
                {
                    path: '/commodityDetail',
                    name: 'commodityDetail',
                    component: CommodityDetailPage
                },
                {
                    path: '/storeDetail',
                    name: 'storeDetail',
                    component: StoreDetailPage
                },
                {
                    path: '/myIndent',
                    name: 'myIndent',
                    component: MyIndentPage
                },
                {
                    path:'/comment',
                    name:'comment',
                    component:CommentPage
                },
                {
                    path:'/indentdetail',
                    name:'indentdetail',
                    component:IndentDetailPage
                },
                {
                    path:'/indentConfirm',
                    name: 'indentConfirm',
                    component:IndentConfirmPage
                },
                {
                    path:'/searchBox',
                    name:'searchBox',
                    component:SearchBoxPage
                }
            ]
        },
        {
            path: '/SelectIdentity',
            name: 'SelectIdentity',
            component: SelectIdentityPage
        },
        {
            path: '/login',
            name: 'login',
            component: LoginPage
        },
        {
            path:'/cusregister',
            name:'cusregister',
            component:CustomerRegisterPage
        },
        {
            path:'/storegister',
            name:'storegister',
            component:StoreRegisterPage
        },
        {
            path:'/uploadblindbox',
            name:'uploadblindbox',
            component:UploadBlindBoxPage
        },
        {
            path:'/UploadSelector',
            name: 'UploadSelector',
            component:UploadSelector
        },
        {
            path:'/pathPlanningPage',
            name:'pathPlanningPage',
            component:PathPlanningPage
        },
        {
            path:'/storeHome',
            name:'storeHome',
            component:StoreHomePage,
            children:[
                {
                    path:'/uploadCommodity',
                    name:'uploadCommodity',
                    component:UploadCommodity
                },
                {
                    path:'/indentmanagepage',
                    name:'indentmanagepage',
                    component:IndentManagePage
                },
                {
                    path:'/commodityManage',
                    name:'commodityManage',
                    component:CommodityManagePage
                },
                {

                    path:'/storemanage',
                    name:'storemanage',
                    component:StoreManagePage
                },
                {
                    path: '/storeCommodityDetail',
                    name: 'storeCommodityDetail',
                    component: CommodityDetailPage
                },
            ]
        
        },
        // {
        //     path:'/storemanage',
        //     name:'storemanage',
        //     component:StoreManagePage
        // },
        {
            path:'/cusinfopage',
            name:'cusinfopage',
            component:CusInfoPage
        },
        {
            path:'/stoinfopage',
            name:'stoinfopage',
            component:StoInfoPage
        },
        {
            path:'/testcopo',
            name:'testcopo',
            component:TestCopo
        }
    ]
});

export default router;

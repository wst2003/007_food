import { createRouter, createWebHistory } from 'vue-router';

import SearchCommodityPage from '@/components/customer/SearchCommodityPage.vue';
import HomePage from "@/components/HomePage.vue"
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
const router = createRouter({
    history: createWebHistory(),
    routes: [
        {
            path: '/',
            redirect: "/searchCommodity",

        },
        {
            path: '/home',
            name: 'home',
            component: HomePage,
            // children: [
            //     {
            //         path: '/searchCommodity',
            //         name: 'searchCommodity',
            //         component: SearchCommodityPage,
            //         meta: {
            //             keepAlive: true //需要被缓存
            //         }
            //     },
            //     {
            //         path: '/commodityDetail',
            //         name: 'commodityDetail',
            //         component: CommodityDetailPage
            //     }
            // ]
            meta: {
                keepAlive: true
            }
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
        }
    ]
});

export default router;

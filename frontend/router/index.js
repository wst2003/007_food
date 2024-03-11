import { createRouter, createWebHistory } from 'vue-router';

import SearchCommodityPage from '@/components/customer/SearchCommodityPage.vue';
import HomePage from "@/components/HomePage.vue"
import CommodityDetailPage from "@/components/commodity/CommodityDetailPage.vue";
import LoginPage from '@/components/public/LoginPage.vue';
import SelectIdentityPage from '@/components/public/SelectIdentityPage.vue';
const router = createRouter({
    history: createWebHistory(),
    routes: [
        {
            path: '/',
            redirect: "/SelectIdentity",
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
            path:'/login',
            name:'login',
            component:LoginPage
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
        }

    ]
});

export default router;

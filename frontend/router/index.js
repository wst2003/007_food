import { createRouter, createWebHistory } from 'vue-router';

import SearchCommodityPage from '@/components/customer/SearchCommodityPage.vue';
import CommodityDetailPage from "@/components/commodity/CommodityDetailPage.vue"
import HomePage from "@/components/HomePage.vue"
const router = createRouter({
    history: createWebHistory(),
    routes: [
        {
            path: '/',
            redirect: '/home'
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

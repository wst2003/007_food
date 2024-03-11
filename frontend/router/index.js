import { createRouter, createWebHistory } from 'vue-router';

import SearchCommodityPage from '@/components/customer/SearchCommodityPage.vue';
import CommodityDetailPage from "@/components/commodity/CommodityDetailPage.vue"
const router = createRouter({
    history: createWebHistory(),
    routes: [
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

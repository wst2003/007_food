import { createRouter, createWebHistory } from 'vue-router';

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
            // meta:{
            //     keepAlive:true
            // },
            component: () => import('@/components/public/HomePage.vue'),
            children: [
                {
                    path: '/mysteryBoxDetail',
                    name: 'mysteryBoxDetail',
                    component: () => import('@/components/commodity/MysteryBoxDetailPage.vue'),
                },
                {
                    path: '/searchCommodity',
                    name: 'searchCommodity',
                    component: () => import('@/components/customer/SearchCommodityPage.vue'),
                    meta: {
                        keepAlive: true //需要被缓存
                    }
                },
                {
                    path: '/commodityDetail',
                    name: 'commodityDetail',
                    component: () => import('@/components/commodity/CommodityDetailPage.vue'),
                },
                {
                    path: '/storeDetail',
                    name: 'storeDetail',
                    component:  () => import('@/components/customer/StoreDetailPage.vue'),
                },
                {
                    path: '/myIndent',
                    name: 'myIndent',
                    component:  () => import('@/components/customer/MyIndentPage.vue'),
                },
                {
                    path:'/comment',
                    name:'comment',
                    component:() => import('@/components/customer/CommentPage.vue'),
                },
                {
                    path:'/indentdetail',
                    name:'indentdetail',
                    component:() => import('@/components/customer/IndentDetailPage.vue')
                },
                {
                    path:'/indentConfirm',
                    name: 'indentConfirm',
                    component:() => import('@/components/indent/IndentConfirmPage.vue')
                },
                {
                    path:'/searchBox',
                    name:'searchBox',
                    component:() => import('@/components/customer/SearchBoxPage.vue')
                }
            ]
        },
        {
            path: '/SelectIdentity',
            name: 'SelectIdentity',
            component:() => import('@/components/public/SelectIdentityPage.vue')
        },
        {
            path: '/login',
            name: 'login',
            component: () => import('@/components/public/LoginPage.vue')
        },
        {
            path:'/cusregister',
            name:'cusregister',
            component:() => import('@/components/public/CusRegisterPage.vue')
        },
        {
            path:'/storegister',
            name:'storegister',
            component:() => import('@/components/public/StoreRegisterPage.vue')
        },
        {
            path:'/uploadblindbox',
            name:'uploadblindbox',
            component:() => import('@/components/store/UploadBlindBoxPage.vue')
        },
        {
            path:'/UploadSelector',
            name: 'UploadSelector',
            component:() => import('@/components/store/UploadSelector.vue')
        },
        {
            path:'/pathPlanningPage',
            name:'pathPlanningPage',
            component:() => import('@/components/store/PathPlanningPage.vue')
        },
        {
            path:'/storeHome',
            name:'storeHome',
            component:() => import('@/components/public/StoreHomePage.vue'),
            children:[
                {
                    path:'/uploadCommodity',
                    name:'uploadCommodity',
                    component:() => import('@/components/store/UploadCommodity.vue'),
                },
                {
                    path:'/indentmanagepage',
                    name:'indentmanagepage',
                    component:() => import('@/components/store/IndentManagePage.vue'),
                },
                {
                    path:'/commodityManage',
                    name:'commodityManage',
                    component:() => import('@/components/store/CommodityManagePage.vue'),
                },
                {

                    path:'/storemanage',
                    name:'storemanage',
                    component:() => import('@/components/store/StoreManagePage.vue'),
                },
                {
                    path: '/storeCommodityDetail',
                    name: 'storeCommodityDetail',
                    component: () => import('@/components/commodity/CommodityDetailPage.vue'),
                },
            ]
        
        },
        {
            path:'/cusinfopage',
            name:'cusinfopage',
            component:() => import('@/components/customer/CusInfoPage.vue'),
        },
        {
            path:'/stoinfopage',
            name:'stoinfopage',
            component:() => import('@/components/public/StoInfoPage.vue'),
        },
        {
            path:'/testcopo',
            name:'testcopo',
            component:() => import('@/components/TestCopo.vue'),
        }
    ]
});

export default router;

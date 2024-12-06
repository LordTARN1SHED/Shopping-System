
import VueRouter from 'vue-router';

const routes = [
    {
        path:'/',
        name:'login',
        component:()=>import('../components/Login')
    },
    {
        path:'/xRegister',
        name:'reg',
        component:()=>import('../components/xRegister.vue')
    },
    {
        path:'/Index',
        name:'index',
        component:()=>import('../components/Index'),
        children:[
            {
                path:'/Home',
                //name:'home',
                meta:{
                    title:'首页'
                },
                component:()=>import('../components/xHome')
            },
            {
                path:'/Mall',
                component:()=>import('../components/Mall.vue')
            },
            {
                path:'/ShoppingCart',
                component:()=>import('../components/ShoppingCart.vue')
            },
            {
                path:'/BooksManage',
                component:()=>import('../components/books/BooksManage.vue')
            },
            {
                path:'/Order',
                component:()=>import('../components/Order.vue')
            }
        ]
    }
]

const router = new VueRouter({
    mode:'history',
    routes
})

export function resetRouter() {
    router.matcher = new VueRouter({
        mode:'history',
        routes: []
    }).matcher
}
const VueRouterPush = VueRouter.prototype.push
VueRouter.prototype.push = function push (to) {
    return VueRouterPush.call(this, to).catch(err => err)
}
export  default router;
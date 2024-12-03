import { createRouter, createWebHistory } from 'vue-router';
import MainPage from '../components/MainPage.vue';
import RegisterForm from '../components/RegisterForm.vue';
import LoginForm from '../components/LoginForm.vue';
import ViewProducts from '../components/ViewProducts.vue';
import ViewOrders from '../components/ViewOrders.vue';
import OrderDetail from '../components/OrderDetail.vue';
import RankingQueries from '../components/RankingQueries.vue';
import RankingDetail from '../components/RankingDetail.vue';

const routes = [
  {
    path: '/',
    name: 'MainPage',
    component: MainPage,
  },
  {
    path: '/register',
    name: 'Register',
    component: RegisterForm,
  },
  {
    path: '/login',
    name: 'Login',
    component: LoginForm,
  },
  {
    path: "/products",
    name: "ViewProducts",
    component: ViewProducts,
  },
  {
    path: '/clientpage/orders',
    name: 'ViewOrders',
    component: ViewOrders,
  },
  {
    path: "/orders/:orderId/details",
    name: "OrderDetails",
    component: OrderDetail,
    props: true, // Pasar params como props
  },
  {
    path: "/ranking-queries",
    name: "RankingQueries",
    component: RankingQueries,
  },
  {
    path: "/ranking/detail/user/:id",
    name: "RankingDetail",
    component: RankingDetail,
  },
  
  

];

const router = createRouter({
  history: createWebHistory('/'),
  routes,
});

export default router;

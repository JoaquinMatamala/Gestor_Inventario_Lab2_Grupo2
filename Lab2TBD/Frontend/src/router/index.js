import { createRouter, createWebHistory } from 'vue-router';
import MainPage from '../components/MainPage.vue';
import RegisterForm from '../components/RegisterForm.vue';
import LoginForm from '../components/LoginForm.vue';
import ViewProducts from '../components/ViewProducts.vue';
import ViewOrders from '../components/ViewOrders.vue';
import OrderDetail from '../components/OrderDetail.vue';
import RankingQueries from '../components/RankingQueries.vue';
import RankingDetail from '../components/RankingDetail.vue';
import SelectLocation from '@/components/SelectLocation.vue';
import AdminPage from '../components/AdminPage.vue';
import StoreManagement from '../components/StoreManagement.vue';
import DeliveryManagement from '../components/DeliveryManagement.vue';

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
  {
    path: "/select-location",
    name: "SelectLocation",
    component: SelectLocation,
  },
  {
    path: '/admin',
    name: 'AdminPage',
    component: AdminPage,
  },
  {
    path: '/admin/stores',
    name: 'StoreManagement',
    component: StoreManagement,
  },
  {
    path: '/admin/delivery',
    name: 'DeliveryManagement',
    component: DeliveryManagement,
  },
];

const router = createRouter({
  history: createWebHistory('/'),
  routes,
});

export default router;

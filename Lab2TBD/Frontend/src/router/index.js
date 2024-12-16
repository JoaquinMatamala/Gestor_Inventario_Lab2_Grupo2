import { createRouter, createWebHistory } from 'vue-router';

// Importación de componentes
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
import NewEstablishment from '@/components/NewEstablishment.vue';
import LocationViewer from '@/components/LocationViewer.vue';
import DeliveryPage from '@/components/DeliveryPage.vue';
import DeliveryMainPage from '@/components/DeliveryMainPage.vue';
import ReviewDeliveryPoint from '@/components/ReviewDeliveryPoint.vue';

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
    path: '/products',
    name: 'ViewProducts',
    component: ViewProducts,
  },
  {
    path: '/clientpage/orders',
    name: 'ViewOrders',
    component: ViewOrders,
  },
  {
    path: '/orders/:orderId/details',
    name: 'OrderDetails',
    component: OrderDetail,
    props: true, // Pasar parámetros de ruta como props
  },
  {
    path: '/ranking-queries',
    name: 'RankingQueries',
    component: RankingQueries,
  },
  {
    path: '/ranking/detail/user/:id',
    name: 'RankingDetail',
    component: RankingDetail,
    props: true, // Pasar parámetros como props
  },
  {
    path: '/select-location',
    name: 'SelectLocation',
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
  {
    path: '/admin/new-establishment',
    name: 'NewEstablishment',
    component: NewEstablishment,
  },
  {
    path: '/location-viewer',
    name: 'LocationViewer',
    component: LocationViewer,
    props: (route) => ({ locationId: Number(route.query.locationId) }), // Recibe query param como prop
  },
  {
    path: '/del-page',
    name: 'DeliveryMainPage',
    component: DeliveryMainPage,
  },
  {
    path: '/del-page/deliveryman',
    name: 'DeliveryMan',
    component: DeliveryPage,
  },
  {
    path: '/del-page/review-point',
    name: 'ReviewDeliveryPoint',
    component: ReviewDeliveryPoint,
  },
];

const router = createRouter({
  history: createWebHistory('/'),
  routes,
});

export default router;

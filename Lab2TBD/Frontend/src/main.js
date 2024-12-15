import { createApp } from 'vue';

// Components
import App from './App.vue';

// Config
import router from '@/router';

// Google Maps Plugin
import VueGoogleMaps from '@fawmi/vue-google-maps';

// Carga la clave desde las variables de entorno
const googleMapsApiKey = process.env.VUE_APP_GOOGLE_MAPS_API_KEY;

createApp(App)
  .use(router)
  .use(VueGoogleMaps, {
    load: {
      key: googleMapsApiKey, // Usa la clave desde .env
      libraries: 'places', // (Opcional) Si necesitas funcionalidades como Autocomplete
    },
  })
  .mount('#app');

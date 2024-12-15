import { createApp } from 'vue';

// Components
import App from './App.vue';

// Config
import router from '@/router';

// Google Maps Plugin
import VueGoogleMaps from '@fawmi/vue-google-maps';

createApp(App)
  .use(router)
  .use(VueGoogleMaps, {
    load: {
      key: '', // Reemplaza con tu API Key de Google Maps
      libraries: 'places', // (Opcional) Si necesitas funcionalidades como Autocomplete
    },
  })
  .mount('#app');

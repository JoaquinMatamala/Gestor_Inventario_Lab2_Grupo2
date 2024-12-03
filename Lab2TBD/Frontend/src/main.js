import { createApp } from 'vue'

// Components
import App from './App.vue'

// Config
import router from '@/router'

createApp(App)
    .use(router)
    .mount('#app')
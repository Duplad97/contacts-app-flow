import './assets/scss/main.scss'
import { createApp } from 'vue'
import App from './App.vue'
import { createPinia } from 'pinia'

const pinia = createPinia();

createApp(App).use(pinia).mount('#app')

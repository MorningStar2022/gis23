// import './assets/main.css'
import { createApp } from "vue";
import App from "./App.vue";
const app = createApp(App);
//引入element-plus
import ElementPlus from "element-plus";
import "element-plus/dist/index.css";
import store from "./store";
app.use(store)

app.use(ElementPlus)
app.mount('#app')

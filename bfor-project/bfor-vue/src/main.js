import "./assets/main.css";

import { createApp } from "vue";
import { createPinia } from "pinia";
import { QuillEditor } from "@vueup/vue-quill";
import "@vueup/vue-quill/dist/vue-quill.snow.css";
import App from "./App.vue";
import router from "./router";
import piniaPluginPersistedstate from "pinia-plugin-persistedstate";
import { useKakao } from "vue3-kakao-maps/@utils";
import InfiniteLoading from "v3-infinite-loading";
import "v3-infinite-loading/lib/style.css";

import "bootstrap";
import "bootstrap/dist/css/bootstrap.min.css";

const { VITE_KAKAO_MAP_SERVICE_KEY } = import.meta.env;

const app = createApp(App);
const pinia = createPinia();

pinia.use(piniaPluginPersistedstate);
app.component("QuillEditor", QuillEditor);
app.component("infinite-loading", InfiniteLoading);

app.use(router);
app.use(pinia);

useKakao(VITE_KAKAO_MAP_SERVICE_KEY);

app.mount("#app");

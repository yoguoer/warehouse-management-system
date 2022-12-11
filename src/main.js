import Vue from "vue";
import App from "./App.vue";
import router from "./router";
import store from "./store";
import ElementUI from "element-ui";
import "element-ui/lib/theme-chalk/index.css";
import "@/assets/iconfont/iconfont.css";
import "@/assets/styles/index.scss";

//全局挂载
Vue.prototype.$store = store;
Vue.use(ElementUI);

import moment from 'moment'
//定义一个全局过滤器实现日期格式化
Vue.filter('datefmt',function (input,fmtstring) {//当input为时间戳时，需转为Number类型
  // 使用momentjs这个日期格式化类库实现日期的格式化功能
  return moment(input).format(fmtstring);
});

var vue = new Vue({
  el: "#app",
  router,
  store,
  render: (h) => h(App),
});
export default vue;

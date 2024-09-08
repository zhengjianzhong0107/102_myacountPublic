import Vue from "vue";
import Router from "vue-router";
import Cookie from "js-cookie";
// import home from "@/view/home"

Vue.use(Router);

let router = new Router({
  mode: "history",
  routes: [
    {
      path: "/",
      name: "home",
      component: () => import("@/view/home")
    },
    {
      path: "/login",
      name: "login",
      component: () => import("@/view/login")
    },
    {
      path: "/registe",
      name: "registe",
      component: () => import("@/view/registe")
    },
    {
      path: "/home_m",
      name: "home_m",
      component: () => import("@/view/home_m")
    }
  ]
});

let mobileFlag = () => {
  var sUserAgent = navigator.userAgent.toLowerCase();
  var bIsIpad = sUserAgent.match(/ipad/i) == "ipad";
  var bIsIphoneOs = sUserAgent.match(/iphone os/i) == "iphone os";
  var bIsMidp = sUserAgent.match(/midp/i) == "midp";
  var bIsUc7 = sUserAgent.match(/rv:1.2.3.4/i) == "rv:1.2.3.4";
  var bIsUc = sUserAgent.match(/ucweb/i) == "ucweb";
  var bIsAndroid = sUserAgent.match(/android/i) == "android";
  var bIsCE = sUserAgent.match(/windows ce/i) == "windows ce";
  var bIsWM = sUserAgent.match(/windows mobile/i) == "windows mobile";
  return (
    bIsIpad ||
    bIsIphoneOs ||
    bIsMidp ||
    bIsUc7 ||
    bIsUc ||
    bIsAndroid ||
    bIsCE ||
    bIsWM
  );
};

router.beforeEach((to, from, next) => {
  if (!Cookie.get("token")) {
    if (to.path == "/login" || to.path == "/registe") next();
    else next("/login");
  } else {
    let isMobile = mobileFlag();
    switch (to.path) {
      case "/":
        if (!isMobile) {
          next();
        } else {
          next("/home_m");
        }
        break;
      case "/home_m":
        if (isMobile) {
          next();
        } else {
          next("/");
        }
        break;
      default:
        next("/");
        break;
    }
  }
});

export default router;

import Vue from 'vue';
import Router from 'vue-router';

Vue.use(Router);


const router = new Router({
    mode: 'history',
    base: process.env.BASE_URL,
    routes: [
        {
        path: "/crud",
        name: "CRUD",
        component: () => import("@/views/crud/CrudView.vue")
      },
      {
        path: '*',
        name: 'login',
        component: () => import('@/views/login/LoginView.vue'),
      },
    //   specialityRoute,
      //Rutas anteriores
      
      /*---------------- PROFILE ----------------*/
    
  
  
      // /*---------------- ERROR PAGES ----------------*/
      // {
      //   path: "/404",
      //   name: "404",
      //   component: () => import("@/views/404.vue")
      // },
      // {
      //   path: "*",
      //   component: () => import("@/views/404.vue")
      // }
  
    ]
  })
  
  // router.beforeEach((to, from, next) => {
  
  //   let isAuthenticated = ( new Vue).$session.exists();
  //   const protectedRoute = to.matched.some(record => record.meta.requireAuth);
  
  //   if (protectedRoute || (to.name == 'login' || to.name == '404')) {
  
  //     if (!isAuthenticated && to.name == 'login') {
  //       next();
  //     } else if (!isAuthenticated && to.name != 'login') {
  //       next({ name: 'login'});
  //     } else if (isAuthenticated && to.name == 'login') {
  //       next({ name: 'RECEPTION'});
  //     } else {
  //         next();
  //     }
  //   }else {
  //     next();
  //   }
  
  
  // });
  
  export default router;
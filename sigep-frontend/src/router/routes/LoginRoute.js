import RutasConstants from "@/core/constants/RutasConstants";

export default {
    path: RutasConstants.LOGIN.path,
    name: RutasConstants.LOGIN.nombre,
    component: () => import('@/views/login/LoginView.vue')
};
import Vue from 'vue';
import Router from 'vue-router';
import RutasConstants from "@/core/constants/RutasConstants";
import CategoriaRoute from "@/router/routes/CategoriaRoute";
import AreaRoute from "@/router/routes/AreaRoute";
import EmpleadoRoute from "@/router/routes/EmpleadoRoute";
import ProyectoRoute from '@/router/routes/ProyectoRoute';
import ClienteRoute from '@/router/routes/ClienteRoute';
import TareaRoute from '@/router/routes/TareaRoute';
import ProductividadRoute from '@/router/routes/ProductividadRoute';
import AvanceRoute from '@/router/routes/AvanceRoute';
import LoginRoute from '@/router/routes/LoginRoute';
import CalendarioRoute from '@/router/routes/CalendarioRoute';
import ArbolRoute from '@/router/routes/ArbolRoute';
import PlataformaDigitalRoute from '@/router/routes/PlataformaDigitalRoute';
import ProveedorAlojamientoRoute from '@/router/routes/ProveedorAlojamientoRoute';
import ResponsableCompraRoute from '@/router/routes/ResponsableCompraRoute';
import GestorContenidoRoute from '@/router/routes/GestorContenidoRoute';
import TecnologiaDesarrolloRoute from '@/router/routes/TecnologiaDesarrolloRoute';
import TipoPlataformaDigitalRoute from '@/router/routes/TipoPlataformaDigitalRoute';
import InicioRoute from '@/router/routes/InicioRoute';
import RoleRoute from './routes/RoleRoute';
import Utileria from '@/core/util/Utileria';


Vue.use(Router);


let routes = [

    // Dashboards

    // {
    //     path: '/',
    //     name: 'analytics',
    //     component: () => import('../DemoPages/Dashboards/Analytics.vue'),
    // },
    LoginRoute,
    InicioRoute,
    CategoriaRoute,
    AreaRoute,
    EmpleadoRoute,
    ClienteRoute,
    TareaRoute,
    ProductividadRoute,
    AvanceRoute,
    ProyectoRoute,
    CalendarioRoute,
    PlataformaDigitalRoute,
    ProveedorAlojamientoRoute,
    ResponsableCompraRoute,
    GestorContenidoRoute,
    TecnologiaDesarrolloRoute,
    TipoPlataformaDigitalRoute,
    ArbolRoute,
    // RoleRoute,
    // Pages

    {
        path: '/pages/login-boxed',
        name: 'login-boxed',
        meta: {
            layout: 'userpages'
        },
        component: () => import('../DemoPages/UserPages/LoginBoxed.vue'),
    },
    {
        path: '/pages/register-boxed',
        name: 'register-boxed',
        meta: {
            layout: 'userpages'
        },
        component: () => import('../DemoPages/UserPages/RegisterBoxed.vue'),
    },
    {
        path: '/pages/forgot-password-boxed',
        name: 'forgot-password-boxed',
        meta: {
            layout: 'userpages'
        },
        component: () => import('../DemoPages/UserPages/ForgotPasswordBoxed.vue'),
    },
    // {
    //     path: '/pages/grafica',
    //     name: 'grafica-pastel',
    //     meta: {
    //         layout: 'userpages'
    //     },
    //     component: () => import('../views/graficapastel/GraficaPastel.vue'),
    // },

    // Elements

    {
        path: '/elements/buttons-standard',
        name: 'buttons-standard',
        component: () => import('../DemoPages/Elements/Buttons/Standard.vue'),
    },
    {
        path: '/elements/dropdowns',
        name: 'dropdowns',
        component: () => import('../DemoPages/Elements/Dropdowns.vue'),
    },
    {
        path: '/elements/icons',
        name: 'icons',
        component: () => import('../DemoPages/Elements/Icons.vue'),
    },
    {
        path: '/elements/badges-labels',
        name: 'badges',
        component: () => import('../DemoPages/Elements/Badges.vue'),
    },
    {
        path: '/elements/cards',
        name: 'cards',
        component: () => import('../DemoPages/Elements/Cards.vue'),
    },
    {
        path: '/elements/list-group',
        name: 'list-group',
        component: () => import('../DemoPages/Elements/ListGroups.vue'),
    },
    {
        path: '/elements/timelines',
        name: 'timeline',
        component: () => import('../DemoPages/Elements/Timeline.vue'),
    },
    {
        path: '/elements/utilities',
        name: 'utilities',
        component: () => import('../DemoPages/Elements/Utilities.vue'),
    },

    // Components

    {
        path: '/components/tabs',
        name: 'tabs',
        component: () => import('../DemoPages/Components/Tabs.vue'),
    },
    {
        path: '/components/accordions',
        name: 'accordions',
        component: () => import('../DemoPages/Components/Accordions.vue'),
    },
    {
        path: '/components/modals',
        name: 'modals',
        component: () => import('../DemoPages/Components/Modals.vue'),
    },
    {
        path: '/components/progress-bar',
        name: 'progress-bar',
        component: () => import('../DemoPages/Components/ProgressBar.vue'),
    },
    {
        path: '/components/tooltips-popovers',
        name: 'tooltips-popovers',
        component: () => import('../DemoPages/Components/TooltipsPopovers.vue'),
    },
    {
        path: '/components/carousel',
        name: 'carousel',
        component: () => import('../DemoPages/Components/Carousel.vue'),
    },
    {
        path: '/components/pagination',
        name: 'pagination',
        component: () => import('../DemoPages/Components/Pagination.vue'),
    },
    {
        path: '/components/maps',
        name: 'maps',
        component: () => import('../DemoPages/Components/Maps.vue'),
    },

    // Tables

    {
        path: '/tables/regular-tables',
        name: 'regular-tables',
        component: () => import('../DemoPages/Tables/RegularTables.vue'),
    },

    // Dashboard Widgets

    {
        path: '/widgets/chart-boxes-3',
        name: 'chart-boxes-3',
        component: () => import('../DemoPages/Widgets/ChartBoxes3.vue'),
    },

    // Forms

    {
        path: '/forms/controls',
        name: 'controls',
        component: () => import('../DemoPages/Forms/Elements/Controls.vue'),
    },
    {
        path: '/forms/layouts',
        name: 'layouts',
        component: () => import('../DemoPages/Forms/Elements/Layouts.vue'),
    },
    // Charts

    {
        path: '/charts/chartjs',
        name: 'chartjs',
        component: () => import('../DemoPages/Charts/Chartjs.vue'),
    },
    {
        path: "*",
        component: InicioRoute.component,
        meta: InicioRoute.meta
    },
];

const enrutador = new Router({
    mode: 'history',
    base: process.env.BASE_URL,
    routes: routes
});

enrutador.beforeEach((to, from, next) => {
    let sesionIniciada = new Vue().$session.exists();
    const permiso = to.meta.permisoModulo || undefined;

    if (sesionIniciada && to.name == RutasConstants.LOGIN.nombre) {
        next({ name: RutasConstants.INICIO.nombre });
    }else if (!sesionIniciada && to.name != RutasConstants.LOGIN.nombre) {
        next({name: RutasConstants.LOGIN.nombre});
    }else{
        if(sesionIniciada){
            if( Utileria.nonEmpty(permiso)){
                if(Utileria.authorized(permiso)){
                    next();
                }else{
                    next({ name: RutasConstants.INICIO.nombre });
                }
            }else{
                next();
            }
        }else{
            next();
        }
    }



    // if (to.name == RutasConstants.LOGIN.nombre || to.name == RutasConstants.INICIO.nombre) {
    //     if (!sesionIniciada && to.name == RutasConstants.LOGIN.nombre) {
    //         next();
    //     } else if (!sesionIniciada && to.name != RutasConstants.LOGIN.nombre) {
    //         next({
    //             name: RutasConstants.LOGIN.nombre
    //         });
    //     } else if (sesionIniciada && to.name == RutasConstants.LOGIN.nombre) {
    //         next({
    //             name: RutasConstants.INICIO.nombre
    //         });
    //     } else {
    //         next();
    //     }
    // } else {
    //     next();
    // }
});

export default enrutador;
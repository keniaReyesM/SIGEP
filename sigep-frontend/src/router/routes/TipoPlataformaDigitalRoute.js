import PermisosContants from "@/core/constants/PermisosContants";
import RutasConstants from "@/core/constants/RutasConstants";

export default {
    path: RutasConstants.TIPO_PLATAFORMA_DIGITAL.path,
    name: RutasConstants.TIPO_PLATAFORMA_DIGITAL.nombre,
    component: () => import('@/views/crud/CrudView.vue'),
    meta: {
      servicio: () => import('@/core/services/TipoPlataformaDigitalService'),
      tabla: () => import('@/components/tipoPlataformaDigital/TipoPlataformaDigitalTabla.vue'),
      formulario: () => import('@/components/tipoPlataformaDigital/TipoPlataformaDigitalFormulario.vue'),
      permisoModulo: PermisosContants.TIPO_PLATAFORMA_DIGITAL_MODULO,
      permisoRegistrar: PermisosContants.TIPO_PLATAFORMA_DIGITAL_REGISTRAR,
      permisoEditar: PermisosContants.TIPO_PLATAFORMA_DIGITAL_EDITAR,
      permisoEliminar: PermisosContants.TIPO_PLATAFORMA_DIGITAL_ELIMINAR,
      encabezado:{
        titulo: "Tipos de plataforma digital",
      }
    }
};
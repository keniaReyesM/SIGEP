import PermisosContants from "@/core/constants/PermisosContants";
import RutasConstants from "@/core/constants/RutasConstants";

export default {
    path: RutasConstants.EMPLEADO.path,
    name: RutasConstants.EMPLEADO.nombre,
    component: () => import('@/views/crud/CrudView.vue'),
    meta: {
      servicio: () => import('@/core/services/EmpleadoService'),
      tabla: () => import('@/components/empleado/EmpleadoTabla.vue'),
      formulario: () => import('@/components/empleado/EmpleadoFormulario.vue'),
      permisoModulo: PermisosContants.EMPLEADO_MODULO,
      permisoRegistrar: PermisosContants.EMPLEADO_REGISTRAR,
      permisoEditar: PermisosContants.EMPLEADO_EDITAR,
      permisoEliminar: PermisosContants.EMPLEADO_ELIMINAR,
      permisoActualizarEstado: PermisosContants.EMPLEADO_ACTUALIZAR_ESTADO,
      encabezado:{
        icono: "mdi-cube",
        titulo: "Empleados",
      },
      modeloInicial: () => {
        return Object.assign({
          horariosLaborales: [ { idDia: null, horaInicio: null, horaFin: null }],
          telefonos: [{ telefono: null, idTipoTelefono: null, principal: false }],
          correos: [{ correo: null, principal: false }]
        }, {});
      }
    }
};
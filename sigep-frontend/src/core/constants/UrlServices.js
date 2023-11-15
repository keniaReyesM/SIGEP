import { API_PATH } from "@/core/config/AppConfig";
  
export default class ServicePath {

    static LOGIN = API_PATH + "login";
  
    /*
     * SERVICIOS DE INICIO DE SESIÓN
     */
    static INICIO_SESION_LOGIN = API_PATH+ "login";
    static OBTENER_INFORMACION_LOGIN = API_PATH+ "usuario/obtenerInformacionLogin";
    
    /*
     * SERVICIOS DE CATEGORIA
     */
    static CATEGORIA_LISTAR_TODOS = API_PATH + "categoria/listarTodos";
    static CATEGORIA_LISTAR_PAGINADO = API_PATH + "categoria/listarPaginado";
    static CATEGORIA_REGISTRAR = API_PATH + "categoria/registrar";
    static CATEGORIA_OBTENER = API_PATH + "categoria/obtener";
    static CATEGORIA_ACTUALIZAR = API_PATH + "categoria/actualizar";
    static CATEGORIA_ELIMINAR = API_PATH + "categoria/eliminar";
    
    /*
     * SERVICIOS DE AREA
     */
    static AREA_LISTAR_TODOS = API_PATH + "area/listarTodos";
    static AREA_LISTAR_PAGINADO = API_PATH + "area/listarPaginado";
    static AREA_REGISTRAR = API_PATH + "area/registrar";
    static AREA_OBTENER = API_PATH + "area/obtener";
    static AREA_ACTUALIZAR = API_PATH + "area/actualizar";
    static AREA_ELIMINAR = API_PATH + "area/eliminar";

     /*
     * SERVICIOS DE GESTOR_CONTENIDO
     */
     static GESTOR_CONTENIDO_LISTAR_TODOS = API_PATH + "gestorContenido/listarTodos";
     static GESTOR_CONTENIDO_LISTAR_PAGINADO = API_PATH + "gestorContenido/listarPaginado";
     static GESTOR_CONTENIDO_REGISTRAR = API_PATH + "gestorContenido/registrar";
     static GESTOR_CONTENIDO_OBTENER = API_PATH + "gestorContenido/obtener";
     static GESTOR_CONTENIDO_ACTUALIZAR = API_PATH + "gestorContenido/actualizar";
     static GESTOR_CONTENIDO_ELIMINAR = API_PATH + "gestorContenido/eliminar";

    /*
     * SERVICIOS DE PROVEEDOR_ALOJAMIENTO
     */
    static PROVEEDOR_ALOJAMIENTO_LISTAR_TODOS = API_PATH + "proveedorAlojamiento/listarTodos";
    static PROVEEDOR_ALOJAMIENTO_LISTAR_PAGINADO = API_PATH + "proveedorAlojamiento/listarPaginado";
    static PROVEEDOR_ALOJAMIENTO_REGISTRAR = API_PATH + "proveedorAlojamiento/registrar";
    static PROVEEDOR_ALOJAMIENTO_OBTENER = API_PATH + "proveedorAlojamiento/obtener";
    static PROVEEDOR_ALOJAMIENTO_ACTUALIZAR = API_PATH + "proveedorAlojamiento/actualizar";
    static PROVEEDOR_ALOJAMIENTO_ELIMINAR = API_PATH + "proveedorAlojamiento/eliminar";

     /*
     * SERVICIOS DE TIPO_PLATAFORMA
     */
     static TIPO_PLATAFORMA_DIGITAL_LISTAR_TODOS = API_PATH + "tipoPlataformaDigital/listarTodos";
     static TIPO_PLATAFORMA_DIGITAL_LISTAR_PAGINADO = API_PATH + "tipoPlataformaDigital/listarPaginado";
     static TIPO_PLATAFORMA_DIGITAL_REGISTRAR = API_PATH + "tipoPlataformaDigital/registrar";
     static TIPO_PLATAFORMA_DIGITAL_OBTENER = API_PATH + "tipoPlataformaDigital/obtener";
     static TIPO_PLATAFORMA_DIGITAL_ACTUALIZAR = API_PATH + "tipoPlataformaDigital/actualizar";
     static TIPO_PLATAFORMA_DIGITAL_ELIMINAR = API_PATH + "tipoPlataformaDigital/eliminar";

    /*
     * SERVICIOS DE RESPONSABLE_COMPRA
     */
    static RESPONSABLE_COMPRA_LISTAR_TODOS = API_PATH + "responsableCompra/listarTodos";
    static RESPONSABLE_COMPRA_LISTAR_PAGINADO = API_PATH + "responsableCompra/listarPaginado";
    static RESPONSABLE_COMPRA_REGISTRAR = API_PATH + "responsableCompra/registrar";
    static RESPONSABLE_COMPRA_OBTENER = API_PATH + "responsableCompra/obtener";
    static RESPONSABLE_COMPRA_ACTUALIZAR = API_PATH + "responsableCompra/actualizar";
    static RESPONSABLE_COMPRA_ELIMINAR = API_PATH + "responsableCompra/eliminar";

     /*
     * SERVICIOS DE TECNOLOGIA_DESARROLLO
     */
     static TECNOLOGIA_DESARROLLO_LISTAR_TODOS = API_PATH + "tecnologiaDesarrollo/listarTodos";
     static TECNOLOGIA_DESARROLLO_LISTAR_PAGINADO = API_PATH + "tecnologiaDesarrollo/listarPaginado";
     static TECNOLOGIA_DESARROLLO_REGISTRAR = API_PATH + "tecnologiaDesarrollo/registrar";
     static TECNOLOGIA_DESARROLLO_OBTENER = API_PATH + "tecnologiaDesarrollo/obtener";
     static TECNOLOGIA_DESARROLLO_ACTUALIZAR = API_PATH + "tecnologiaDesarrollo/actualizar";
     static TECNOLOGIA_DESARROLLO_ELIMINAR = API_PATH + "tecnologiaDesarrollo/eliminar";

    /*
     * SERVICIOS DE ROL
     */
    static ROL_LISTAR_TODOS = API_PATH + "rol/listarTodos";
    static ROL_LISTAR_PAGINADO = API_PATH + "rol/listarPaginado";
    static ROL_REGISTRAR = API_PATH + "rol/registrar";
    static ROL_OBTENER = API_PATH + "rol/obtener";
    static ROL_ACTUALIZAR = API_PATH + "rol/actualizar";
    static ROL_ELIMINAR = API_PATH + "rol/eliminar";

    /*
     * SERVICIOS DE PUESTO
     */
    static PUESTO_LISTAR_TODOS = API_PATH + "puesto/listarTodos";
    static PUESTO_LISTAR_PAGINADO = API_PATH + "puesto/listarPaginado";
    static PUESTO_REGISTRAR = API_PATH + "puesto/registrar";
    static PUESTO_OBTENER = API_PATH + "puesto/obtener";
    static PUESTO_ACTUALIZAR = API_PATH + "puesto/actualizar";
    static PUESTO_ELIMINAR = API_PATH + "puesto/eliminar";
    
    /*
     * SERVICIOS DE EMPLEADO
     */
    static EMPLEADO_LISTAR_PAGINADO = API_PATH + "empleado/listarPaginado";
    static EMPLEADO_LISTAR_TODOS_ACTIVOS = API_PATH + "empleado/listarTodosActivos";
    static EMPLEADO_LISTAR_TODOS = API_PATH + "empleado/listarTodos";
    static EMPLEADO_REGISTRAR = API_PATH + "empleado/registrar";
    static EMPLEADO_OBTENER = API_PATH + "empleado/obtener";
    static EMPLEADO_ACTUALIZAR = API_PATH + "empleado/actualizar";
    static EMPLEADO_ACTUALIZAR_ESTADO = API_PATH + "empleado/actualizarEstado";
  

    /*
     * SERVICIOS DE DIA
     */
    static DIA_LISTAR_TODOS = API_PATH + "dia/listarTodos";


    /*
     * SERVICIOS DE TIPO TELEFONO
     */
    static TIPO_TELEFONO_LISTAR_TODOS = API_PATH + "tipoTelefono/listarTodos";

    /*
     * SERVICIOS DE TIPO TAREA
     */
    static TIPO_TAREA_LISTAR_TODOS = API_PATH + "tipoTarea/listarTodos";
    /*
     * SERVICIOS DE NIVEL PRIORIDAD
     */
    static NIVEL_PRIORIDAD_LISTAR_TODOS = API_PATH + "nivelPrioridad/listarTodos";

    /*
     * SERVICIOS DE ESTADO
     */
    static ESTADO_LISTAR_TIPO_TAREA = API_PATH + "estado/listarTipoTarea";
    static ESTADO_LISTAR_TIPO_GENERAL = API_PATH + "estado/listarTipoGeneral";


    
    /*
     * SERVICIOS DE PROYECTO
     */
    static PROYECTO_LISTAR_PAGINADO = API_PATH + "proyecto/listarPaginado";
    static PROYECTO_LISTAR_TODOS = API_PATH + "proyecto/listarTodos";
    static PROYECTO_REGISTRAR = API_PATH + "proyecto/registrar";
    static PROYECTO_OBTENER = API_PATH + "proyecto/obtener";
    static PROYECTO_ACTUALIZAR = API_PATH + "proyecto/actualizar";
    static PROYECTO_ACTUALIZAR_ESTADO = API_PATH + "proyecto/actualizarEstado";
    static PROYECTO_LISTAR_EMPLEADOS_POR_PROYECTO = API_PATH + "proyecto/listarEmpleadosPorProyecto";
    static PROYECTO_OBTENER_INFORMACION_GRAFICA_CIRCULAR = API_PATH + "proyecto/obtenerInformacionGraficaCircular";
    static PROYECTO_OBTENER_INFORMACION_GENERAL = API_PATH + "proyecto/obtenerInformacionGeneral";
    static PROYECTO_LISTAR_PAGINADO_PRODUCTIVIDAD_EMPLEADO = API_PATH + "proyecto/listarPaginadoProductividadEmpleado";
    static PROYECTO_LISTAR_PAGINADO_PRODUCTIVIDAD_PROYECTO = API_PATH + "proyecto/listarPaginadoProductividadProyecto";
    static PROYECTO_LISTAR_PAGINADO_PRODUCTIVIDAD_GENERAL = API_PATH + "proyecto/listarPaginadoProductividadGeneral";
    static PROYECTO_REPORTE_PRODUCTIVIDAD_EMPLEADO = API_PATH + "proyecto/generarReporteProductividadEmpleado";
    static PROYECTO_REPORTE_PRODUCTIVIDAD_PROYECTO = API_PATH + "proyecto/generarReporteProductividadProyecto";


     /*
     * SERVICIOS DE CLIENTE
     */
     static CLIENTE_LISTAR_PAGINADO = API_PATH + "cliente/listarPaginado";
     static CLIENTE_LISTAR_TODOS = API_PATH + "cliente/listarTodos";
     static CLIENTE_REGISTRAR = API_PATH + "cliente/registrar";
     static CLIENTE_OBTENER = API_PATH + "cliente/obtener";
     static CLIENTE_ACTUALIZAR = API_PATH + "cliente/actualizar";
     static CLIENTE_ACTUALIZAR_ESTADO = API_PATH + "cliente/actualizarEstado";


       /*
     * SERVICIOS DE TAREA
     */
       static TAREA_LISTAR_PAGINADO = API_PATH + "tarea/listarPaginado";
       static TAREA_LISTAR_POR_PROYECTO = API_PATH + "tarea/listarTareasPorProyecto";
       static TAREA_LISTAR_ARBOL = API_PATH + "tarea/listarTareasArbol";
       static TAREA_LISTAR_CALENDARIO = API_PATH + "tarea/listarTareasEvento";
       static TAREA_LISTAR_REUTILIZAR = API_PATH + "tarea/listarTareasReutilizar";
       static TAREA_REGISTRAR = API_PATH + "tarea/registrar";
       static TAREA_OBTENER = API_PATH + "tarea/obtener";
       static TAREA_ACTUALIZAR = API_PATH + "tarea/actualizar";
       static TAREA_OBTENER_REPORTE_TABLERO_OPERACION = API_PATH + "tarea/generarReporteTableroOperacion";
       static TAREA_OBTENER_TAREA_CALENDARIO = API_PATH + "tarea/obtenerTareaCalendario";

       /*
      * SERVICIOS DE PLATAFORMA_DIGITAL
      */
      static PLATAFORMA_DIGITAL_LISTAR_PAGINADO = API_PATH + "plataformaDigital/listarPaginado";
      static PLATAFORMA_DIGITAL_REGISTRAR = API_PATH + "plataformaDigital/registrar";
      static PLATAFORMA_DIGITAL_OBTENER = API_PATH + "plataformaDigital/obtener";
      static PLATAFORMA_DIGITAL_ACTUALIZAR = API_PATH + "plataformaDigital/actualizar";
      static PLATAFORMA_DIGITAL_ELIMINAR = API_PATH + "plataformaDigital/eliminar";

      /*
      * SERVICIOS DE PLATAFORMA_DIGITAL
      */
      static AVANCE_LISTAR_PAGINADO = API_PATH + "avance/listarPaginado";
      static AVANCE_REGISTRAR = API_PATH + "avance/registrar";
      static AVANCE_ELIMINAR = API_PATH + "avance/eliminar";
  }
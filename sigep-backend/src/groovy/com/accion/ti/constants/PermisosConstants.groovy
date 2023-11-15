package com.accion.ti.constants;
import com.accion.ti.enums.RolEnum;

class PermisosConstants {

    public static final def ROLES = (RolEnum.values()*.getRole())
    public static final def ROL_ADMIN = [RolEnum.ADMINISTRADOR.getRole()]
    public static final def ROL_EMPLEADO = [RolEnum.EMPLEADO.getRole()]

    public static final def permisos = [
        [clave: "trs", nombre: "Tareas", roles: ROLES, permisos: [
            [clave: "pry", nombre: "Proyectos", roles: ROLES, permisos: [
                [clave: "pry01", nombre: "Registrar", roles: ROL_ADMIN],
                [clave: "pry02", nombre: "Editar", roles: ROL_ADMIN],
                [clave: "pry03", nombre: "Eliminar", roles: ROL_ADMIN],
                [clave: "pry04", nombre: "Actualizar estado", roles: ROL_ADMIN]
            ]],
            [clave: "tr", nombre: "Tareas", roles: ROLES, permisos: [
                [clave: "tr01", nombre: "Registrar", roles: ROLES],
                [clave: "tr02", nombre: "Editar", roles: ROL_ADMIN],
                [clave: "tr03", nombre: "Eliminar", roles: ROLES],
                [clave: "tr04", nombre: "Actualizar estado", roles: ROLES],
                [clave: "av", nombre: "Avances", roles: ROLES, permisos: [
                        [clave: "av01", nombre: "Registrar", roles: ROLES],
                        [clave: "av02", nombre: "Eliminar", roles: ROLES]
                    ]
                ]
            ]],
            [clave: "prd", nombre: "Productividad", roles: ROL_ADMIN]

        ]],
        [clave: "ad", nombre: "Administración", roles: ROL_ADMIN, permisos: [
            [clave: "dt", nombre: "Directorio", roles: ROL_ADMIN, permisos: [
                [clave: "ars", nombre: "Areas", roles: ROL_ADMIN, permisos:[
                    [clave: "ars01", nombre: "Registrar", roles: ROL_ADMIN],
                    [clave: "ars02", nombre: "Editar", roles: ROL_ADMIN],
                    [clave: "ars03", nombre: "Eliminar", roles: ROL_ADMIN]
                ]],
                [clave: "ctg", nombre: "Categorías", roles: ROL_ADMIN, permisos:[
                    [clave: "ctg01", nombre: "Registrar", roles: ROL_ADMIN],
                    [clave: "ctg02", nombre: "Editar", roles: ROL_ADMIN],
                    [clave: "ctg03", nombre: "Eliminar", roles: ROL_ADMIN]
                ]],
                [clave: "gtc", nombre: "Gestores de contenido", roles: ROL_ADMIN, permisos:[
                    [clave: "gtc01", nombre: "Registrar", roles: ROL_ADMIN],
                    [clave: "gtc02", nombre: "Editar", roles: ROL_ADMIN],
                    [clave: "gtc03", nombre: "Eliminar", roles: ROL_ADMIN]
                ]],
                [clave: "pva", nombre: "Proveedores de alojamiento", roles: ROL_ADMIN, permisos:[
                    [clave: "pva01", nombre: "Registrar", roles: ROL_ADMIN],
                    [clave: "pva02", nombre: "Editar", roles: ROL_ADMIN],
                    [clave: "pva03", nombre: "Eliminar", roles: ROL_ADMIN]
                ]],
                [clave: "rpc", nombre: "Responsables de compra", roles: ROL_ADMIN, permisos:[
                    [clave: "rpc01", nombre: "Registrar", roles: ROL_ADMIN],
                    [clave: "rpc02", nombre: "Editar", roles: ROL_ADMIN],
                    [clave: "rpc03", nombre: "Eliminar", roles: ROL_ADMIN]
                ]],
                [clave: "tcd", nombre: "Tecnologías de desarrollo", roles: ROL_ADMIN, permisos:[
                    [clave: "tcd01", nombre: "Registrar", roles: ROL_ADMIN],
                    [clave: "tcd02", nombre: "Editar", roles: ROL_ADMIN],
                    [clave: "tcd03", nombre: "Eliminar", roles: ROL_ADMIN]
                ]],
                [clave: "tpd", nombre: "Tipos de plataforma digital", roles: ROL_ADMIN, permisos:[
                    [clave: "tpd01", nombre: "Registrar", roles: ROL_ADMIN],
                    [clave: "tpd02", nombre: "Editar", roles: ROL_ADMIN],
                    [clave: "tpd03", nombre: "Eliminar", roles: ROL_ADMIN]
                ]]    
            ]],
            [clave: "emp", nombre: "Empleados", roles: ROL_ADMIN, permisos: [
                [clave: "emp01", nombre: "Registrar", roles: ROL_ADMIN],
                [clave: "emp02", nombre: "Editar", roles: ROL_ADMIN],
                [clave: "emp03", nombre: "Eliminar", roles: ROL_ADMIN],
                [clave: "emp04", nombre: "Actualizar estado", roles: ROL_ADMIN]
            ]],
            [clave: "cli", nombre: "Clientes", roles: ROL_ADMIN, permisos: [
                [clave: "cli01", nombre: "Registrar", roles: ROL_ADMIN],
                [clave: "cli02", nombre: "Editar", roles: ROL_ADMIN],
                [clave: "cli03", nombre: "Eliminar", roles: ROL_ADMIN],
                [clave: "cli04", nombre: "Actualizar estado", roles: ROL_ADMIN]
            ]],
            [clave: "pdt", nombre: "Plataformas digitales", roles: ROL_ADMIN, permisos: [
                [clave: "pdt01", nombre: "Registrar", roles: ROL_ADMIN],
                [clave: "pdt02", nombre: "Editar", roles: ROL_ADMIN],
                [clave: "pdt03", nombre: "Eliminar", roles: ROL_ADMIN]
            ]]
        ]]
    ];

}

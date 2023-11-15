package com.accion.ti.enums;


public enum RolEnum {

    ADMINISTRADOR("ROLE_ADMIN", "Administrador"), EMPLEADO("ROLE_EMPLEADO", "Empleado");

    private final String role;
    private final String descripcion;

    private RolEnum(String role, descripcion){
        this.role = role;
        this.descripcion = descripcion;
    }

    String getRole() {
        return role
    }

    String getDescripcion() {
        return descripcion
    }
}

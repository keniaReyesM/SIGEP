package com.accion.ti.enums;


public enum NivelPrioridadEnum {


    URGENTE(3, "Urgente"), ALTO(2, "Alto"), NINGUNO(1, "Ninguno"), BAJO(0, "Bajo");

    private final Integer prioridad;
    private final String nombre;

    private NivelPrioridadEnum(Integer prioridad, String nombre){
        this.prioridad = prioridad;
        this.nombre = nombre;
    }

    Integer getPrioridad() {
        return prioridad
    }

    String getNombre() {
        return nombre
    }
}

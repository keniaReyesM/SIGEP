package com.accion.ti.enums;


public enum TipoEstadoEnum {

    GENERAL("General"), TAREA("Tarea");

    private final String valor;

    private TipoEstadoEnum(String valor){
        this.valor=valor;
    }

    public String getValor(){
        return valor;
    }

}

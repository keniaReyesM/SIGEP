package com.accion.ti.enums;


public enum EstadoGeneralEnum {

    ACTIVO("Activo"), INACTIVO("Inactivo");

    private final String valor;

    private EstadoGeneralEnum(String valor){
        this.valor=valor;
    }

    public String getValor(){
        return valor;
    }

}

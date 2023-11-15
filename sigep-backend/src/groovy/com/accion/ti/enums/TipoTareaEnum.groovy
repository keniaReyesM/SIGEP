package com.accion.ti.enums;


public enum TipoTareaEnum {

    TAREA("Tarea"), SUBTAREA("Subtarea");

    private final String valor;

    private TipoTareaEnum(String valor){
        this.valor=valor;
    }

    public String getValor(){
        return valor;
    }

}

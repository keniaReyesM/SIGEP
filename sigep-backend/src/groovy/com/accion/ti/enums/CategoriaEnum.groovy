package com.accion.ti.enums;


public enum CategoriaEnum {

    NO_DEFINIDO("N/D");

    private final String valor;

    private CategoriaEnum(String valor){
        this.valor=valor;
    }

    public String getValor(){
        return valor;
    }

}

package com.accion.ti.enums;


public enum TipoPrivacidadEnum {

    PRIVADO("Privado"), PUBLICO("Público");

    private final String valor;

    private TipoPrivacidadEnum(String valor){
        this.valor=valor;
    }

    public String getValor(){
        return valor;
    }

}

package com.accion.ti.enums;


public enum DiaEnum {

    LUNES("Lunes", 1), MARTES("Martes", 2), MIERCOLES("Miércoles", 3), JUEVES("Jueves", 4),
    VIERNES("Viernes",5), SABADO("Sábado",6), DOMINGO("Domingo",7);

    private final String valor;
    private final Integer numero;

    private DiaEnum(String valor, Integer numero){
        this.valor=valor;
        this.numero = numero;
    }

    public String getValor(){
        return valor;
    }

    Integer getNumero() {
        return numero
    }
}

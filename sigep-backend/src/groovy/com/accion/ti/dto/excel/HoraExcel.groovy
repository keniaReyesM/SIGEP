package com.accion.ti.dto.excel

import java.time.LocalTime

class HoraExcel {

    private Integer hoja;
    private Integer fila;
    private Integer columna;
    private LocalTime hora;

    Integer getHoja() {
        return hoja
    }

    void setHoja(Integer hoja) {
        this.hoja = hoja
    }

    Integer getFila() {
        return fila
    }

    void setFila(Integer fila) {
        this.fila = fila
    }

    Integer getColumna() {
        return columna
    }

    void setColumna(Integer columna) {
        this.columna = columna
    }

    LocalTime getHora() {
        return hora
    }

    void setHora(LocalTime hora) {
        this.hora = hora
    }

    @Override
    public String toString() {
        return "HoraExcel{" +
                "hoja=" + hoja +
                ", fila=" + fila +
                ", columna=" + columna +
                ", hora=" + hora +
                '}';
    }
}

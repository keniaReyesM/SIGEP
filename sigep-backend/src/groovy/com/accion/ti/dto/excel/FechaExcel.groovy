package com.accion.ti.dto.excel

import java.time.LocalDate

class FechaExcel {

    private Integer hoja;
    private Integer fila;
    private Integer columna;
    private LocalDate fecha;

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

    LocalDate getFecha() {
        return fecha
    }

    void setFecha(LocalDate fecha) {
        this.fecha = fecha
    }

    Integer getHoja() {
        return hoja
    }

    void setHoja(Integer hoja) {
        this.hoja = hoja
    }
}

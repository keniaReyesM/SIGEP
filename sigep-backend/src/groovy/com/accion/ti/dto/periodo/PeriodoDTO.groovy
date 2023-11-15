package com.accion.ti.dto.periodo

import java.time.LocalDate

class PeriodoDTO {

    private LocalDate fechaInicio;
    private LocalDate fechaFin;

    PeriodoDTO() {
    }

    PeriodoDTO(LocalDate fechaInicio, LocalDate fechaFin) {
        this.fechaInicio = fechaInicio
        this.fechaFin = fechaFin
    }

    LocalDate getFechaInicio() {
        return fechaInicio
    }

    void setFechaInicio(LocalDate fechaInicio) {
        this.fechaInicio = fechaInicio
    }

    LocalDate getFechaFin() {
        return fechaFin
    }

    void setFechaFin(LocalDate fechaFin) {
        this.fechaFin = fechaFin
    }
}

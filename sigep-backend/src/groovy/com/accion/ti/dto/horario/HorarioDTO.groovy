package com.accion.ti.dto.horario

import java.time.LocalTime

class HorarioDTO {

    private LocalTime horaInicio;
    private LocalTime horaFin;

    HorarioDTO() {
    }

    HorarioDTO(LocalTime horaInicio, LocalTime horaFin) {
        this.horaInicio = horaInicio
        this.horaFin = horaFin
    }

    LocalTime getHoraInicio() {
        return horaInicio
    }

    void setHoraInicio(LocalTime horaInicio) {
        this.horaInicio = horaInicio
    }

    LocalTime getHoraFin() {
        return horaFin
    }

    void setHoraFin(LocalTime horaFin) {
        this.horaFin = horaFin
    }
}

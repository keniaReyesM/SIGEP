package com.accion.ti

class Avance {

	Date fechaRegistro
	Date fechaAvance
    Date horaInicio
	Date horaFin
	Empleado empleadoRegistro
    AsignacionTarea asignacionTarea

	static belongsTo = [AsignacionTarea, Empleado]

	static mapping = {
		version false
        horaInicio type: 'time', sqlType: "time"
		horaFin type: 'time', sqlType: "time"
		fechaAvance type: 'date', sqlType: "date"
	}

    static constraints = {
		fechaAvance unique: ['horaInicio', 'horaFin', 'asignacionTarea']
	}
}

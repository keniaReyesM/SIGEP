package com.accion.ti

class HorarioLaboral {

	Date horaInicio
	Date horaFin
	Dia dia
	Empleado empleado

	static belongsTo = [Dia, Empleado]

	static mapping = {
		version false
	}
}

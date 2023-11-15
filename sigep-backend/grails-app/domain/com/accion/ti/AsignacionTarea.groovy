package com.accion.ti

class AsignacionTarea {

	Date fechaRegistro
	Empleado empleadoAsignacion
	Empleado empleado
	Estado estado
	Tarea tarea

	static belongsTo = [Empleado, Estado, Tarea]
	static hasMany = [avances: Avance]

	static mapping = {
		version false
	}
}

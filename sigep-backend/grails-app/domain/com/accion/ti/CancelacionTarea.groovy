package com.accion.ti

class CancelacionTarea {

	String motivo
	Date fechaRegistro
	Empleado empleado
	Estado estado
	Tarea tarea

	static belongsTo = [Empleado, Estado, Tarea]

	static mapping = {
		version false
	}

	static constraints = {
		motivo maxSize: 500
	}
}

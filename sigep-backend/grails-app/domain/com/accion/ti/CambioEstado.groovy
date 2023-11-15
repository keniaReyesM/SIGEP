package com.accion.ti

class CambioEstado {

	Date fechaRegistro
	Empleado empleado
	Estado estadoAnterior
	Estado estadoNuevo
	Tarea tarea

	static belongsTo = [Empleado, Estado, Tarea]

	static mapping = {
		version false
	}
}

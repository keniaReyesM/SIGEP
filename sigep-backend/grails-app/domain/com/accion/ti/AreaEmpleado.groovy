package com.accion.ti

class AreaEmpleado {

	Date fechaRegistro
	Area area
	Empleado empleadoAsignacion
	Empleado empleado
	Estado estado

	static belongsTo = [Area, Empleado, Estado]

	static mapping = {
		version false
	}
}

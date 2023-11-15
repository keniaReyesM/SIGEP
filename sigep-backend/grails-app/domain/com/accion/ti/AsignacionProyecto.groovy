package com.accion.ti

class AsignacionProyecto {

	Date fechaRegistro
	Empleado empleadoAsignacion
	Empleado empleado
	Estado estado
	Proyecto proyecto

	static belongsTo = [Empleado, Estado, Proyecto]

	static mapping = {
		version false
	}
}

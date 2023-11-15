package com.accion.ti

class Estado {

	String nombre
	String porcentaje;
	String color;
	TipoEstado tipoEstado

	static hasMany = [areaEmpleados: AreaEmpleado,
	                  asignacionesProyectos: AsignacionProyecto,
	                  asignacionesTareas: AsignacionTarea,
	                  cambioEstadosAnterior: CambioEstado,
	                  cambioEstadosNuevo: CambioEstado,
	                  cancelacionTareas: CancelacionTarea,
	                  empleados: Empleado,
	                  proyectos: Proyecto,
	                  tareas: Tarea]
	static belongsTo = [TipoEstado]

	static mappedBy = [cambioEstadosAnterior: "estadoAnterior",
	                   cambioEstadosNuevo: "estadoNuevo"]

	static mapping = {
		version false
	}

	static constraints = {
		nombre maxSize: 45
		porcentaje maxSize: 10, nullable: true
		color maxSize: 20, nullable: true
	}
}

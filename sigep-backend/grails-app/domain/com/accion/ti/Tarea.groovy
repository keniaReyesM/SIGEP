package com.accion.ti

class Tarea {

	String clave
	String nombre
	String descripcion
	Date fechaHoraInicio
	Date fechaHoraFin
	Date fechaRegistro
	Empleado empleado
	Estado estado
	NivelPrioridad nivelPrioridad
	Proyecto proyecto
	Tarea tarea
	TipoTarea tipoTarea

	static hasMany = [asignacionTareas: AsignacionTarea,
	                  cambioEstados: CambioEstado,
	                  cancelacionTareas: CancelacionTarea,
	                  tareas: Tarea]
	static belongsTo = [Empleado, Estado, NivelPrioridad, Proyecto, TipoTarea]

	static mapping = {
		
		version false
	}

	static constraints = {
		clave maxSize: 45, unique: false
		nombre maxSize: 50
		descripcion type: 'text', sqlType: "text"
		fechaHoraFin nullable: true
		tarea nullable: true
	}
}

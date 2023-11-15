package com.accion.ti

class Empleado {

	Date fechaRegistro
	Usuario usuario
	Estado estado
	Persona persona
	Puesto puesto
	Empresa empresa

	static hasMany = [horariosLaborales: HorarioLaboral,
					  areasEmpleado: AreaEmpleado,
					  avances: Avance,
					  responsableAsignacionesArea: AreaEmpleado,
	                  asignacionesProyecto: AsignacionProyecto,
	                  responsableAsignacionesProyecto: AsignacionProyecto,
	                  asignacionesTarea: AsignacionTarea,
	                  responsableAsignacionesTarea: AsignacionTarea,
	                  cambiosEstado: CambioEstado,
	                  cancelacionesTarea: CancelacionTarea,
	                  proyectos: Proyecto,
	                  tareas: Tarea]
	static belongsTo = [Estado, Persona, Puesto, Usuario, Empresa]

	
	static mappedBy = [areasEmpleado: "empleado",
	                   responsableAsignacionesArea: "empleadoAsignacion",
	                   avances: "empleadoRegistro",
	                   asignacionesProyecto: "empleado",
	                   responsableAsignacionesProyecto: "empleadoAsignacion",
	                   asignacionesTarea: "empleado",
	                   responsableAsignacionesTarea: "empleadoAsignacion"]

	static mapping = {
		version false
	}
}

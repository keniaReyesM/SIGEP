package com.accion.ti

class Proyecto {

	String clave
	String nombre
	String descripcion
	String color
	Date fechaInicio
	Date fechaFin
	Date fechaRegistro
	Categoria categoria
	Empleado empleado
	Estado estado
	TipoPrivacidad tipoPrivacidad
	Empresa empresa;

	static hasMany = [asignaciones: AsignacionProyecto,
	                  clientes: ClienteProyecto,
	                  tareas: Tarea]
	static belongsTo = [Categoria, Empleado, Estado, TipoPrivacidad, Empresa]

	static mapping = {
		version false
	}

	static constraints = {
		clave maxSize: 45, unique: "empresa"
		nombre maxSize: 50
		descripcion maxSize: 200
		fechaFin nullable: true
		color  maxSize: 15, nullable: true
	}
}

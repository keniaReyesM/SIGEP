package com.accion.ti

class NivelPrioridad {

	Integer prioridad
	String nombre

	static hasMany = [tareas: Tarea]

	static mapping = {
		version false
	}

	static constraints = {
		prioridad unique: true
		nombre maxSize: 45, unique: true
	}
}

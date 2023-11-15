package com.accion.ti

class TipoTarea {

	String nombre

	static hasMany = [tareas: Tarea]

	static mapping = {
		version false
	}

	static constraints = {
		nombre maxSize: 45, unique: true
	}
}

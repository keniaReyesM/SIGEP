package com.accion.ti

class Dia {

	String nombre

	static hasMany = [horariosLaborales: HorarioLaboral]

	static mapping = {
		version false
	}

	static constraints = {
		nombre maxSize: 10, unique: true
	}
}

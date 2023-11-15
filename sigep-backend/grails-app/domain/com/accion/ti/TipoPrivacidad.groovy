package com.accion.ti

class TipoPrivacidad {

	String nombre

	static hasMany = [proyectos: Proyecto]

	static mapping = {
		version false
	}

	static constraints = {
		nombre maxSize: 45, unique: true
	}
}

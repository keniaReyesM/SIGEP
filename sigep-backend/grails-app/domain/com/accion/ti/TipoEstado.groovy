package com.accion.ti

class TipoEstado {

	String nombre
	Empresa empresa

	static belongsTo = [Empresa]
	static hasMany = [estados: Estado]

	static mapping = {
		version false
	}

	static constraints = {
		nombre maxSize: 45, unique: true
	}
}

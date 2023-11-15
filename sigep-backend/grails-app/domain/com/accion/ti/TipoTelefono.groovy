package com.accion.ti

class TipoTelefono {

	String nombre

	static hasMany = [telefonos: Telefono]

	static mapping = {
		version false
	}

	static constraints = {
		nombre maxSize: 15, unique: true
	}
}

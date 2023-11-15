package com.accion.ti

class TipoPlataformaDigital {

	String nombre

	static hasMany = [plataformasDigitales: PlataformaDigital]

	static mapping = {
		version false
	}

	static constraints = {
		nombre maxSize: 15, unique: true
	}
}

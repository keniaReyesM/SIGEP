package com.accion.ti

class TecnologiaDesarrollo {

	String nombre

	static hasMany = [tecnologiaDesarrolloPlataformasDigitales: TecnologiaDesarrolloPlataformaDigital]
	
	static mapping = {
		version false
	}

	static constraints = {
		nombre maxSize: 100, unique: true
	}
}

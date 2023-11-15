package com.accion.ti

class GestorContenido {

	String nombre

	static hasMany = [gestorContenidoPlataformasDigitales: GestorContenidoPlataformaDigital]
	
	static mapping = {
		version false
	}

	static constraints = {
		nombre maxSize: 100, unique: true
	}
}

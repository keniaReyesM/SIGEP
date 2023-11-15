package com.accion.ti

class ResponsableCompra {

	String nombre

	static hasMany = [plataformasDigitales: PlataformaDigital]

	static mapping = {
		version false
	}

	static constraints = {
		nombre maxSize: 100, unique: true
	}
}

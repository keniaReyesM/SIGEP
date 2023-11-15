package com.accion.ti

class GestorContenidoPlataformaDigital {

	GestorContenido gestorContenido
	PlataformaDigital plataformaDigital

	static belongsTo = [GestorContenido, PlataformaDigital]

	static mapping = {
		version false
	}

	static constraints = {
		gestorContenido unique: ['plataformaDigital']
	}
}

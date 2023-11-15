package com.accion.ti

class TecnologiaDesarrolloPlataformaDigital {

	TecnologiaDesarrollo tecnologiaDesarrollo
	PlataformaDigital plataformaDigital

	static belongsTo = [TecnologiaDesarrollo, PlataformaDigital]

	static mapping = {
		version false
	}

	static constraints = {
		tecnologiaDesarrollo unique: ['plataformaDigital']
	}
}

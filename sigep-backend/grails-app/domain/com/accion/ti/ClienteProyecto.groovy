package com.accion.ti

class ClienteProyecto {

	Cliente cliente
	Proyecto proyecto

	static belongsTo = [Cliente, Proyecto]

	static mapping = {
		version false
	}

	static constraints = {
		proyecto unique: ['cliente']
	}
}

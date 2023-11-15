package com.accion.ti

class Telefono {

	String telefono
	Boolean principal
	Cliente cliente
	Persona persona
	TipoTelefono tipoTelefono

	static belongsTo = [Cliente, Persona, TipoTelefono]

	static mapping = {
		version false
	}

	static constraints = {
		telefono maxSize: 15
		persona nullable: true
		cliente nullable: true
	}
}

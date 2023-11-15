package com.accion.ti

class Correo {

	String correo
	Boolean principal
	Cliente cliente
	Persona persona

	static belongsTo = [Cliente, Persona]

	static mapping = {
		version false
	}

	static constraints = {
		correo maxSize: 100
		persona nullable: true
		cliente nullable: true
	}
}

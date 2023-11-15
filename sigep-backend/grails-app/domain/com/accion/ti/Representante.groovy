package com.accion.ti

class Representante {

	Date fechaRegistro
	Cliente cliente
	Persona persona

	static belongsTo = [Cliente, Persona]

	static mapping = {
		version false
	}

	 static constraints = {
		persona unique: ['cliente']
	 }
}

package com.accion.ti

class Direccion {

	String numeroInterior
	String numeroExterior
	String calle
	String colonia
	String municipio
	String estado
	Cliente cliente
	Persona persona

	static belongsTo = [Cliente, Persona]

	static mapping = {
		version false
	}

	static constraints = {
		cliente nullable: true
		persona nullable: true
		numeroInterior maxSize: 5
		numeroExterior maxSize: 5
		calle maxSize: 100
		colonia maxSize: 100
		municipio maxSize: 100
		estado maxSize: 100
	}
}

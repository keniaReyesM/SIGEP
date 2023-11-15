package com.accion.ti

class Persona {

	String nombre
	String primerApellido
	String segundoApellido
	Date fechaNacimiento
	String nombreCompleto

	static hasMany = [correos: Correo,
	                  representantes: Representante,
	                  telefonos: Telefono]

	static hasOne = [personaFoto: PersonaFoto,
					 empleado: Empleado,  
					 direccion: Direccion]						

	//   static transients = ['nombreCompleto']

	static mapping = {
		nombreCompleto formula:"CONCAT(nombre,' ',primer_apellido,IFNULL(CONCAT(' ',segundo_apellido), ''))"
		version false
	}

	static constraints = {
		nombre maxSize: 100
		primerApellido maxSize: 100
		segundoApellido nullable: true, maxSize: 100
		personaFoto  unique: true, nullable: true
		empleado  unique: true, nullable: true
		direccion  unique: true, nullable: true
	}
}

package com.accion.ti

class Puesto {

	String nombre
	Empresa empresa

	static hasMany = [empleados: Empleado]
	static belongsTo = [Empresa]
	
	static mapping = {
		version false
	}

	static constraints = {
		nombre maxSize: 100,  unique: "empresa"
	}
}

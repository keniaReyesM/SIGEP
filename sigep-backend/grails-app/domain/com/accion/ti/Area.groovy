package com.accion.ti

class Area {

	String nombre
	Empresa empresa

	static hasMany = [areaEmpleados: AreaEmpleado]
	static belongsTo = [Empresa]

	static mapping = {
		version false
	}

	static constraints = {
		nombre maxSize: 100, unique: "empresa"
	}
}

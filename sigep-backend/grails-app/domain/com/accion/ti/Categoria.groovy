package com.accion.ti

class Categoria {

	String nombre
	String descripcion
	Empresa empresa;

	static hasMany = [proyectos: Proyecto]
	static belongsTo = [Empresa];
	
	static mapping = {
		version false
	}

	static constraints = {
		nombre maxSize: 45, unique: "empresa"
		descripcion maxSize: 100
	}
}

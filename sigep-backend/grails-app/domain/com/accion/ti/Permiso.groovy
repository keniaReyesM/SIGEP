package com.accion.ti

class Permiso {

	String nombre
	String clave
	Permiso permiso
	
	static hasMany = [permisoRoles: PermisoRol]

	static mapping = {
		version false
	}

	static constraints = {
		nombre maxSize: 300, unique: false
		clave maxSize: 20, unique: true
		permiso nullable: true
	}
}

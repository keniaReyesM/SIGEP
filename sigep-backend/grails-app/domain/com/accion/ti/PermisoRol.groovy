package com.accion.ti

class PermisoRol {

	Rol rol
	Permiso permiso

	static belongsTo = [Rol, Permiso]

	static mapping = {
		version false
	}

	static constraints = {
		rol unique: ['permiso']
	}
}

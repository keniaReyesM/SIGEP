package com.accion.ti

import groovy.transform.EqualsAndHashCode
import groovy.transform.ToString

@EqualsAndHashCode(includes='authority')
@ToString(includes='authority', includeNames=true, includePackage=false)
class Rol implements Serializable {

	private static final long serialVersionUID = 1

	String authority
	String descripcion

	static hasMany = [permisosRol: PermisoRol]

	Rol(String authority) {
		this()
		this.authority = authority
	}

	static constraints = {

		authority blank: false, unique: true
	}

	static mapping = {
		version false
		cache true
	}

	Rol() {

	}
}

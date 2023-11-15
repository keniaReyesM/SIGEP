package com.accion.ti

class Cliente {

	String razonSocial
	Empresa empresa
	Estado estado

	static hasMany = [
	                  clienteProyectos: ClienteProyecto,
	                  correos: Correo,
	                  plataformasDigitales: PlataformaDigital,
	                  representantes: Representante,
	                  telefonos: Telefono]

	static hasOne = [clienteFoto: ClienteFoto,  direccion: Direccion, empresa: Empresa]						

	static mapping = {
		version false
	}

	static constraints = {
		direccion  unique: true, nullable: true
		razonSocial maxSize: 100
		clienteFoto  unique: true, nullable: true
	}
}

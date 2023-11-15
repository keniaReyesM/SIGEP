package com.accion.ti

class Empresa {

    String nombre;
    String razonSocial;
    Date fechaRegistro
    Date fechaLicencia
    Correo correo;
	Telefono telefono;
    Direccion direccion;

	static hasMany = [ clientes: Cliente, 
						areas: Area, 
						puestos: Puesto, 
						empleados: Empleado,
						categorias: Categoria,
						proyectos: Proyecto]

	static belongsTo = [Correo, Direccion, Telefono]


	static mapping = {
		version false
	}

	static constraints = {
		nombre maxSize: 100
		razonSocial maxSize: 100
	}
}

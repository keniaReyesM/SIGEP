package com.accion.ti

class PlataformaDigital {

	String nombre
    String url
	String ip
	Cliente cliente
	TipoPlataformaDigital tipoPlataformaDigital
	ResponsableCompra responsableCompra
	ProveedorAlojamiento proveedorAlojamiento

	static belongsTo = [Cliente, TipoPlataformaDigital, 
						ResponsableCompra, ProveedorAlojamiento]

	static hasMany = [plataformaDigitalGestoresContenidos: GestorContenidoPlataformaDigital, 
					  plataformaDigitalTecnologiasDesarrollo: TecnologiaDesarrolloPlataformaDigital]

	static mapping = {
		version false
	}

	static constraints = {
		nombre maxSize: 100
		url maxSize: 500, nullable: true
	}
}

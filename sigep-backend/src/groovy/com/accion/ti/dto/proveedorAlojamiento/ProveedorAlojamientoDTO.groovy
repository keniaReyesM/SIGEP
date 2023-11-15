package com.accion.ti.dto.proveedorAlojamiento

class ProveedorAlojamientoDTO {

    private Long idProveedorAlojamiento;
	private String nombre;
  	
	public Long getIdProveedorAlojamiento() {
        return idProveedorAlojamiento;
    }

    public void setIdProveedorAlojamiento(Long idProveedorAlojamiento) {
        this.idProveedorAlojamiento = idProveedorAlojamiento;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

}
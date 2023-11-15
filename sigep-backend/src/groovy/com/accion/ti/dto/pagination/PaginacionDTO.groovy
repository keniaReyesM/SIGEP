package com.accion.ti.dto.pagination

class PaginacionDTO {

    private Long paginaActual;
    private Long maximoResultados;
    private String busqueda;
    private String fechaInicio;
    private String fechaFin;
    private Long cantidadOmitir;


    public Long getPaginaActual() {
        return paginaActual;
    }

    public void setPaginaActual(Long paginaActual) {
        this.paginaActual = paginaActual;
    }

    public Long getMaximoResultados() {
        return maximoResultados;
    }

    public void setMaximoResultados(Long maximoResultados) {
        this.maximoResultados = maximoResultados;
    }

    public String getBusqueda() {
        return busqueda;
    }

    public void setBusqueda(String busqueda) {
        this.busqueda = busqueda;
    }

    public Long getCantidadOmitir() {
        return cantidadOmitir;
    }

    public void setCantidadOmitir(Long cantidadOmitir) {
        this.cantidadOmitir = cantidadOmitir;
    }

    String getFechaInicio() {
        return fechaInicio
    }

    void setFechaInicio(String fechaInicio) {
        this.fechaInicio = fechaInicio
    }

    String getFechaFin() {
        return fechaFin
    }

    void setFechaFin(String fechaFin) {
        this.fechaFin = fechaFin
    }
}

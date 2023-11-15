package com.accion.ti.dto.response

class RespuestaListaDTO<T> {

    private Long total;
    private List<T> elementos;


    public Long getTotal() {
        return total;
    }

    public void setTotal(Long total) {
        this.total = total;
    }

    public List<T> getElementos() {
        return elementos;
    }

    public void setElementos(List<T> elementos) {
        this.elementos = elementos;
    }
    
}

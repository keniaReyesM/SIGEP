package com.accion.ti.dto.response

class ResponseListDTO<T> {

    private Long totalCount;
    private List<T> items;

    Long getTotalCount() {
        return totalCount
    }

    void setTotalCount(Long totalCount) {
        this.totalCount = totalCount
    }

    List<T> getItems() {
        return items
    }

    void setItems(List<T> items) {
        this.items = items
    }

}

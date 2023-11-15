package com.accion.ti.dto.personalType

class PersonalTypeDTO {

    private Long idPersonalType
    private String name;
    private String acronym;
    private Integer requiresSignature;

    Long getIdPersonalType() {
        return idPersonalType
    }

    void setIdPersonalType(Long idPersonalType) {
        this.idPersonalType = idPersonalType
    }

    String getName() {
        return name
    }

    void setName(String name) {
        this.name = name
    }

    String getAcronym() {
        return acronym
    }

    void setAcronym(String acronym) {
        this.acronym = acronym
    }

    Integer getRequiresSignature() {
        return requiresSignature
    }

    void setRequiresSignature(Integer requiresSignature) {
        this.requiresSignature = requiresSignature
    }
}

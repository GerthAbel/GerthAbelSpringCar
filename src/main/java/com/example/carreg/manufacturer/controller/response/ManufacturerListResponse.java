package com.example.carreg.manufacturer.controller.response;

import com.example.carreg.manufacturer.entity.ManufacturerEntity;

import java.util.List;

public class ManufacturerListResponse {

    private List<ManufacturerEntity> manufacturers;

    public ManufacturerListResponse() {
    }

    public ManufacturerListResponse(List<ManufacturerEntity> manufacturers) {
        this.manufacturers = manufacturers;
    }

    public List<ManufacturerEntity> getmanufacturers() {
        return manufacturers;
    }

    public void setmanufacturers(List<ManufacturerEntity> manufacturers) {
        this.manufacturers = manufacturers;
    }
}

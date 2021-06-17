package com.example.carreg.car.controller.response;

import com.example.carreg.car.entity.CarEntity;

import java.util.List;

public class CarListResponse {

    private List<CarEntity> cars;

    public List<CarEntity> getCars() {
        return cars;
    }

    public void setCars(List<CarEntity> cars) {
        this.cars = cars;
    }
}

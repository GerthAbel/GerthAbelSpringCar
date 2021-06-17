package com.example.carreg.car.entity;

import com.example.carreg.manufacturer.entity.ManufacturerEntity;
import com.example.carreg.core.entity.CoreEntity;

import javax.persistence.*;

@Entity
@Table(name = "car")
public class CarEntity extends CoreEntity {

    @Column(name = "type")
    private String type;


    @ManyToOne
    @JoinColumn(name = "manufacturer_id")
    private ManufacturerEntity manufacturer;

    @Column(name = "numberOfDoors")
    private Double numberOfDoors;

    @Column(name = "yearOfProduction")
    private Double yearOfProduction;

    public CarEntity() {
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public ManufacturerEntity getmanufacturer() {
        return manufacturer;
    }

    public void setmanufacturer(ManufacturerEntity manufacturer) {
        this.manufacturer = manufacturer;
    }

    public Double getNumberOfDoors() {
        return numberOfDoors;
    }

    public void setNumberOfDoors(Double numberOfDoors) {
        this.numberOfDoors = numberOfDoors;
    }

    public Double getYearOfProduction() {
        return yearOfProduction;
    }

    public void setYearOfProduction(Double yearOfProduction) {
        this.yearOfProduction = yearOfProduction;
    }
}

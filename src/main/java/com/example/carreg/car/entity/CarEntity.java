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
    private Integer numberOfDoors;

    @Column(name = "yearOfProduction")
    private Integer yearOfProduction;

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

    public Integer getNumberOfDoors() {
        return numberOfDoors;
    }

    public void setNumberOfDoors(Integer numberOfDoors) {
        this.numberOfDoors = numberOfDoors;
    }

    public Integer getYearOfProduction() {
        return yearOfProduction;
    }

    public void setYearOfProduction(Integer yearOfProduction) {
        this.yearOfProduction = yearOfProduction;
    }
}

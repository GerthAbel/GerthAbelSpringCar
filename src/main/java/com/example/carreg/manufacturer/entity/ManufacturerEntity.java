package com.example.carreg.manufacturer.entity;

import com.example.carreg.core.entity.CoreEntity;

import javax.persistence.*;

@Entity
@Table(name = "manufacturer")
public class ManufacturerEntity extends CoreEntity {
    @Column(name = "name", unique = true)
    private String name;

    public ManufacturerEntity() {
    }

    public String getName() {
        return name;
    }

    public void setName(String firstName) {
        this.name = firstName;
    }

}

package com.example.carreg.car.service.impl;

import com.example.carreg.car.entity.CarEntity;
import com.example.carreg.car.service.CarService;
import com.example.carreg.core.impl.CoreCRUDServiceImpl;
import org.springframework.stereotype.Service;


@Service
public class CarServiceImpl extends CoreCRUDServiceImpl<CarEntity> implements CarService {

    @Override
    protected void updateCore(CarEntity updatableEntity, CarEntity entity) {
        updatableEntity.setmanufacturer(entity.getmanufacturer());
        updatableEntity.setType(entity.getType());
    }

    @Override
    protected Class<CarEntity> getManagedClass() {
        return CarEntity.class;
    }
}

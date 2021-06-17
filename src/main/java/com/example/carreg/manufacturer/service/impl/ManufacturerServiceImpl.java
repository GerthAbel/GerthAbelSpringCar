package com.example.carreg.manufacturer.service.impl;

import com.example.carreg.manufacturer.entity.ManufacturerEntity;
import com.example.carreg.manufacturer.service.ManufacturerService;
import com.example.carreg.core.impl.CoreCRUDServiceImpl;
import org.springframework.stereotype.Service;

@Service
public class ManufacturerServiceImpl extends CoreCRUDServiceImpl<ManufacturerEntity> implements ManufacturerService {


    @Override
    protected void updateCore(ManufacturerEntity updatableEntity, ManufacturerEntity entity) {
        updatableEntity.setName(entity.getName());
    }

    @Override
    protected Class<ManufacturerEntity> getManagedClass() {
        return ManufacturerEntity.class;
    }
}

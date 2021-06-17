package com.example.carreg.manufacturer.controller;

import com.example.carreg.manufacturer.controller.response.ManufacturerListResponse;
import com.example.carreg.manufacturer.entity.ManufacturerEntity;
import com.example.carreg.manufacturer.service.impl.ManufacturerServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
public class ManufacturerController {

    @Autowired
    private ManufacturerServiceImpl service;

    @GetMapping(value = "/api/manufacturer/{id}")
    public ResponseEntity findById(@PathVariable Long id) {
        ManufacturerEntity entity = service.findById(id);
        if (entity != null) {
            return ResponseEntity.ok(entity);
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
    }

    @GetMapping(value = "/api/manufacturer")
    public ResponseEntity<ManufacturerListResponse> findAll() {
        ManufacturerListResponse response = new ManufacturerListResponse();
        response.setmanufacturers(service.findAll());
        return ResponseEntity.ok(response);
    }


    //create - egy könyv létrehozása
    @PostMapping(value = "/api/manufacturer", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ManufacturerEntity> create(@RequestBody ManufacturerEntity entity) {
        service.create(entity);
        return ResponseEntity.ok(entity);
    }

    @PutMapping(value = "/api/manufacturer", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ManufacturerEntity> update(@RequestBody ManufacturerEntity entity) {
        return ResponseEntity.ok(service.update(entity));
    }


    @DeleteMapping(value = "/api/manufacturer/{id}")
    public ResponseEntity<String> deleteById(@PathVariable Long id) {
        if(service.deleteById(id)){
            return ResponseEntity.ok("Sikeres művelet");
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
    }

}

package com.education.project.cars.manager.carsmanager.IOService;

import com.education.project.cars.manager.carsmanager.model.Car;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.education.project.cars.manager.carsmanager.service.CarList;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.IOException;

@Service
public class WriteServiceJsonAltImp implements WriteService{

    private final ObjectMapper objectMapper = new ObjectMapper();

    @Override
    public CarList carListWrite(CarList cars, String file) {
        try {
            objectMapper.writeValue(new File(file), cars);
            return cars;
        }
        catch (IOException e){
            System.out.println(e.getMessage());
            return null;
        }
    }

    @Override
    public Car carWrite(Car car, String fileName) {
        return null;
    }

    @Override
    public Car carUpdate(Long idc, Car car, String table) {
        return null;
    }

    @Override
    public void carErase(Long idc, String fileName) {}


}

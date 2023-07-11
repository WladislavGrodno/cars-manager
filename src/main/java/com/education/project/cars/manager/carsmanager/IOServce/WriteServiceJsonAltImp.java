package com.education.project.cars.manager.carsmanager.IOServce;

import com.education.project.cars.manager.carsmanager.model.Car;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.education.project.cars.manager.carsmanager.service.CarList;
//import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.IOException;

@Service
//@Primary
public class WriteServiceJsonAltImp implements WriteService{

    private final ObjectMapper objectMapper = new ObjectMapper();

    @Override
    public CarList carListWriter(CarList cars, String file) {
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
    public Car carWriter(Car car, String fileName) {
        return null;
    }

    @Override
    public void carEraser(Long idc, String fileName) {}
}

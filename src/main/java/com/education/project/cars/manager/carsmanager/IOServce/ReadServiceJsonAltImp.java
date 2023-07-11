package com.education.project.cars.manager.carsmanager.IOServce;

import com.education.project.cars.manager.carsmanager.model.Car;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Service;
import com.education.project.cars.manager.carsmanager.service.CarList;

import java.io.IOException;

@Service
public class ReadServiceJsonAltImp implements ReadService{
    private final ObjectMapper objectMapper = new ObjectMapper();
    @Override
    public CarList carListReader(String file) {
        try {
            return objectMapper.readValue(file, CarList.class);
        }
        catch (IOException e){
            System.out.print("bad " + file);
            System.out.println(e.getMessage());
            return new CarList();
        }
    }

    @Override
    public Car carReader(Long idc, String fileName) {
        return null;
    }
}

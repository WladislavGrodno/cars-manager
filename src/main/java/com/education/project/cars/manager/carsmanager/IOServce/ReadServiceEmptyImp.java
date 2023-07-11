package com.education.project.cars.manager.carsmanager.IOServce;

import com.education.project.cars.manager.carsmanager.model.Car;
import org.springframework.stereotype.Service;
import com.education.project.cars.manager.carsmanager.service.CarList;

@Service
public class ReadServiceEmptyImp implements ReadService{
    @Override
    public CarList carListReader(String fileName) {
        return null;
    }

    @Override
    public Car carReader(Long idc, String fileName) {
        return null;
    }
}

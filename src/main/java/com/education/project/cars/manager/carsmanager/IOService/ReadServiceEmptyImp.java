package com.education.project.cars.manager.carsmanager.IOService;

import com.education.project.cars.manager.carsmanager.model.Car;
import org.springframework.stereotype.Service;
import com.education.project.cars.manager.carsmanager.service.CarList;

@Service
public class ReadServiceEmptyImp implements ReadService{
    @Override
    public CarList carListRead(String fileName) {
        return null;
    }
    @Override
    public CarList carListCustomRead(String sortBy, String filter, String fileName) {
        return null;
    }
    @Override
    public Car carRead(Long idc, String fileName) {
        return null;
    }
}

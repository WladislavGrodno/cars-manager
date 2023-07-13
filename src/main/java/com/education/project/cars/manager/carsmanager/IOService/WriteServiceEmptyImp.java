package com.education.project.cars.manager.carsmanager.IOService;

import com.education.project.cars.manager.carsmanager.model.Car;
import org.springframework.stereotype.Service;
import com.education.project.cars.manager.carsmanager.service.CarList;

@Service
public class WriteServiceEmptyImp implements WriteService{
    @Override
    public CarList carListWrite(CarList list, String fileName) {
        return list;
    }

    @Override
    public Car carWrite(Car car, String fileName) {
        return car;
    }

    @Override
    public void carErase(Long idc, String fileName) {}

    @Override
    public Car carUpdate(Long idc, Car car, String table) {
        return car;
    }
}

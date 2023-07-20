package com.education.project.cars.manager.carsmanager.service;

import com.education.project.cars.manager.carsmanager.IOService.IOService;
import com.education.project.cars.manager.carsmanager.model.Car;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class CarServiceDBImp implements CarService{
    @Autowired
    @Qualifier("IOServiceDBImp")
    private IOService carService;

    @Override
    public CarList carListRead() {
        return carService.carListRead();
    }

    @Override
    public CarList carListCustomRead(
            String sortBy, String filter) {
        return carService.carListCustomRead(sortBy, filter);
    }

    @Override
    public Car carRead(Long idc) {
        return carService.carRead(idc);
    }

    @Override
    public CarList carListWrite(CarList list) {
        return carService.carListWrite(list);
    }

    @Override
    public Car carWrite(Car car) {
        return carService.carWrite(car);
    }

    @Override
    public Car carUpdate(Long idc, Car car) {
        return carService.carUpdate(idc, car);
    }

    @Override
    public void carErase(Long idc) {
        carService.carErase(idc);
    }

}

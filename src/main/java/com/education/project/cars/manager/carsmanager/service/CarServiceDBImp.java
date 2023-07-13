package com.education.project.cars.manager.carsmanager.service;

import com.education.project.cars.manager.carsmanager.IOService.ReadService;
import com.education.project.cars.manager.carsmanager.IOService.WriteService;
import com.education.project.cars.manager.carsmanager.model.Car;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class CarServiceDBImp implements CarService{
    @Autowired
    @Qualifier("writeServiceDBImp")
    private WriteService carServiceOut;

    @Autowired
    @Qualifier("readServiceDBImp")
    private ReadService carServiceIn;

    /*
    @Autowired
    @Qualifier("carServiceDBAltImp")
    private CarService carService;
     */

    @Override
    public CarList carListRead(String fileName) {
        return carServiceIn.carListRead(fileName);
    }

    @Override
    public CarList carListCustomRead(
            String sortBy, String filter, String fileName) {
        return carServiceIn.carListCustomRead(sortBy, filter, fileName);
    }

    @Override
    public Car carRead(Long idc, String fileName) {
        return carServiceIn.carRead(idc, fileName);
    }

    @Override
    public CarList carListWrite(CarList list, String fileName) {
        return carServiceOut.carListWrite(list, fileName);
    }

    @Override
    public Car carWrite(Car car, String fileName) {
        return carServiceOut.carWrite(car, fileName);
    }

    @Override
    public Car carUpdate(Long idc, Car car, String table) {
        return carServiceOut.carUpdate(idc, car, table);
    }

    @Override
    public void carErase(Long idc, String fileName) {
        carServiceOut.carErase(idc, fileName);
    }

}

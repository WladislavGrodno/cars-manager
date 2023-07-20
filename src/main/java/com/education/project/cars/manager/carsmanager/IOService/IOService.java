package com.education.project.cars.manager.carsmanager.IOService;

import com.education.project.cars.manager.carsmanager.model.Car;
import com.education.project.cars.manager.carsmanager.service.CarList;

public interface IOService {
    CarList carListWrite(CarList list);
    Car carWrite(Car car);
    Car carUpdate(Long idc, Car car);
    void carErase(Long idc);
    CarList carListRead();
    CarList carListCustomRead(String sortBy, String filter);
    Car carRead(Long idc);
}

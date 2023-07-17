package com.education.project.cars.manager.carsmanager.service;

import com.education.project.cars.manager.carsmanager.model.Car;

public interface CarService {
    CarList carListRead();
    CarList carListCustomRead(
            String sortBy, String filter);
    Car carRead(Long idc);
    CarList carListWrite(CarList list);
    Car carWrite(Car car);
    Car carUpdate(Long idc, Car car);
    void carErase(Long idc);

}

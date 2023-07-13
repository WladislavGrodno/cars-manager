package com.education.project.cars.manager.carsmanager.service;

import com.education.project.cars.manager.carsmanager.model.Car;

public interface CarService {
    CarList carListRead(String fileName);
    CarList carListCustomRead(
            String sortBy, String filter, String fileName);
    Car carRead(Long idc, String fileName);
    CarList carListWrite(CarList list, String fileName);
    Car carWrite(Car car, String fileName);
    Car carUpdate(Long idc, Car car, String table);
    void carErase(Long idc, String fileName);

}

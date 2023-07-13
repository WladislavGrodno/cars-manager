package com.education.project.cars.manager.carsmanager.IOService;

import com.education.project.cars.manager.carsmanager.model.Car;
import com.education.project.cars.manager.carsmanager.service.CarList;

public interface WriteService {
    CarList carListWrite(CarList list, String fileName);
    Car carWrite(Car car, String fileName);
    Car carUpdate(Long idc, Car car, String table);
    void carErase(Long idc, String fileName);
}

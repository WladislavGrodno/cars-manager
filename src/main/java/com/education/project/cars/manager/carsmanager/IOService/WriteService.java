package com.education.project.cars.manager.carsmanager.IOService;

import com.education.project.cars.manager.carsmanager.model.Car;
import com.education.project.cars.manager.carsmanager.service.CarList;

public interface WriteService {
    CarList carListWriter(CarList list, String fileName);
    Car carWriter(Car car, String fileName);
    Car carUpdater(Long idc, Car car, String table);
    void carEraser(Long idc, String fileName);

}

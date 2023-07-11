package com.education.project.cars.manager.carsmanager.IOServce;

import com.education.project.cars.manager.carsmanager.model.Car;
import com.education.project.cars.manager.carsmanager.service.CarList;

public interface WriteService {
    CarList carListWriter(CarList list, String fileName);
    Car carWriter(Car car, String fileName);
    void carEraser(Long idc, String fileName);
}

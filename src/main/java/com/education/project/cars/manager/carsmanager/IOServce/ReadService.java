package com.education.project.cars.manager.carsmanager.IOServce;

import com.education.project.cars.manager.carsmanager.model.Car;
import com.education.project.cars.manager.carsmanager.service.CarList;

public interface ReadService {
    CarList carListReader(String fileName);
    Car carReader(Long idc, String fileName);
}

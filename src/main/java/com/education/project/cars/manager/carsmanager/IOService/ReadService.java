package com.education.project.cars.manager.carsmanager.IOService;

import com.education.project.cars.manager.carsmanager.model.Car;
import com.education.project.cars.manager.carsmanager.service.CarList;

public interface ReadService {
    CarList carListRead(String fileName);
    CarList carListCustomRead(
            String sortBy, String filter, String fileName);

    Car carRead(Long idc, String fileName);
}
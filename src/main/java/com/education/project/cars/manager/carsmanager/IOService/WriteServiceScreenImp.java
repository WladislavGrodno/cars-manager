package com.education.project.cars.manager.carsmanager.IOService;

import com.education.project.cars.manager.carsmanager.model.Car;
import com.education.project.cars.manager.carsmanager.service.CarList;
import org.springframework.stereotype.Service;

@Service
public class WriteServiceScreenImp implements WriteService{
    @Override
    public CarList carListWrite(CarList list, String fileName) {
        System.out.printf(
                "%21s. %15s: %15s, %5s. %20s%n",
                "ИД номер", "Марка", "Модель", "Год", "Цена");
        list.forEach(System.out::println);
        return list;
    }

    @Override
    public Car carWrite(Car car, String fileName) {
        System.out.println(car);
        return car;
    }

    @Override
    public Car carUpdate(Long idc, Car car, String table) {
        return null;
    }

    @Override
    public void carErase(Long idc, String fileName) {
    }
}

package com.education.project.cars.manager.carsmanager.IOServce;

import com.education.project.cars.manager.carsmanager.model.Car;
import com.education.project.cars.manager.carsmanager.service.CarList;
import org.springframework.stereotype.Service;

@Service
public class WriteServiceScreenImp implements WriteService{
    @Override
    public CarList carListWriter(CarList list, String fileName) {
        System.out.printf(
                "%21s. %15s: %15s, %5s. %20s%n",
                "ИД номер", "Марка", "Модель", "Год", "Цена");
        list.forEach(System.out::println);
        return list;
    }

    @Override
    public Car carWriter(Car car, String fileName) {
        System.out.println(car);
        return car;
    }

    @Override
    public void carEraser(Long idc, String fileName) {
    }
}

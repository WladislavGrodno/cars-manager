package com.education.project.cars.manager.carsmanager.IOService;

import com.education.project.cars.manager.carsmanager.model.Car;
import com.education.project.cars.manager.carsmanager.service.CarList;
import org.springframework.stereotype.Service;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

@Service
public class WriteServiceTxtAltImp implements WriteService{

    @Override
    public CarList carListWrite(CarList cars, String file) {
        try(BufferedWriter writer =
                    new BufferedWriter(new FileWriter(file, false))) {
            writer.write("CarList object\n");
            cars.forEach(car -> {
                try {
                    writer.write(car.getYear().toString());writer.append('\n');
                    writer.write(car.getBrand()); writer.append('\n');
                    writer.write(car.getModel()); writer.append('\n');
                    writer.write(car.getCost().toString());
                    writer.append('\n');
                } catch (IOException e) {
                    System.out.println("Error of writing");
                    System.out.println(e.getMessage());
                }
            });
            return cars;
        }
        catch (IOException e){
            System.out.println("Can't open file");
            System.out.println(e.getMessage());
        }
        return null;
    }

    @Override
    public Car carWrite(Car car, String fileName) {
        return null;
    }

    @Override
    public Car carUpdate(Long idc, Car car, String table) {
        return null;
    }

    @Override
    public void carErase(Long idc, String fileName) {}
}

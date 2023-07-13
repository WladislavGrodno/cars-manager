package com.education.project.cars.manager.carsmanager.IOService;

import com.education.project.cars.manager.carsmanager.model.Car;
import org.springframework.stereotype.Service;
import com.education.project.cars.manager.carsmanager.service.CarList;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

@Service
public class ReadServiceTxtAltImp implements ReadService{
    @Override
    public CarList carListRead(String file) {
        try(BufferedReader reader =
                    new BufferedReader(new FileReader(file))) {
            if (reader.readLine().equals("CarList object")){
                CarList carList = new CarList();
                String carYear;
                while ((carYear = reader.readLine()) != null){
                    carList.add(new Car(
                            Integer.parseInt(carYear),
                            reader.readLine(),
                            reader.readLine(),
                            Integer.parseInt(reader.readLine())
                    ));
                }
                return carList;
            }
            else {
                System.out.println("Alien file");
            }
        }
        catch (IOException e){
            System.out.println("Can't open file");
            System.out.println(e.getMessage());
        }
        return new CarList();
    }

    @Override
    public Car carRead(Long idc, String fileName) {
        return null;
    }

    @Override
    public CarList carListCustomRead(
            String sortBy, String filter, String fileName) {
        return null;
    }
}

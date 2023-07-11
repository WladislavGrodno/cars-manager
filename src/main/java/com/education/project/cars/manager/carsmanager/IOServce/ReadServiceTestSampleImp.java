package com.education.project.cars.manager.carsmanager.IOServce;

import com.education.project.cars.manager.carsmanager.model.Car;
import org.springframework.stereotype.Service;
import com.education.project.cars.manager.carsmanager.service.CarList;

@Service
public class ReadServiceTestSampleImp implements ReadService{
    @Override
    public CarList carListReader(String fileName) {
        CarList garage = new CarList();
        garage.add(new Car(2002,"Mercedes", "w140", 15000));
        garage.add(new Car(1996,"Mercedes", "w140", 10000));
        garage.add(new Car(2019,"VW", "Polo", 10000));
        garage.add(new Car(2016,"VW", "Golf", 12000));
        garage.add(new Car(2012,"VW", "Passat", 8000));
        garage.add(new Car(1998,"Audi", "100", 500));
        garage.add(new Car(2010,"Audi", "A4", 8000));
        garage.add(new Car(2012,"Audi", "A4", 9000));
        garage.add(new Car(2020,"Audi", "A5", 30000));
        garage.add(new Car(2005,"Volvo", "S90", 8000));
        garage.add(new Car(2014,"Volvo", "S80", 18000));
        garage.add(new Car(2020,"Volvo", "S60", 20000));
        garage.add(new Car(2022,"Volvo", "S60", 23000));
        garage.add(new Car(2006,"Suzuki", "Baleno", 3000));
        garage.add(new Car(2019,"Suzuki", "Swift", 14000));
        return garage;
    }

    @Override
    public Car carReader(Long idc, String fileName) {
        return null;
    }
}

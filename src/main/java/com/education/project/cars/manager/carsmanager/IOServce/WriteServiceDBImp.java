package com.education.project.cars.manager.carsmanager.IOServce;
import com.education.project.cars.manager.carsmanager.model.Car;
import com.education.project.cars.manager.carsmanager.service.CarList;
import com.education.project.cars.manager.carsmanager.service.DBPoolService;

import java.util.Random;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Slf4j
@Getter
@Setter
@Service
public class WriteServiceDBImp implements WriteService {
    @Autowired
    private DBPoolService source;

    @Autowired
    private ReadServiceDBImp readService;

    public WriteServiceDBImp(DBPoolService source) {
        this.source = source;
    }

    @Override
    public CarList carListWriter(CarList list, String fileName) {
        CarList listOut = new CarList();
        list.forEach(car -> {
            Car carOut = carWriter(car, fileName);
            if (carOut != null) listOut.add(carOut);
        });
        return listOut;
    }

    @Override
    public Car carWriter(Car car, String table) {
        Integer label = new Random().nextInt();
        do {
            Long idCar = new Random().nextLong();
            car.setIdCar(idCar);
            log.debug("{\"newIDCar\": {}, \"label\": {}}", idCar, label);
            source.writeDB(
                    String.format("INSERT INTO %s " +
                                    "(IDC, Year, Brand, Model, Cost) VALUES " +
                                    "(%d, %d, '%s', '%s', %d);",
                            table,
                            car.getIdCar(),
                            car.getYear(),
                            car.getBrand(),
                            car.getModel(),
                            car.getCost())
            );
        } while (source.getStatus() == -8);
        if (source.getStatus() == 0) return car;
        else return null;
    }

    public Car carUpdater(Long idc, Car car, String table) {
        Car carControl = readService.carReader(idc, table);
        if (carControl == null) return null;

        if (car.getYear() == -1) car.setYear(carControl.getYear());
        if (car.getBrand().equals("empty")) car.setBrand(carControl.getBrand());
        if (car.getModel().equals("empty")) car.setModel(carControl.getModel());
        if (car.getCost() == -1) car.setCost(carControl.getCost());

        source.writeDB(
                String.format(
                        "UPDATE %s SET (Idc, Year, Brand, Model, Cost) = " +
                                "(%d, %d, '%s', '%s', %d) WHERE Idc = %d;",
                        table,
                        idc,
                        car.getYear(),
                        car.getBrand(),
                        car.getModel(),
                        car.getCost(),
                        idc
                )
        );
        if (source.getStatus() != 0) return null;
        car.setIdCar(idc);
        return car;
    }

    @Override
    public void carEraser(Long idc, String table) {
        source.writeDB(
                String.format("DELETE FROM %s WHERE Idc = %d;", table, idc));
    }

}

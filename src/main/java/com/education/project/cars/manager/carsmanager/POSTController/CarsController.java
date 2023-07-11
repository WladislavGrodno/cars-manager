package com.education.project.cars.manager.carsmanager.POSTController;

import com.education.project.cars.manager.carsmanager.IOServce.ReadServiceDBImp;
import com.education.project.cars.manager.carsmanager.IOServce.WriteServiceDBImp;
import com.education.project.cars.manager.carsmanager.model.Car;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.*;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Collection;

/**
 * CRUD операции для создания автомобилей в БД постгрес
 */
@Slf4j
@RestController
public class CarsController {
    private final String table = "Garage";

    /*
    @Autowired
    @Qualifier("WriteServiceDBImp")
    private WriteService carServiceOut;
     */
    @Autowired
    private WriteServiceDBImp carServiceOut;

    /*
    @Autowired
    @Qualifier("ReadServiceDBImp")
    private ReadService carServiceIn;
     */

    @Autowired
    private ReadServiceDBImp carServiceIn;

    /**
     * логика создания машины в БД и возвращение ее в ответе
     * @param car inserted car
     * @return inserted car
     */
    @PostMapping("/cars")
    public Car createCar(@RequestBody Car car) {
        log.info("{\"insertCar\": {\"car\": \"{}\", \"table\": \"{}\"}}",
                car, table);
        return carServiceOut.carWriter(car, table);
    }

    /**
     * логика обнволения машины по id в БД и возвращение ее в ответе
     * @param car updated car
     * @param id identifier of updated car
     * @return updated car
     */
    @PutMapping("/cars/{id}")
    public Car updateCar(@RequestBody Car car, @PathVariable Long id) {
        log.info("{\"updateCar\": " +
                "{\"id\": {}, \"car\": \"{}\", \"table\": \"{}\"}}",
                id, car, table);
        return carServiceOut.carUpdater(id, car, table);
    }

    /**
     * вернуть список всех машин которые лежат в бд
     * @return список всех машин которые лежат в бд
     */
    @GetMapping("/cars")
    public Collection<Car> getCars() {
        log.info("{\"returnTable\": {\"table\": \"{}\"}}", table);
        log.debug("debug");
        log.error("no error");
        return carServiceIn.carListReader(table);
    }

    /**
     * вернуть машину по id
     * @param id identifier of requested car
     * @return requested car
     */
    @GetMapping("/cars/{id}")
    public Car getCarById(@PathVariable Long id) {
        log.info("{\"returnCar\": {\"id\": {}, \"table\": \"{}\"}}", id, table);
        return carServiceIn.carReader(id, table);
    }

    /**
     * удалить машину по id
     * @param id identifier of deleted car
     */
    @DeleteMapping("/cars/{id}")
    public void deleteCarById(@PathVariable Long id) {
        log.info("{\"deleteCar\": {\"id\": {}, \"table\": \"{}\"}}", id, table);
        carServiceOut.carEraser(id, table);
    }
}
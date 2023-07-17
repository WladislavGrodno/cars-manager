package com.education.project.cars.manager.carsmanager.ApiController;

import com.education.project.cars.manager.carsmanager.model.Car;

import com.education.project.cars.manager.carsmanager.service.CarService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;

import lombok.extern.slf4j.Slf4j;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.DeleteMapping;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.Collection;

/**
 * CRUD операции для создания автомобилей в БД постгрес
 */
@Slf4j
@RestController
@Tag(name = "Cars-manager API")
//@ApiResponse
public class CarsController {
    @Autowired
    @Qualifier("carServiceDBImp")
    private CarService carService;

    /**
     * логика создания машины в БД и возвращение ее в ответе
     * @param car inserted car
     * @return inserted car
     */
    @PostMapping("/cars")
    @Operation(
            summary = "Add a new car to Garage",
//          description = "Register car in table ${table}")
            description = "Register car in the table Garage")
    @ApiResponse(responseCode = "200", description = "Successfully retrieved")
    @ApiResponse(responseCode = "400", description = "Bad request")
    @ApiResponse(responseCode = "404", description = "The car was not created")
    @ApiResponse(responseCode = "500", description = "Internal server error")
    public Car createCar(@RequestBody Car car) {
        log.info("{\"insertCar\": {\"car\": \"{}\"}}",
                car);
        return carService.carWrite(car);
    }

    /**
     * логика обнволения машины по id в БД и возвращение ее в ответе
     * @param car updated car
     * @param id identifier of updated car
     * @return updated car
     */
    @PutMapping("/cars/{id}")
    @Operation(
            summary = "Update an existing car",
            description = "Update an existing car by Id")
    @ApiResponse(responseCode = "200", description = "Successfully retrieved")
    @ApiResponse(responseCode = "400", description = "Bad request")
    @ApiResponse(responseCode = "404", description = "The car was not found")
    @ApiResponse(responseCode = "500", description = "Internal server error")
    public Car updateCar(@RequestBody Car car, @PathVariable Long id) {
        log.info("{\"updateCar\": {\"id\": {}, \"car\": \"{}\"}}", id, car);
        return carService.carUpdate(id, car);
    }

    /**
     * вернуть список всех машин которые лежат в бд
     * @return список всех машин которые лежат в бд
     */
    @GetMapping("/cars")
    @Operation(
            summary = "Returns all cars",
            description = "Returns all cars from table")
    @ApiResponse(responseCode = "200", description = "Successfully retrieved")
    @ApiResponse(responseCode = "400", description = "Bad request")
    @ApiResponse(responseCode = "404", description = "The database is empty")
    @ApiResponse(responseCode = "500", description = "Internal server error")
    public Collection<Car> getCars() {
        log.info("{\"returnTable\": {\"Garage\"}}");
        return carService.carListRead();
    }

    /**
     * вернуть фильтрованный и упорядоченный список машин
     * @param sortBy список параметров сортировки: <br>
     *               [field for sort by.[sort direction: ASC | DESC]].
     *               [field for sort by.[sort direction: ASC. | DESC.]]
     *               ...
     * @param filter параметры фильтрации:
     *               ((condition: equal | not_equal).field.value) | .
     * @return фильтрованный и упорядоченный список машин
     */
    @GetMapping("/carsCustom")
    @Operation(
            summary = "Returns selected cars",
            description = "Returns selected cars from table")
    @ApiResponse(responseCode = "200", description = "Successfully retrieved")
    @ApiResponse(responseCode = "400", description = "Bad request")
    @ApiResponse(responseCode = "404", description = "The database is empty")
    @ApiResponse(responseCode = "500", description = "Internal server error")
    public Collection<Car> getCustomCars(
            @Parameter(description =
                    "List of field for sort with direction\n" +
                            "example: brand.year.desc")
            @RequestParam("sortBy")
            String sortBy,
            @Parameter(description =
                    "equal.brand.VW\n" +
                    "not_equal.brand.Opel")
            @RequestParam("filter"
            ) String filter
            ) {
        log.info("{\"returnTable\": {" +
                        "\"sort\": {}, " +
                        "\"filter\": \"{}\"" +
                        "}}",
                sortBy, filter);
        return carService.carListCustomRead(sortBy, filter);
    }


    /**
     * вернуть машину по id
     * @param id identifier of requested car
     * @return requested car
     */
    @GetMapping("/cars/{id}")
    @Operation(
            summary = "Find a car by ID",
            description = "Return a single car by ID")
    @ApiResponse(responseCode = "200", description = "Successfully retrieved")
    @ApiResponse(responseCode = "400", description = "Bad request")
    @ApiResponse(responseCode = "404", description = "The car was not found")
    @ApiResponse(responseCode = "500", description = "Internal server error")
    public Car getCarById(@PathVariable Long id) {
        log.info("{\"returnCar\": {\"id\": {}}}", id);
        return carService.carRead(id);
    }

    /**
     * удалить машину по id
     * @param id identifier of deleted car
     */
    @DeleteMapping("/cars/{id}")
    @Operation(
            summary = "Delete a car",
            description = "Delete a car")
    @ApiResponse(responseCode = "200", description = "Successfully retrieved")
    @ApiResponse(responseCode = "400", description = "Bad request")
    @ApiResponse(responseCode = "500", description = "Internal server error")

    public void deleteCarById(@PathVariable Long id) {
        log.info("{\"deleteCar\": {\"id\": {}}}", id);
        carService.carErase(id);
    }
}
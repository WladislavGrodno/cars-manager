package com.education.project.cars.manager.carsmanager.service;

import com.education.project.cars.manager.carsmanager.dto.request.CarRequest;
import com.education.project.cars.manager.carsmanager.dto.response.CarResponse;
import com.education.project.cars.manager.carsmanager.mapper.CarMapper;
import com.education.project.cars.manager.carsmanager.model.Car;
import com.education.project.cars.manager.carsmanager.model.CarPage;
import com.education.project.cars.manager.carsmanager.model.CarSearchCriteria;
import com.education.project.cars.manager.carsmanager.repository.CarCriteriaRepository;
import com.education.project.cars.manager.carsmanager.repository.CarRepository;

import jakarta.validation.constraints.NotNull;
import lombok.extern.slf4j.Slf4j;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

@Slf4j
@Service
public class CarServiceDBImp implements CarService{
    /*
    @Autowired
    @Qualifier("IOServiceDBImp")
    private IOService carService;
     */

    @Autowired
    private CarCriteriaRepository carCriteriaRepository;
    @Autowired
    private CarRepository carRepository;
    @Autowired
    private CarMapper mapper;

    //ready
    @Override
    public ResponseEntity<Collection<CarResponse>> carListRead()
            throws Exception {
        log.info("{\"returnTable\": {\"Garage\"}}");
        Collection<Car> carsControl = carRepository.findAll();
        if (carsControl.isEmpty()) throw new Exception("TABLE IS EMPTY");
        return new ResponseEntity<>(
                mapper.toDtoList(carsControl),
                HttpStatus.OK);

    }

    @Override
    public List<Car> carListCustomRead(
            String sortBy, String filter) {
//        return carService.carListCustomRead(sortBy, filter);
        return null;
        //ToDo
    }

    @Override
    public ResponseEntity<Page<Car>> carListAlienCustomRead(
            CarPage carPage,
            CarSearchCriteria carSearchCriteria
            ){
        log.info("{\"getAlienCars\": {" +
                        "\"carPage\": \"{}," +
                        "\"carSearchCriteria\": \"{}" +
                        "\"}}",
                carPage, carSearchCriteria);
        return new ResponseEntity<>(
                carCriteriaRepository.findAllWithFilters(
                        carPage, carSearchCriteria),
                HttpStatus.OK
        );
    }

    @Override
    public List<Car> carListWrite(List<Car> list) {
        return carRepository.saveAll(list);
    }

    //ready
    @Override
    public ResponseEntity<CarResponse> carWrite(
            @NotNull CarRequest car) {
        log.info("{\"insertCar\": {\"car\": \"{}\"}}", car);
        return new ResponseEntity<>(
                mapper.toDto(carRepository.save(mapper.toCar(0L, car))),
                HttpStatus.OK);
    }

    //ready
    @Override
    public ResponseEntity<CarResponse> carUpdate(
            @NotNull Long idc,
            @NotNull CarRequest car)
            throws Exception {
        log.info("{\"updateCar\": {\"id\": {}, \"car\": \"{}\"}}", idc, car);
        Optional<Car> carControl = carRepository.findById(idc);
        if (carControl.isEmpty()) throw new Exception("NOT FOUND");
        return new ResponseEntity<>(
                mapper.toDto(carRepository.save(mapper.toCar(idc, car))),
                HttpStatus.OK);
    }

    //ready
    @Override
    public ResponseEntity<CarResponse> carRead(@NotNull Long idc)
            throws Exception {
        log.info("{\"returnCar\": {\"id\": {}}}", idc);
        Optional<Car> carControl = carRepository.findById(idc);
        if (carControl.isEmpty()) throw new Exception("NOT FOUND");
        return new ResponseEntity<>(
                mapper.toDto(carControl.get()),
                HttpStatus.OK);
    }


    //ready
    @Override
    public void carErase(@NotNull Long idc) throws Exception{
        log.info("{\"deleteCar\": {\"id\": {}}}", idc);
        carRepository.deleteById(idc);
        throw new Exception("OPERATION SUCCESSFUL");
    }


    private String orderList(String[] list, int i){
        if (i >= list.length) return "";
        switch (list[i].toLowerCase()) {
            case "year", "brand", "model", "cost" -> {
                if (i + 1 >= list.length) return list[i];
                switch (list[i + 1].toLowerCase()) {
                    case "asc", "desc" -> {
                        String s = orderList(list, i + 2);
                        switch (s) {
                            case "b" -> {
                                return s;
                            }
                            case "" -> {
                                return list[i].toUpperCase() + " " +
                                        list[i + 1].toUpperCase();
                            }
                            default -> {
                                return list[i].toUpperCase() + " " +
                                        list[i + 1].toUpperCase()
                                        + ", " + s;
                            }
                        }
                    }
                    default -> {
                        String s = orderList(list, i + 1);
                        switch (s) {
                            case "b" -> {
                                return s;
                            }
                            case "" -> {
                                return list[i].toUpperCase();
                            }
                            default -> {
                                return list[i].toUpperCase() + ", " + s;
                            }
                        }
                    }
                }
            }
            default -> {return "b";}
        }
    }
    private String orderList(String[] list){
        return orderList(list, 0);
    }

    private String filterList(String[] list){
        switch (list.length) {
            case 0 -> {return "";}
            case 3 -> {
                String condition = list[0].toLowerCase();
                switch (condition) {
                    case "equal", "not_equal" -> {
                        String ethanol;
                        switch (list[1].toLowerCase()) {
                            case "brand", "model" ->
                                    ethanol = "'" + list[2] + "'";
                            case "year", "cost" ->
                                    ethanol = list[2];
                            default -> {return "b";}
                        }
                        return list[1] +
                                ((condition.equals("equal")) ? " = " : " <> ") +
                                ethanol;
                    }
            /*
            case "not_equal" -> {}
            case "between" -> {
                source.readDB("SELECT Year, Brand, Model, Cost FROM " + table +
                        " WHERE " + field + " BETWEEN " + min + " AND " + max + ";");
            }
            case "not_between" -> {}
             */
                    default -> {return "b";}
                }
            }
            default -> {return "b";}
        }
    }

    /*
    @Override
    public List<Car> carListCustomRead(String sortBy, String filter) {

        StringBuilder query = new StringBuilder();
        query.append("SELECT Idc, Year, Brand, Model, Cost FROM Garage");

        String sortPhrase = orderList(sortBy.split("\\."));
        String filterPhrase = filterList(filter.split("\\."));

        switch (filterPhrase){
            case "b" -> {return null;}
            case "" -> {}
            default -> query.append(" WHERE ").append(filterPhrase);
        }

        switch (sortPhrase){
            case "b" -> {return null;}
            case "" -> {}
            default -> query.append(" ORDER BY ").append(sortPhrase);
        }

        query.append(";");

        source.readDB(
                query.toString()
        );

        car.setIdCar(idc);
        return carRepository.save(car);

        //return carListGetFromRS(source.getRs());
    }

     */
}

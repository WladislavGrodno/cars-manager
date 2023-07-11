package com.education.project.cars.manager.carsmanager.service;

import com.education.project.cars.manager.carsmanager.LambdaInterface.IntGetter;
import com.education.project.cars.manager.carsmanager.model.Car;

import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.SQLException;

@Slf4j
@Setter
@Service
public class CarServiceDBAltImp implements CarService{
    @Autowired
    private DBPoolService source;
    private String table = "Garage";

    private Car getCarFromDBRow(){
        try {
            return new Car(
                    source.getRs().getInt("Year"),
                    source.getRs().getString("Brand"),
                    source.getRs().getString("Model"),
                    source.getRs().getInt("Cost")
            );
        }
        catch (SQLException e){
            log.error("{\"error\": \"{}\", \"state\": \"{}\"}",
                    e.getMessage(), e.getSQLState());
            return null;
        }
    }

    /**
     * Add Car object to CarList from current DB row
     * @param cars CarList car collector
     * @return Good reading
     */
    private boolean addCarToListFromDBRow(CarList cars){
        Car car = getCarFromDBRow();
        if (car == null) return false;
        cars.add(car);
        return true;
    }

    /**
     * Translate result of SQL query to CarList
     * @return CarList
     */
    private CarList getCarListFromDBSource(){
        //method for get car list of result set database
        CarList cars = new CarList();
        try {
            while (source.getRs().next()) {
                Car car = getCarFromDBRow();
                if (car == null) break;
                cars.add(car);
            }
        } catch (SQLException e) {
            log.error("{\"error\": \"{}\", \"state\": \"{}\"}",
                    e.getMessage(), e.getSQLState());
        }
        return cars;
    }

    public CarList getExtremeCar(CarList list, String field,
                                 IntGetter fieldValue, Order order) {
        source.readDB("SELECT Year, Brand, Model, Cost FROM " +
                table + " ORDER BY " + field +
                (order.equals(Order.DESC)? ";" : " DESC;"));
        CarList cars = new CarList();
        try {
            Integer maxPrice;
            if (source.getRs().next()) {
                Car car = getCarFromDBRow();
                if (car == null) return cars;
                maxPrice = fieldValue.get(car);
                cars.add(car);
                while (source.getRs().next()) {
                    car = getCarFromDBRow();
                    if (car == null || fieldValue.get(car) != maxPrice)
                        return cars;
                    cars.add(car);
                }
            }
        } catch (SQLException e) {
            log.error("{\"error\": \"{}\", \"state\": \"{}\"}",
                    e.getMessage(), e.getSQLState());
        }
        return cars;
    }

    @Override
    public CarList getMaxCostCar(CarList list) {
        return getExtremeCar(list, "Cost", Car::getCost, Order.ASC);
    }

    @Override
    public CarList getMinCostCar(CarList list) {
        return getExtremeCar(list, "Cost", Car::getCost, Order.DESC);
    }

    @Override
    public CarList findBrandList(String searchBrand, CarList list) {
        return filter("Brand", searchBrand);
    }

    @Override
    public CarList findModelList(String searchModel, CarList list) {
        return filter("Model", searchModel);
    }

    /**
     * Фильтрация списка по строковому значению поля
     * @param field поле для сравнения
     * @param ethanol эталонная строка
     * @return фильтрованный список
     */
    private CarList filter(String field, String ethanol){
        source.readDB("SELECT Year, Brand, Model, Cost FROM " + table +
                " WHERE " + field + " = '" + ethanol +"';");
        return getCarListFromDBSource();
    }


    /**
     * Фильтрация списка по числовому значению поля
     * @param field поле для сравнения
     * @param min минимально допустимое значение
     * @param max максимально допустимое значение
     * @return фильтрованный список
     */
    private CarList filter(String field, Integer min, Integer max){
        source.readDB("SELECT Year, Brand, Model, Cost FROM " + table +
                " WHERE " + field + " BETWEEN " + min + " AND " + max + ";");
        return getCarListFromDBSource();
    }

    @Override
    public CarList getListByPriceRange(
            int startPrice, int endPrice, CarList list) {
        return filter("Cost", startPrice, endPrice);
    }

    /**
     * Сортировка списка по заданному строковому полю
     * @param field поле сортировки
     * @param order направление сортировки
     * @return сортированный список
     */
    private CarList sort(String field, Order order){
        source.readDB("SELECT Year, Brand, Model, Cost FROM " +
                table + " ORDER BY " + field +
                (order.equals(Order.DESC)? " DESC;" : ";"));
        return getCarListFromDBSource();
    }

    @Override
    public CarList sortListByPrice(CarList list) {
        return sort("Cost", Order.ASC);
    }

    @Override
    public CarList sortListByBrand(CarList list) {
        return sort("Brand", Order.ASC);
    }

}

enum Order {
    DESC,
    ASC
}
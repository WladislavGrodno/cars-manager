package com.education.project.cars.manager.carsmanager.service;

import com.education.project.cars.manager.carsmanager.LambdaInterface.*;
import com.education.project.cars.manager.carsmanager.model.Car;
import org.springframework.stereotype.Service;

/**
 * Альтернативная реализация CarService
 */
@Service
public class CarServiceAltImp implements CarService{

    /**
     * Поиск в списке автомобилей с максимальной ценой
     * @param cars исходный список
     * @return список автомобилей с максимальной ценой
     */
    @Override
    public CarList getMaxCostCar(CarList cars){
        return getExtremeCar(cars, Car::getCost,
                (o1, o2) -> (o1 <= o2), (o1, o2) -> (o1 < o2));
    }

    /**
     * Поиск в списке автомобилей с минимальной ценой
     * @param cars исходный список
     * @return список автомобилей с минимальной ценой
     */
    @Override
    public CarList getMinCostCar(CarList cars){
        return getExtremeCar(cars, Car::getCost,
                (o1, o2) -> (o1 >= o2), (o1, o2) -> (o1 > o2));
    }

    /**
     * Поиск экстремальных объектов в списке
     * @param cars исходный список
     * @param fValue получатель значения Integer-поля (функции от объекта Car)
     * @param notStrict нестрогий принцип отбора (>= || <=),
     *                 где o1 - эталон, o2 - альтернативное
     * @param strict строгий принцип отбора (> || <),
     *              где o1 - эталон, o2 - альтернативное
     * @return список автомобилей с экстремальной ценой
     */
    private CarList getExtremeCar(CarList cars,
                                      IntGetter fValue,
                                      Comparator notStrict,
                                      Comparator strict) {
        Car ethanolCar = cars.get(0);// начальный объект
        Integer ethanol = fValue.get(ethanolCar);
        CarList extremeCars = new CarList();
        extremeCars.add(ethanolCar);
        // эталонное численное значение сравниваемого поля
        for (Car car : cars){
            Integer param = fValue.get(car);
            // численное значение поля альтернативного объекта
            if (notStrict.compare(ethanol, param)) {
                if (strict.compare(ethanol, param)) {
                    ethanol = param;
                    extremeCars.clear();
                }
                extremeCars.add(car);
            }
        }
        return extremeCars;
    }

    /**
     * Сортировка списка по заданному Integer-полю
     * @param cars исходный список
     * @param fValue получатель значения Integer-поля (функции от объекта Car)
     * @return отсортированный список
     */
    private CarList sort(CarList cars, IntGetter fValue){
        if(fValue.get(cars.get(0)) != null)
            cars.sort(java.util.Comparator.comparing(fValue::get));
        return cars;
    }

    /**
     * Сортировка списка по заданному строковому полю
     * @param cars исходный список
     * @param fValue получатель строкового значения поля
     *              (функции от объекта Car)
     * @return сортированный список
     */
    private CarList sort(CarList cars, StringGetter fValue){
        if(fValue.get(cars.get(0)) != null)
            cars.sort(java.util.Comparator.comparing(fValue::get));
        return cars;
    }

    /**
     * Сортировка списка по цене автомобиля
     * @param cars исходный список
     * @return сортирорванный список
     */
    @Override
    public CarList sortListByPrice(CarList cars) {
        return sort(cars, Car::getCost);
    }

    /**
     * Сортировка списка по марке автомобиля
     * @param cars исходный список
     * @return сортирорванный список
     */
    @Override
    public CarList sortListByBrand(CarList cars) {
        return sort(cars, Car::getBrand);
    }

    /**
     * Фильтрация списка по строковому значению поля
     * @param cars исходный список
     * @param fValue поле для сравнения
     * @param ethanol эталонная строка
     * @return фильтрованный список
     */
    private CarList filter(CarList cars, StringGetter fValue, String ethanol){
        CarList newList = new CarList();
        for (Car car : cars)
            if(fValue.get(car).equals(ethanol))
                newList.add(car);
        return newList;
    }

    /**
     * Фильтрация списка по числовому значению поля
     * @param cars исходный список
     * @param fValue поле для сравнения
     * @param min минимально допустимое значение
     * @param max максимально допустимое значение
     * @return фильтрованный список
     */
    private CarList filter(CarList cars,
                           IntGetter fValue, Integer min, Integer max){
        CarList newList = new CarList();
        for (Car car : cars) {
            Integer n = fValue.get(car);
            if (n >= min && n <= max) newList.add(car);
        }
        return newList;
    }

    /**
     * Фильтрация списка по марке автомобиля
     * @param searchBrand искомая марка
     * @param cars исходный список
     * @return фильтрорванный список
     */
    @Override
    public CarList findBrandList(String searchBrand, CarList cars) {
        return filter(cars, Car::getBrand, searchBrand);
    }

    /**
     * Фильтрация списка по марке автомобиля
     * @param searchModel искомая модель
     * @param cars исходный список
     * @return фильтрорванный список
     */
    @Override
    public CarList findModelList(String searchModel, CarList cars) {
        return filter(cars, Car::getModel, searchModel);
    }

    /**
     * Фильтрация списка по диапазону цен
     * @param minPrice минимальная цена
     * @param maxPrice максимальная цена
     * @param cars исходный список
     * @return фильтрорванный список
     */
    @Override
    public CarList getListByPriceRange(
            int minPrice, int maxPrice, CarList cars) {
        return filter(cars, Car::getCost, minPrice, maxPrice);
    }
}


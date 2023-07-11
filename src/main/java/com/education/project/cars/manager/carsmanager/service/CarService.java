package com.education.project.cars.manager.carsmanager.service;

public interface CarService {
    CarList getMaxCostCar(CarList list);
    CarList getMinCostCar(CarList list);
    CarList findBrandList(String searchBrand, CarList list);
    CarList findModelList(String searchModel, CarList list);
    CarList getListByPriceRange(int startPrice, int endPrice, CarList list);
    CarList sortListByPrice(CarList list);
    CarList sortListByBrand(CarList list);
    default void printCarList(CarList list){
        list.forEach(car -> {
            String strF = String.format("Year: %d, brand: %s, model: %s, cost: %d\n",
                    car.getYear(), car.getBrand(), car.getModel(), car.getCost());
            System.out.print(strF);
        });
    }
}

package com.education.project.cars.manager.carsmanager.service;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import com.education.project.cars.manager.carsmanager.IOServce.ReadService;
import com.education.project.cars.manager.carsmanager.IOServce.WriteService;

@Getter
@Setter
public class CarServiceProcessorImp implements CarService{
    private ReadService inService;
    private WriteService outService;
    private CarService carService;
    private String fileSource;
    private String fileDestination;

    @Setter(AccessLevel.NONE)
    private CarList cars;

    public CarServiceProcessorImp(ReadService inService,
                                  WriteService outService,
                                  CarService carService,
                                  String fileSource,
                                  String fileDestination) {
        this.inService = inService;
        this.outService = outService;
        this.carService = carService;
        this.fileSource = fileSource;
        this.fileDestination = fileDestination;
    }

    @Override
    public CarList getMaxCostCar(CarList list) {
        return carService.getMaxCostCar(list);
    }

    @Override
    public CarList getMinCostCar(CarList list) {
        return carService.getMinCostCar(list);
    }

    @Override
    public CarList findBrandList(String searchBrand, CarList list) {
        return carService.findBrandList(searchBrand, list);
    }

    @Override
    public CarList findModelList(String searchModel, CarList list) {
        return carService.findModelList(searchModel, list);
    }

    @Override
    public CarList getListByPriceRange(
            int startPrice, int endPrice, CarList list) {
        return carService.getListByPriceRange(startPrice, endPrice, list);
    }

    @Override
    public CarList sortListByPrice(CarList list) {
        return carService.sortListByPrice(list);
    }

    @Override
    public CarList sortListByBrand(CarList list) {
        return carService.sortListByBrand(list);
    }

    public void out(CarList list){
        outService.carListWriter(list, fileDestination);
    }
    public CarList in(){
        return inService.carListReader(fileSource);
    }


    public String toString(){
        return String.format(
                "inService: %s; outService: %s; carService: %s; " +
                        "fileSource: %s; fileDestination: %s.",
                inService.getClass().getSimpleName(),
                outService.getClass().getSimpleName(),
                carService.getClass().getSimpleName(),
                fileSource.getClass().getSimpleName(),
                fileDestination.getClass().getSimpleName()
                );
    }


}

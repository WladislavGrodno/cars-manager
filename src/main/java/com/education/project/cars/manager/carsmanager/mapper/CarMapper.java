package com.education.project.cars.manager.carsmanager.mapper;

import com.education.project.cars.manager.carsmanager.dto.request.CarRequest;
import com.education.project.cars.manager.carsmanager.dto.response.CarResponse;
import com.education.project.cars.manager.carsmanager.model.Car;
import org.mapstruct.Mapper;

import java.util.Collection;

@Mapper(componentModel = "spring")
public interface CarMapper {
    CarResponse toDto(Car car);

    Car toCar(Long idCar, CarRequest carRequest);
    //Car to

    Collection<CarResponse> toDtoList(Collection<Car> cars);
}

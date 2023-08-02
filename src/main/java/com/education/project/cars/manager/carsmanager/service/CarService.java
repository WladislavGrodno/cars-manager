package com.education.project.cars.manager.carsmanager.service;

import com.education.project.cars.manager.carsmanager.dto.request.CarRequest;
import com.education.project.cars.manager.carsmanager.dto.response.CarResponse;
import com.education.project.cars.manager.carsmanager.model.Car;
import com.education.project.cars.manager.carsmanager.model.CarPage;
import com.education.project.cars.manager.carsmanager.model.CarSearchCriteria;

import jakarta.validation.constraints.NotNull;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;

import java.util.Collection;
import java.util.List;

public interface CarService {
    ResponseEntity<Collection<CarResponse>> carListRead() throws Exception;
    List<Car> carListCustomRead(String sortBy, String filter);
    ResponseEntity<Page<Car>> carListAlienCustomRead(
            CarPage carPage,
            CarSearchCriteria carSearchCriteria
    );

    List<Car> carListWrite(List<Car> list);

    ResponseEntity<CarResponse> carRead(@NotNull Long idc) throws Exception;
    ResponseEntity<CarResponse> carWrite(@NotNull CarRequest car);
    ResponseEntity<CarResponse> carUpdate(
            @NotNull Long idc,
            @NotNull CarRequest car)
            throws Exception;
    void carErase(@NotNull Long idc) throws Exception;
}

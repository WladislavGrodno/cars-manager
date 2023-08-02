package com.education.project.cars.manager.carsmanager.dto.response;

public record CarResponse(
        Long idCar,
        Integer year,
        String brand,
        String model,
        Integer cost
) {}

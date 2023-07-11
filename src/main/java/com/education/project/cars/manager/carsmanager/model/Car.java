package com.education.project.cars.manager.carsmanager.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Car {

    private Long idCar = -1L;
    private Integer year = -1;
    private String brand = "empty";
    private String model = "empty";
    private Integer cost = -1;

    public Car(Integer year, String brand, String model, Integer cost) {
        this.year = year;
        this.brand = brand;
        this.model = model;
        this.cost = cost;
    }

    public String toString() {
        if (year == -1) return "";
        return String.format("{" +
                        "\"idCar\": %d, " +
                        "\"year\": %d, " +
                        "\"brand\": \"%s\", " +
                        "\"model\": \"%s\", " +
                        "\"cost\": %d}",
                idCar,
                year,
                brand,
                model,
                cost
        );
    }
}

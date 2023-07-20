package com.education.project.cars.manager.carsmanager.model;

import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Tag(name = "Car")
public class Car {

    @Schema(
            description = "Идентификатор автомобиля",
            example = "1525430455740003903"
    )
    private Long idCar = -1L;

    @Schema(
            description = "Год производства",
//            pattern = "^.{0,100}",
            example = "1127"
    )
    private Integer year = -1;

    @Schema(
            description = "Изготовитель",
            example = "Русско-Балтiйский Вагонный Заводъ въ Ригъ"
    )
    private String brand = "empty";

    @Schema(
            description = "Модель",
            example = "С24/40"
    )
    private String model = "empty";

    @Schema(
            description = "Цена",
//            pattern = "^.{0,100}",
            example = "1234567"
    )
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

package com.education.project.cars.manager.carsmanager.model;

import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Table;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Tag(name = "Car")
@Table(name = "garage")
public class Car {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Schema(
            description = "Идентификатор автомобиля",
            example = "1525430455740003903"
    )
    @Column(name = "idc", insertable = false)
    private Long idCar = -1L;

    @Schema(
            description = "Год производства",
//            pattern = "^.{0,100}",
            example = "1127"
    )
    @Column(name = "year", nullable = false)
    private Integer year = -1;

    @Schema(
            description = "Изготовитель",
            example = "Русско-Балтiйский Вагонный Заводъ въ Ригъ"
    )
    @Column(name = "brand", nullable = false)
    private String brand = "empty";

    @Schema(
            description = "Модель",
            example = "С24/40"
    )
    @Column(name = "model", nullable = false)
    private String model = "empty";

    @Schema(
            description = "Цена",
//            pattern = "^.{0,100}",
            example = "1234567"
    )
    @Column(name = "cost", nullable = false)
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

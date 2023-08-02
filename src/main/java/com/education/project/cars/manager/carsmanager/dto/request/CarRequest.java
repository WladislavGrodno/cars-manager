package com.education.project.cars.manager.carsmanager.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record CarRequest(
        @Schema(
                description = "Год производства",
//            pattern = "^.{0,100}",
                example = "1127"
        )
        @NotNull(message = "Null value Year is denied")
        Integer year,

        @Schema(
                description = "Изготовитель",
                example = "Русско-Балтiйский Вагонный Заводъ въ Ригъ"
        )
        @NotBlank(message = "Blank value Brand is denied")
        @Size(max = 60)
        String brand,

        @Schema(
                description = "Модель",
                example = "С24/40"
        )
        @NotBlank(message = "Blank value Model is denied")
        @Size(max = 60)
        String model,

        @Schema(
                description = "Цена",
//            pattern = "^.{0,100}",
                example = "1234567"
        )
        @NotNull(message = "Null value Cost is denied")
        Integer cost

) {
}

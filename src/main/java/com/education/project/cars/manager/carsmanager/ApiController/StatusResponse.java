package com.education.project.cars.manager.carsmanager.ApiController;

import java.util.HashMap;
import java.util.Map;

public record StatusResponse(
        String status,
        String message,
        Map<String, Object> details
) {
    public StatusResponse(String status, String message) {
        this(status, message, new HashMap<>());
    }
}

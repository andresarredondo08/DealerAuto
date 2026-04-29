package com.andres_arredondo.dealers_auto_center.inventory.vehicle.dto;

import com.andres_arredondo.dealers_auto_center.inventory.vehicle.domain.VehicleStatus;
import jakarta.validation.constraints.DecimalMin;

import java.math.BigDecimal;

public record UpdateVehicleRequest(
        String model,
        @DecimalMin("0")BigDecimal price,
        VehicleStatus status
        ) {
}

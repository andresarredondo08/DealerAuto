package com.andres_arredondo.dealers_auto_center.inventory.vehicle.dto;

import com.andres_arredondo.dealers_auto_center.inventory.vehicle.domain.VehicleStatus;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotNull;

import java.math.BigDecimal;
import java.util.UUID;

public record CreateVehicleRequest(
        @NotNull UUID dealerId,
        @NotNull String model,
        @NotNull @DecimalMin("0") BigDecimal price,
        @NotNull VehicleStatus status
        ) {
}

package com.andres_arredondo.dealers_auto_center.inventory.vehicle.dto;

import com.andres_arredondo.dealers_auto_center.inventory.dealer.domain.Dealer;
import com.andres_arredondo.dealers_auto_center.inventory.vehicle.domain.VehicleStatus;

import java.math.BigDecimal;
import java.util.UUID;

public record VehicleResponse(
       UUID id,
       String tenantId,
       UUID dealerId,
       String dealerName,
       String model,
       BigDecimal price,
       VehicleStatus status

) {
}

package com.andres_arredondo.dealers_auto_center.inventory.vehicle.mapper;

import com.andres_arredondo.dealers_auto_center.inventory.dealer.domain.Dealer;
import com.andres_arredondo.dealers_auto_center.inventory.vehicle.domain.Vehicle;
import com.andres_arredondo.dealers_auto_center.inventory.vehicle.dto.CreateVehicleRequest;
import com.andres_arredondo.dealers_auto_center.inventory.vehicle.dto.VehicleResponse;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface VehicleMapper {

    @Mapping(source = "dealer.id", target = "dealerId")
    @Mapping(source = "dealer.name", target = "dealerName")
    VehicleResponse toResponse(Vehicle vehicle);
}

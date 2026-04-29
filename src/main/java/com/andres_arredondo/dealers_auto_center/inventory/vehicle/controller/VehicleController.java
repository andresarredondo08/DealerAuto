package com.andres_arredondo.dealers_auto_center.inventory.vehicle.controller;

import com.andres_arredondo.dealers_auto_center.inventory.dealer.domain.SubscriptionType;
import com.andres_arredondo.dealers_auto_center.inventory.vehicle.domain.VehicleStatus;
import com.andres_arredondo.dealers_auto_center.inventory.vehicle.dto.CreateVehicleRequest;
import com.andres_arredondo.dealers_auto_center.inventory.vehicle.dto.UpdateVehicleRequest;
import com.andres_arredondo.dealers_auto_center.inventory.vehicle.dto.VehicleResponse;
import com.andres_arredondo.dealers_auto_center.inventory.vehicle.mapper.VehicleMapper;
import com.andres_arredondo.dealers_auto_center.inventory.vehicle.service.VehicleService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.UUID;

@RestController
@RequestMapping("/vehicles")
@RequiredArgsConstructor
public class VehicleController {

    private final VehicleService vehicleService;
    private final VehicleMapper vehicleMapper;

    @PostMapping
    public VehicleResponse create(@Valid @RequestBody CreateVehicleRequest request) {
        return vehicleMapper.toResponse(vehicleService.create(request));
    }

    @GetMapping("/{id}")
    public VehicleResponse getById(@PathVariable UUID id) {
        return vehicleMapper.toResponse(vehicleService.getById(id));
    }

    @GetMapping
    public Page<VehicleResponse> getAll(
            @RequestParam(required = false) String model,
            @RequestParam(required = false) VehicleStatus status,
            @RequestParam(required = false) BigDecimal priceMin,
            @RequestParam(required = false) BigDecimal priceMax,
            @RequestParam(required = false) SubscriptionType subscription,
            Pageable pageable
    ) {
        return vehicleService.getAll(
                        model,
                        status,
                        priceMin,
                        priceMax,
                        subscription,
                        pageable
                )
                .map(vehicleMapper::toResponse);
    }

    @PatchMapping("/{id}")
    public VehicleResponse update(
            @PathVariable UUID id,
            @Valid @RequestBody UpdateVehicleRequest request
    ) {
        return vehicleMapper.toResponse(vehicleService.update(id, request));
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable UUID id) {
        vehicleService.delete(id);
    }
}

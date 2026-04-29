package com.andres_arredondo.dealers_auto_center.inventory.vehicle.service;

import com.andres_arredondo.dealers_auto_center.inventory.dealer.domain.Dealer;
import com.andres_arredondo.dealers_auto_center.inventory.dealer.domain.SubscriptionType;
import com.andres_arredondo.dealers_auto_center.inventory.dealer.repository.DealerRepository;
import com.andres_arredondo.dealers_auto_center.inventory.security.TenantContext;
import com.andres_arredondo.dealers_auto_center.inventory.shared.exception.ResourceNotFoundException;
import com.andres_arredondo.dealers_auto_center.inventory.shared.exception.TenantAccessDeniedException;
import com.andres_arredondo.dealers_auto_center.inventory.vehicle.domain.Vehicle;
import com.andres_arredondo.dealers_auto_center.inventory.vehicle.domain.VehicleStatus;
import com.andres_arredondo.dealers_auto_center.inventory.vehicle.dto.CreateVehicleRequest;
import com.andres_arredondo.dealers_auto_center.inventory.vehicle.dto.UpdateVehicleRequest;
import com.andres_arredondo.dealers_auto_center.inventory.vehicle.dto.VehicleResponse;
import com.andres_arredondo.dealers_auto_center.inventory.vehicle.repository.VehicleRepository;
import com.andres_arredondo.dealers_auto_center.inventory.vehicle.specification.VehicleSpecification;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.UUID;

@Service
@AllArgsConstructor
public class VehicleService {

    private final VehicleRepository vehicleRepository;
    private final DealerRepository dealerRepository;

    public Vehicle create(CreateVehicleRequest request) {
        String tenantId = TenantContext.getTenantId();

        Dealer dealer = dealerRepository.findById(request.dealerId())
                .orElseThrow(() -> new ResourceNotFoundException("Dealer not found"));

        if (!dealer.getTenantId().equals(tenantId)) {
            throw new TenantAccessDeniedException("Dealer belongs to another tenant");
        }

        Vehicle vehicle = Vehicle.builder()
                .tenantId(tenantId)
                .dealer(dealer)
                .model(request.model())
                .price(request.price())
                .status(request.status())
                .build();
        return vehicleRepository.save(vehicle);

    }

    public Vehicle getById(UUID id) {
        return vehicleRepository.findByIdAndTenantId(id, TenantContext.getTenantId())
                .orElseThrow(() -> new ResourceNotFoundException("Vehicle not found"));
    }

    public Page<Vehicle> getAll(
            String model,
            VehicleStatus status,
            BigDecimal priceMin,
            BigDecimal priceMax,
            SubscriptionType subscription,
            Pageable pageable
    ) {
        String tenantId = TenantContext.getTenantId();

        Specification<Vehicle> specification = Specification.allOf(
                VehicleSpecification.tenantIs(tenantId),
                VehicleSpecification.modelContains(model),
                VehicleSpecification.statusIs(status),
                VehicleSpecification.priceGreaterThanOrEqual(priceMin),
                VehicleSpecification.priceLessThanOrEqual(priceMax),
                VehicleSpecification.dealerSubscriptionIs(subscription)
        );

        return vehicleRepository.findAll(specification, pageable);
    }

    public Vehicle update(UUID id, UpdateVehicleRequest request) {
        Vehicle vehicle = getById(id);

        if (request.model() != null) {
            vehicle.setModel(request.model());
        }

        if (request.price() != null) {
            vehicle.setPrice(request.price());
        }

        if (request.status() != null) {
            vehicle.setStatus(request.status());
        }

        return vehicleRepository.save(vehicle);
    }

    public void delete(UUID id) {
        Vehicle vehicle = getById(id);
        vehicleRepository.delete(vehicle);
    }
}

package com.andres_arredondo.dealers_auto_center.inventory.vehicle.specification;

import com.andres_arredondo.dealers_auto_center.inventory.dealer.domain.SubscriptionType;
import com.andres_arredondo.dealers_auto_center.inventory.vehicle.domain.Vehicle;
import com.andres_arredondo.dealers_auto_center.inventory.vehicle.domain.VehicleStatus;
import org.springframework.data.jpa.domain.Specification;

import java.math.BigDecimal;

public class VehicleSpecification {

    public static Specification<Vehicle> tenantIs(String tenantId){
        return ((root, query, cb) -> cb.equal(root.get("tenantId"), tenantId));
    }
    public static Specification<Vehicle> modelContains(String model){
        return ((root, query, cb) -> {
            if (model == null || model.isBlank()){
                return null;
            }
            return cb.like(
                    cb.lower(root.get("model")),
                    "%" + model.toLowerCase() + "%"
            );
        });
    }

    public static Specification<Vehicle> statusIs(VehicleStatus status) {
        return (root, query, cb) -> {
            if (status == null) {
                return null;
            }

            return cb.equal(root.get("status"), status);
        };
    }

    public static Specification<Vehicle> priceGreaterThanOrEqual(BigDecimal priceMin) {
        return (root, query, cb) -> {
            if (priceMin == null) {
                return null;
            }

            return cb.greaterThanOrEqualTo(root.get("price"), priceMin);
        };
    }

    public static Specification<Vehicle> priceLessThanOrEqual(BigDecimal priceMax) {
        return (root, query, cb) -> {
            if (priceMax == null) {
                return null;
            }

            return cb.lessThanOrEqualTo(root.get("price"), priceMax);
        };
    }

    public static Specification<Vehicle> dealerSubscriptionIs(SubscriptionType subscriptionType) {
        return (root, query, cb) -> {
            if (subscriptionType == null) {
                return null;
            }

            var dealerJoin = root.join("dealer");

            return cb.equal(
                    dealerJoin.get("subscriptionType"),
                    subscriptionType
            );
        };
    }
}

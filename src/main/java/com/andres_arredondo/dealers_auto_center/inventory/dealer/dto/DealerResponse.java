package com.andres_arredondo.dealers_auto_center.inventory.dealer.dto;

import com.andres_arredondo.dealers_auto_center.inventory.dealer.domain.SubscriptionType;

import java.util.UUID;

public record DealerResponse(
        UUID id,
        String tenantId,
        String name,
        String email,
        SubscriptionType subscriptionType
) {
}

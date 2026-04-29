package com.andres_arredondo.dealers_auto_center.inventory.dealer.dto;

import com.andres_arredondo.dealers_auto_center.inventory.dealer.domain.SubscriptionType;
import jakarta.validation.constraints.Email;

public record UpdateDealerRequest(
        String name,
        @Email String email,
        SubscriptionType subscriptionType
) {
}

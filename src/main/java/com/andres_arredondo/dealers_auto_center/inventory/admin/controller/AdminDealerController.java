package com.andres_arredondo.dealers_auto_center.inventory.admin.controller;

import com.andres_arredondo.dealers_auto_center.inventory.admin.application.AdminDealerService;
import com.andres_arredondo.dealers_auto_center.inventory.dealer.domain.SubscriptionType;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping("/admin/dealers")
@RequiredArgsConstructor
public class AdminDealerController {

    private final AdminDealerService adminDealerService;

    @GetMapping("/countBySubscription")
    @PreAuthorize("hasRole('GLOBAL_ADMIN')")
    public Map<SubscriptionType, Long> countBySubscription() {
        return adminDealerService.countBySubscription();
    }
}

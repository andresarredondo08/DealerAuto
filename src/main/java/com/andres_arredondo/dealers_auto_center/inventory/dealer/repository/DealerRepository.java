package com.andres_arredondo.dealers_auto_center.inventory.dealer.repository;

import com.andres_arredondo.dealers_auto_center.inventory.dealer.domain.Dealer;
import com.andres_arredondo.dealers_auto_center.inventory.dealer.domain.SubscriptionType;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface DealerRepository extends JpaRepository<Dealer, UUID> {

    Optional<Dealer> findByIdAndTenantId(UUID id, String tenantId);

    Page<Dealer> findAllByTenantId(String tenantId, Pageable pageable);

    @Query("""
        SELECT dealer.subscriptionType, COUNT(dealer)
        FROM Dealer dealer
        GROUP BY dealer.subscriptionType
    """)
    List<Object[]> countBySubscriptionOverall();
}

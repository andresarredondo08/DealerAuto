package com.andres_arredondo.dealers_auto_center.inventory.admin.application;

import com.andres_arredondo.dealers_auto_center.inventory.dealer.domain.SubscriptionType;
import com.andres_arredondo.dealers_auto_center.inventory.dealer.repository.DealerRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.EnumMap;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class AdminDealerService {
    private final DealerRepository dealerRepository;

    public Map<SubscriptionType, Long> countBySubscription() {
        Map<SubscriptionType, Long> result = new EnumMap<>(SubscriptionType.class);

        for(SubscriptionType type : SubscriptionType.values()){
            result.put(type,0L);
        }

        dealerRepository.countBySubscriptionOverall()
                .forEach(r -> result.put(
                        (SubscriptionType)  r[0],
                        (Long) r[1]
                ));
        return result;
    }
}

package com.andres_arredondo.dealers_auto_center.inventory.dealer.service;

import com.andres_arredondo.dealers_auto_center.inventory.dealer.domain.Dealer;
import com.andres_arredondo.dealers_auto_center.inventory.dealer.dto.CreateDealerRequest;
import com.andres_arredondo.dealers_auto_center.inventory.dealer.dto.UpdateDealerRequest;
import com.andres_arredondo.dealers_auto_center.inventory.dealer.repository.DealerRepository;
import com.andres_arredondo.dealers_auto_center.inventory.security.TenantContext;
import com.andres_arredondo.dealers_auto_center.inventory.shared.exception.ResourceNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class DealerService {

    private final DealerRepository dealerRepository;

    public Dealer create(CreateDealerRequest request){
        String tenantId = TenantContext.getTenantId();

        Dealer dealer = Dealer.builder()
                .tenantId(tenantId)
                .email(request.email())
                .name(request.name())
                .subscriptionType(request.subscriptionType())
                .build();
        return dealerRepository.save(dealer);
    }

    public Dealer getById(UUID id){
        System.out.println("Este es el tenantContext " + TenantContext.getTenantId());
        return dealerRepository.findByIdAndTenantId(id, TenantContext.getTenantId())
                .orElseThrow(() -> new ResourceNotFoundException("Dealer not found"));
    }

    public Page<Dealer> getAll(Pageable pageable){
        return dealerRepository.findAllByTenantId(TenantContext.getTenantId(),pageable);
    }

    public Dealer update(UUID id,UpdateDealerRequest request){
        Dealer dealer = getById(id);

        if(request.email() != null){
            dealer.setEmail(request.email());
        }
        if(request.name() != null){
            dealer.setName(request.name());
        }
        if(request.subscriptionType() != null){
            dealer.setSubscriptionType(request.subscriptionType());
        }

        return dealerRepository.save(dealer);
    }

    public void delete(UUID id){
        Dealer dealer = getById(id);
        dealerRepository.delete(dealer);
    }
}

package com.andres_arredondo.dealers_auto_center.inventory.dealer.mapper;

import com.andres_arredondo.dealers_auto_center.inventory.dealer.domain.Dealer;
import com.andres_arredondo.dealers_auto_center.inventory.dealer.dto.CreateDealerRequest;
import com.andres_arredondo.dealers_auto_center.inventory.dealer.dto.DealerResponse;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface DealerMapper {

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "tenantId", ignore = true)
    Dealer toEntity(CreateDealerRequest request);

    DealerResponse toResponse(Dealer dealer);
}
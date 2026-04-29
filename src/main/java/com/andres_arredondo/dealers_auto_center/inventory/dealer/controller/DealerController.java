package com.andres_arredondo.dealers_auto_center.inventory.dealer.controller;

import com.andres_arredondo.dealers_auto_center.inventory.dealer.dto.CreateDealerRequest;
import com.andres_arredondo.dealers_auto_center.inventory.dealer.dto.DealerResponse;
import com.andres_arredondo.dealers_auto_center.inventory.dealer.dto.UpdateDealerRequest;
import com.andres_arredondo.dealers_auto_center.inventory.dealer.mapper.DealerMapper;
import com.andres_arredondo.dealers_auto_center.inventory.dealer.service.DealerService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/dealers")
@RequiredArgsConstructor
public class DealerController {

    private final DealerService dealerService;
    private final DealerMapper dealerMapper;

    @PostMapping
    public DealerResponse create(@Valid @RequestBody CreateDealerRequest request){
        return dealerMapper.toResponse(dealerService.create(request));
    }

    @GetMapping("/{id}")
    public DealerResponse getById(@PathVariable UUID id){
        return dealerMapper.toResponse(dealerService.getById(id));
    }

    @GetMapping
    public Page<DealerResponse> getAll(Pageable pageable){
        return dealerService.getAll(pageable).map(dealerMapper::toResponse);
    }

    @PatchMapping("/{id}")
    public DealerResponse update(
            @PathVariable UUID id,
            @Valid@RequestBody UpdateDealerRequest request
            ){
        return dealerMapper.toResponse(dealerService.update(id, request));
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable UUID id){
        dealerService.delete(id);
    }
}

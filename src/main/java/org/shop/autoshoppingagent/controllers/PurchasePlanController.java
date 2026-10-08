package org.shop.autoshoppingagent.controllers;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.shop.autoshoppingagent.request.ApprovePurchasePlanRequest;
import org.shop.autoshoppingagent.request.CreatePurchasePlanRequest;
import org.shop.autoshoppingagent.response.ApprovalResponse;
import org.shop.autoshoppingagent.response.PurchasePlanResponse;
import org.shop.autoshoppingagent.service.PurchasePlanService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/api/purchase-plans")
@RequiredArgsConstructor
public class PurchasePlanController {

    private final PurchasePlanService purchasePlanService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public PurchasePlanResponse createPlan(
            @Valid @RequestBody CreatePurchasePlanRequest request) {

        return purchasePlanService.createPlan(request);
    }

    @PostMapping("/{planId}/approve")
    public ApprovalResponse approvePlan(
            @PathVariable UUID planId,
            @Valid @RequestBody ApprovePurchasePlanRequest request) {

        return purchasePlanService.approvePlan(
                planId,
                request
        );
    }
}

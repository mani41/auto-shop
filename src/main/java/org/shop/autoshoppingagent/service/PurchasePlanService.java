package org.shop.autoshoppingagent.service;

import lombok.RequiredArgsConstructor;
import org.shop.autoshoppingagent.entity.Product;
import org.shop.autoshoppingagent.entity.PurchasePlan;
import org.shop.autoshoppingagent.entity.PurchasePlanItem;
import org.shop.autoshoppingagent.entity.ShoppingSession;
import org.shop.autoshoppingagent.enums.PurchasePlanStatus;
import org.shop.autoshoppingagent.repository.ProductRepository;
import org.shop.autoshoppingagent.repository.PurchasePlanItemRepository;
import org.shop.autoshoppingagent.repository.PurchasePlanRepository;
import org.shop.autoshoppingagent.repository.ShoppingSessionRepository;
import org.shop.autoshoppingagent.request.ApprovePurchasePlanRequest;
import org.shop.autoshoppingagent.request.CreatePurchasePlanRequest;
import org.shop.autoshoppingagent.response.ApprovalResponse;
import org.shop.autoshoppingagent.response.PurchasePlanResponse;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class PurchasePlanService {

    private final PurchasePlanRepository purchasePlanRepository;
    private final PurchasePlanItemRepository purchasePlanItemRepository;
    private final ProductRepository productRepository;
    private final ShoppingSessionRepository shoppingSessionRepository;
    private final PlanHashService planHashService;

    @Transactional
    public PurchasePlanResponse createPlan(
            CreatePurchasePlanRequest request) {

        ShoppingSession session =
                shoppingSessionRepository.findById(
                        request.shoppingSessionId()
                ).orElseThrow(() ->
                        new IllegalArgumentException(
                                "Shopping session not found"
                        ));

        Product product =
                productRepository.findById(request.productId())
                        .orElseThrow(() ->
                                new IllegalArgumentException(
                                        "Product not found"
                                ));

        if (!product.isStockAvailable()) {
            throw new IllegalStateException(
                    "Product is no longer in stock"
            );
        }

        int quantity = request.quantity();

        BigDecimal subtotal =
                product.getPrice()
                        .multiply(BigDecimal.valueOf(quantity));

        BigDecimal shippingCost =
                product.getShippingCost();

        BigDecimal totalAmount =
                subtotal.add(shippingCost);

        PurchasePlan plan = new PurchasePlan();

        plan.setId(UUID.randomUUID());
        plan.setShoppingSession(session);
        plan.setStatus(PurchasePlanStatus.READY_FOR_APPROVAL);
        plan.setCurrency(product.getCurrency());
        plan.setSubtotal(subtotal);
        plan.setShippingCost(shippingCost);
        plan.setTotalAmount(totalAmount);

        OffsetDateTime now = OffsetDateTime.now();

        plan.setCreatedAt(now);
        plan.setUpdatedAt(now);

        /*
         * Hash is calculated from authoritative values.
         */
        String canonicalData =
                buildCanonicalData(
                        session,
                        product,
                        quantity,
                        subtotal,
                        shippingCost,
                        totalAmount
                );

        plan.setPlanHash(
                planHashService.calculateHash(canonicalData)
        );

        purchasePlanRepository.save(plan);

        PurchasePlanItem item = new PurchasePlanItem();

        item.setId(UUID.randomUUID());
        item.setPurchasePlan(plan);
        item.setMerchant(product.getMerchant());
        item.setProduct(product);

        item.setExternalProductId(
                product.getExternalId()
        );

        item.setProductName(
                product.getName()
        );

        item.setMerchantName(
                product.getMerchant().getName()
        );

        item.setQuantity(quantity);
        item.setUnitPrice(product.getPrice());
        item.setShippingCost(shippingCost);
        item.setLineTotal(subtotal);
        item.setCurrency(product.getCurrency());
        item.setCreatedAt(now);

        purchasePlanItemRepository.save(item);

        return new PurchasePlanResponse(
                plan.getId(),
                session.getId(),
                plan.getStatus(),
                product.getId(),
                product.getName(),
                product.getMerchant().getName(),
                quantity,
                product.getPrice(),
                shippingCost,
                subtotal,
                totalAmount,
                product.getCurrency(),
                plan.getPlanHash(),
                plan.getExpiresAt(),
                plan.getCreatedAt()
        );
    }

    private String buildCanonicalData(
            ShoppingSession session,
            Product product,
            int quantity,
            BigDecimal subtotal,
            BigDecimal shippingCost,
            BigDecimal totalAmount) {

        return String.join("|",
                session.getId().toString(),
                product.getId().toString(),
                product.getMerchant().getId().toString(),
                product.getExternalId(),
                String.valueOf(quantity),
                product.getPrice().toPlainString(),
                shippingCost.toPlainString(),
                subtotal.toPlainString(),
                totalAmount.toPlainString(),
                product.getCurrency()
        );
    }

    @Transactional
    public ApprovalResponse approvePlan(
            UUID planId,
            ApprovePurchasePlanRequest request) {

        PurchasePlan plan =
                purchasePlanRepository.findById(planId)
                        .orElseThrow(() ->
                                new IllegalArgumentException(
                                        "Purchase plan not found"
                                ));

        if (plan.getStatus() != PurchasePlanStatus.READY_FOR_APPROVAL) {
            throw new IllegalStateException(
                    "Purchase plan cannot be approved in status: "
                            + plan.getStatus()
            );
        }

        String currentHash = calculateCurrentPlanHash(plan);

        if (!currentHash.equals(request.planHash())) {
            throw new IllegalStateException(
                    "Purchase plan has changed. Re-approval is required."
            );
        }

        plan.setStatus(PurchasePlanStatus.APPROVED);
        plan.setApprovedAt(OffsetDateTime.now());
        plan.setUpdatedAt(OffsetDateTime.now());

        purchasePlanRepository.save(plan);

        return new ApprovalResponse(
                plan.getId(),
                plan.getStatus(),
                plan.getTotalAmount(),
                plan.getCurrency(),
                plan.getPlanHash(),
                plan.getApprovedAt()
        );
    }

    private String calculateCurrentPlanHash(
            PurchasePlan plan) {

        PurchasePlanItem item =
                purchasePlanItemRepository
                        .findByPurchasePlan_Id(plan.getId())
                        .stream()
                        .findFirst()
                        .orElseThrow(() ->
                                new IllegalStateException(
                                        "Purchase plan item not found"
                                ));

        String canonicalData =
                String.join("|",
                        plan.getShoppingSession()
                                .getId()
                                .toString(),

                        item.getProduct()
                                .getId()
                                .toString(),

                        item.getMerchant()
                                .getId()
                                .toString(),

                        item.getExternalProductId(),

                        String.valueOf(item.getQuantity()),

                        item.getUnitPrice()
                                .toPlainString(),

                        item.getShippingCost()
                                .toPlainString(),

                        plan.getSubtotal()
                                .toPlainString(),

                        plan.getTotalAmount()
                                .toPlainString(),

                        plan.getCurrency()
                );

        return planHashService.calculateHash(canonicalData);
    }
}

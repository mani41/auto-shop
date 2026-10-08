package org.shop.autoshoppingagent.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.shop.autoshoppingagent.adapters.MerchantProductAdapter;
import org.shop.autoshoppingagent.dtos.ProductSearchCriteria;
import org.shop.autoshoppingagent.dtos.ProductSearchResult;
import org.shop.autoshoppingagent.entity.Merchant;
import org.shop.autoshoppingagent.entity.Product;
import org.shop.autoshoppingagent.repository.MerchantRepository;
import org.shop.autoshoppingagent.repository.ProductRepository;
import org.springframework.stereotype.Service;

import java.time.OffsetDateTime;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Slf4j
public class ProductSearchService {

    private final ProductRepository productRepository;
    private final MerchantRepository merchantRepository;
    private final List<MerchantProductAdapter> adapters;

    public List<ProductSearchResult> search(
            ProductSearchCriteria criteria) {

        List<ProductSearchResult> results =
                new ArrayList<>();

        log.info(
                "REGISTERED_MERCHANT_ADAPTERS count={} adapters={}",
                adapters.size(),
                adapters.stream()
                        .map(MerchantProductAdapter::merchantCode)
                        .toList()
        );

        for (MerchantProductAdapter adapter : adapters) {

            log.info(
                    "MERCHANT_SEARCH_STARTED merchant={}",
                    adapter.merchantCode()
            );

            List<ProductSearchResult> merchantResults =
                    adapter.search(criteria);

            log.info(
                    "MERCHANT_SEARCH_COMPLETED merchant={} resultCount={} externalIds={}",
                    adapter.merchantCode(),
                    merchantResults.size(),
                    merchantResults.stream()
                            .map(ProductSearchResult::externalId)
                            .toList()
            );

            for (ProductSearchResult result : merchantResults) {

                saveProduct(result);

                //results.add(result);
            }

            results.addAll(merchantResults);
        }

        return results.stream()
                .filter(ProductSearchResult::stockAvailable)
                .filter(product ->
                        criteria.maxPrice() == null
                                || product.price()
                                .add(product.shippingCost())
                                .compareTo(criteria.maxPrice()) <= 0)
                .filter(product ->
                        criteria.maxDeliveryDays() == null
                                || product.deliveryDays() == null
                                || product.deliveryDays()
                                <= criteria.maxDeliveryDays())
                .sorted(
                        Comparator.comparing(
                                ProductSearchResult::price
                        )
                )
                .toList();
    }

    private void saveProduct(ProductSearchResult result) {

        Merchant merchant =
                merchantRepository
                        .findByCode(result.merchantCode())
                        .orElseGet(() -> createMerchant(result));

        Product product =
                productRepository
                        .findByMerchantIdAndExternalId(
                                merchant.getId(),
                                result.externalId()
                        )
                        .orElseGet(Product::new);

        OffsetDateTime now = OffsetDateTime.now();

        if (product.getId() == null) {
            product.setId(UUID.randomUUID());
            product.setCreatedAt(now);
        }

        product.setMerchant(merchant);
        product.setExternalId(result.externalId());
        product.setName(result.name());
        product.setBrand(result.brand());
        product.setCategory(result.category());
        product.setPrice(result.price());
        product.setCurrency(result.currency());
        product.setShippingCost(result.shippingCost());
        product.setDeliveryDays(result.deliveryDays());
        product.setRating(result.rating());
        product.setStockAvailable(result.stockAvailable());
        product.setProductUrl(result.productUrl());
        product.setUpdatedAt(now);

        log.debug(
                "PRODUCT_SAVED merchant={} externalId={} price={} shipping={}",
                result.merchantCode(),
                result.externalId(),
                result.price(),
                result.shippingCost()
        );

        productRepository.save(product);
    }

    private Merchant createMerchant(
            ProductSearchResult result) {

        Merchant merchant = new Merchant();

        merchant.setId(UUID.randomUUID());
        merchant.setName(result.merchantName());
        merchant.setCode(result.merchantCode());
        merchant.setActive(true);
        merchant.setCreatedAt(OffsetDateTime.now());

        return merchantRepository.save(merchant);
    }
}

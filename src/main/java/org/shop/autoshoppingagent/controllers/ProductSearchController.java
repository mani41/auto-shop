package org.shop.autoshoppingagent.controllers;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.shop.autoshoppingagent.dtos.ProductSearchCriteria;
import org.shop.autoshoppingagent.dtos.ProductSearchResult;
import org.shop.autoshoppingagent.service.ProductSearchService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/products")
@RequiredArgsConstructor
@Slf4j
public class ProductSearchController {

    private final ProductSearchService productSearchService;

    @PostMapping("/search")
    public List<ProductSearchResult> search(
            @RequestBody ProductSearchCriteria criteria) {

        log.info(
                "PRODUCT_SEARCH_STARTED keyword={} category={} maxPrice={} maxDeliveryDays={}",
                criteria.keyword(),
                criteria.category(),
                criteria.maxPrice(),
                criteria.maxDeliveryDays()
        );

        List<ProductSearchResult> results =
                productSearchService.search(criteria);

        log.info(
                "PRODUCT_SEARCH_COMPLETED resultCount={}",
                results.size()
        );

        return results;
    }
}

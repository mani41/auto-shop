package org.shop.autoshoppingagent.adapters;

import lombok.extern.slf4j.Slf4j;
import org.shop.autoshoppingagent.dtos.ProductSearchCriteria;
import org.shop.autoshoppingagent.dtos.ProductSearchResult;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.List;

@Component
@Slf4j
public class MockCromaProductAdapter
        implements MerchantProductAdapter {

    @Override
    public String merchantCode() {
        return "CROMA";
    }

    @Override
    public List<ProductSearchResult> search(
            ProductSearchCriteria criteria) {

        log.info(
                "CROMA_SEARCH keyword={} maxPrice={}",
                criteria.keyword(),
                criteria.maxPrice()
        );

        List<ProductSearchResult> results = List.of(

                new ProductSearchResult(
                        "CR-TV-001",
                        "CROMA",
                        "Croma",
                        "Sony 55 inch 4K TV",
                        "Sony",
                        "TV",
                        new BigDecimal("59999"),
                        "INR",
                        new BigDecimal("0"),
                        2,
                        new BigDecimal("4.4"),
                        true,
                        "https://example.com/croma/CR-TV-001"
                ),

                new ProductSearchResult(
                        "CR-TV-002",
                        "CROMA",
                        "Croma",
                        "Samsung 55 Inch 4K Smart TV",
                        "Samsung",
                        "TV",
                        new BigDecimal("53999"),
                        "INR",
                        new BigDecimal("0"),
                        2,
                        new BigDecimal("4.5"),
                        true,
                        "https://example.com/croma/CR-TV-002"
                )
        );

        log.info(
                "CROMA_SEARCH_COMPLETED resultCount={}",
                results.size()
        );

        return results;
    }
}

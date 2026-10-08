package org.shop.autoshoppingagent.adapters;

import lombok.extern.slf4j.Slf4j;
import org.shop.autoshoppingagent.dtos.ProductSearchCriteria;
import org.shop.autoshoppingagent.dtos.ProductSearchResult;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.List;

@Component
@Slf4j
public class MockFlipkartProductAdapter
        implements MerchantProductAdapter {

    @Override
    public String merchantCode() {
        return "FLIPKART";
    }

    @Override
    public List<ProductSearchResult> search(
            ProductSearchCriteria criteria) {

        log.info(
                "FLIPKART_SEARCH keyword={} maxPrice={}",
                criteria.keyword(),
                criteria.maxPrice()
        );

        List<ProductSearchResult> results = List.of(

                new ProductSearchResult(
                        "FK-TV-001",
                        "FLIPKART",
                        "Flipkart",
                        "Samsung 55 Inch 4K Ultra HD Smart TV",
                        "Samsung",
                        "TV",
                        new BigDecimal("51999"),
                        "INR",
                        new BigDecimal("499"),
                        3,
                        new BigDecimal("4.3"),
                        true,
                        "https://example.com/flipkart/FK-TV-001"
                ),

                new ProductSearchResult(
                        "FK-TV-002",
                        "FLIPKART",
                        "Flipkart",
                        "Sony Bravia 55 Inch 4K Smart TV",
                        "Sony",
                        "TV",
                        new BigDecimal("57499"),
                        "INR",
                        new BigDecimal("299"),
                        5,
                        new BigDecimal("4.6"),
                        true,
                        "https://example.com/flipkart/FK-TV-002"
                )
        );

        log.info(
                "FLIPKART_SEARCH_COMPLETED resultCount={}",
                results.size()
        );

        return results;
    }
}

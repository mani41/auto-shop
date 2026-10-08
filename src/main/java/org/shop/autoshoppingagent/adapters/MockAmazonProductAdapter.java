package org.shop.autoshoppingagent.adapters;

import lombok.extern.slf4j.Slf4j;
import org.shop.autoshoppingagent.dtos.ProductSearchCriteria;
import org.shop.autoshoppingagent.dtos.ProductSearchResult;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.List;

@Component
@Slf4j
public class MockAmazonProductAdapter
        implements MerchantProductAdapter {

    @Override
    public String merchantCode() {
        return "AMAZON";
    }

    @Override
    public List<ProductSearchResult> search(
            ProductSearchCriteria criteria) {

        log.info(
                "AMAZON_SEARCH keyword={} maxPrice={}",
                criteria.keyword(),
                criteria.maxPrice()
        );

        List<ProductSearchResult> results = List.of(

                new ProductSearchResult(
                        "AMZ-TV-001",
                        "AMAZON",
                        "Amazon",
                        "Sony Bravia 55 Inch 4K Ultra HD Smart LED TV",
                        "Sony",
                        "TV",
                        new BigDecimal("58499"),
                        "INR",
                        new BigDecimal("0"),
                        3,
                        new BigDecimal("4.5"),
                        true,
                        "https://example.com/amazon/AMZ-TV-001"
                ),

                new ProductSearchResult(
                        "AMZ-TV-002",
                        "AMAZON",
                        "Amazon",
                        "Samsung 55 Inch 4K Smart TV",
                        "Samsung",
                        "TV",
                        new BigDecimal("52999"),
                        "INR",
                        new BigDecimal("0"),
                        4,
                        new BigDecimal("4.4"),
                        true,
                        "https://example.com/amazon/AMZ-TV-002"
                )
        );

        log.info(
                "AMAZON_SEARCH_COMPLETED resultCount={}",
                results.size()
        );

        return results;
    }
}

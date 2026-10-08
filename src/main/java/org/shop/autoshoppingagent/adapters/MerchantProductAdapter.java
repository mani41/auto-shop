package org.shop.autoshoppingagent.adapters;

import org.shop.autoshoppingagent.dtos.ProductSearchCriteria;
import org.shop.autoshoppingagent.dtos.ProductSearchResult;

import java.util.List;

public interface MerchantProductAdapter {

    String merchantCode();

    List<ProductSearchResult> search(
            ProductSearchCriteria criteria
    );
}

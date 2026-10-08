package org.shop.autoshoppingagent.repository;

import org.shop.autoshoppingagent.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface ProductRepository
        extends JpaRepository<Product, UUID> {

    List<Product> findByStockAvailableTrue();

    List<Product> findByBrandIgnoreCase(String brand);

    List<Product> findByCategoryIgnoreCase(String category);

    List<Product> findByPriceLessThanEqual(BigDecimal price);

    Optional<Product> findByMerchantIdAndExternalId(
            UUID merchantId,
            String externalId
    );
}

package org.shop.autoshoppingagent.repository;

import org.shop.autoshoppingagent.entity.Merchant;
import org.shop.autoshoppingagent.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface MerchantRepository
        extends JpaRepository<Merchant, UUID> {

    Optional<Merchant> findByCode(String code);
}

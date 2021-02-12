package com.store.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.store.model.Purchase;

@Repository
public interface PurchaseRepository extends CrudRepository<Purchase, Long> {
}

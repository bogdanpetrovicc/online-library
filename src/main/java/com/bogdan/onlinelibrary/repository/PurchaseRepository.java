package com.bogdan.onlinelibrary.repository;

import com.bogdan.onlinelibrary.entity.Purchase;
import com.bogdan.onlinelibrary.repository.generic.GenericRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PurchaseRepository extends GenericRepository<Purchase> {
}

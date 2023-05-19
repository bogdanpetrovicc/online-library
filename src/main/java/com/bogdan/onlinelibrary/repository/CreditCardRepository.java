package com.bogdan.onlinelibrary.repository;

import com.bogdan.onlinelibrary.entity.CreditCard;
import com.bogdan.onlinelibrary.repository.generic.GenericRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CreditCardRepository extends GenericRepository<CreditCard> {
}

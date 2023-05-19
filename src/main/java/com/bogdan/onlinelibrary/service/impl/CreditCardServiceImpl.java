package com.bogdan.onlinelibrary.service.impl;

import com.bogdan.onlinelibrary.entity.CreditCard;
import com.bogdan.onlinelibrary.repository.generic.GenericRepository;
import com.bogdan.onlinelibrary.service.CreditCardService;
import com.bogdan.onlinelibrary.service.generic.impl.GenericServiceImpl;
import org.springframework.stereotype.Service;

@Service
public class CreditCardServiceImpl extends GenericServiceImpl<CreditCard> implements CreditCardService {

    protected CreditCardServiceImpl(GenericRepository<CreditCard> genericRepository) {
        super(genericRepository);
    }
}

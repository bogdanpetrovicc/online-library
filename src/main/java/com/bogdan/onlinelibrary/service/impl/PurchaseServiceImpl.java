package com.bogdan.onlinelibrary.service.impl;

import com.bogdan.onlinelibrary.entity.Purchase;
import com.bogdan.onlinelibrary.repository.PurchaseRepository;
import com.bogdan.onlinelibrary.service.PurchaseService;
import com.bogdan.onlinelibrary.service.generic.impl.GenericServiceImpl;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PurchaseServiceImpl extends GenericServiceImpl<Purchase> implements PurchaseService {

    protected PurchaseServiceImpl(PurchaseRepository purchaseRepository) {
        super(purchaseRepository);
    }

    @Override
    public List<Purchase> findAllByMemberId(Integer memberId) {
        return ((PurchaseRepository) genericRepository).findAllByMemberId(memberId);
    }
}

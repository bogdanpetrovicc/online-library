package com.bogdan.onlinelibrary.service;

import com.bogdan.onlinelibrary.entity.Purchase;
import com.bogdan.onlinelibrary.service.generic.GenericService;

import java.util.List;

public interface PurchaseService extends GenericService<Purchase> {
    List<Purchase> findAllByMemberId(Integer memberId);

    void savePurchase(Integer userId, Integer bookId);
}

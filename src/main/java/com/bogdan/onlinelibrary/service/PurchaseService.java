package com.bogdan.onlinelibrary.service;

import com.bogdan.onlinelibrary.entity.Purchase;
import com.bogdan.onlinelibrary.service.generic.GenericService;

import java.util.List;

public interface PurchaseService extends GenericService<Purchase> {

    void savePurchase(Integer userId, Integer bookId);

    List<Purchase> findAllByLoggedInMember();
}

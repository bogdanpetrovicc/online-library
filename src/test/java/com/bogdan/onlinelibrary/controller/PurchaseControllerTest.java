package com.bogdan.onlinelibrary.controller;

import com.bogdan.onlinelibrary.entity.Purchase;
import com.bogdan.onlinelibrary.exception.NotEnoughMoneyException;
import com.bogdan.onlinelibrary.service.MemberService;
import com.bogdan.onlinelibrary.service.PurchaseService;
import com.bogdan.onlinelibrary.service.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@SpringBootTest
@ContextConfiguration(classes = {PurchaseController.class})
class PurchaseControllerTest {
    @MockBean
    private PurchaseService purchaseService;
    @MockBean
    private MemberService memberService;
    @MockBean
    private UserService userService;
    @MockBean
    private Model model;
    @Autowired
    private PurchaseController purchaseController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testGetPurchasesPage() {
        List<Purchase> purchases = new ArrayList<>();
        purchases.add(new Purchase());
        purchases.add(new Purchase());
        when(purchaseService.findAll()).thenReturn(purchases);

        String result = purchaseController.getPurchasesPage(model);

        assertEquals("purchase/purchases", result);
        verify(model, times(1)).addAttribute("purchases", purchases);
    }

    @Test
    void testSavePurchase() {
        int bookId = 1;
        int userId = 1;

        String result = purchaseController.savePurchase(bookId, userId);

        assertEquals("redirect:/purchases/my-purchases", result);
        verify(purchaseService, times(1)).savePurchase(userId, bookId);
    }

    @Test
    void testSavePurchase_NotEnoughMoneyException() {
        int bookId = 1;
        int userId = 1;
        doThrow(NotEnoughMoneyException.class).when(purchaseService).savePurchase(userId, bookId);

        try {
            purchaseController.savePurchase(bookId, userId);
        } catch (RuntimeException ex) {
            assertEquals("Not enough money!", ex.getMessage());
        }
    }

}

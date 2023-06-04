package com.bogdan.onlinelibrary.controller;

import com.bogdan.onlinelibrary.entity.CreditCard;
import com.bogdan.onlinelibrary.service.CreditCardService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@SpringBootTest
@ContextConfiguration(classes = {CreditCardController.class})
class CreditCardControllerTest {
    @MockBean
    private CreditCardService creditCardService;
    @MockBean
    private Model model;
    @MockBean
    private BindingResult bindingResult;

    @Autowired
    private CreditCardController creditCardController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testGetCreditCardsPage() {
        CreditCard creditCard = new CreditCard();
        when(creditCardService.findByLoggedInUser()).thenReturn(creditCard);

        String result = creditCardController.getCreditCardsPage(model);

        assertEquals("credit-card/update-credit-card", result);
        verify(model, times(1)).addAttribute("creditCard", creditCard);
    }

    @Test
    void testUpdateCreditCard_WithValidData() {
        CreditCard creditCard = new CreditCard();
        int creditCardId = 1;

        when(bindingResult.hasErrors()).thenReturn(false);

        String result = creditCardController.updateCreditCard(creditCard, creditCardId, bindingResult);

        assertEquals("redirect:/books", result);
        verify(creditCardService, times(1)).update(creditCard);
        assertEquals(creditCardId, creditCard.getId());
    }

    @Test
    void testUpdateCreditCard_WithInvalidData() {
        CreditCard creditCard = new CreditCard();
        int creditCardId = 1;

        when(bindingResult.hasErrors()).thenReturn(true);

        try {
            creditCardController.updateCreditCard(creditCard, creditCardId, bindingResult);
        } catch (RuntimeException e) {
            assertEquals("Credit card data are invalid!", e.getMessage());
        }

        verifyNoInteractions(creditCardService);
    }
}
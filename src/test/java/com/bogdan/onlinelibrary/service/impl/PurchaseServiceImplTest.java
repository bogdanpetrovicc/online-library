package com.bogdan.onlinelibrary.service.impl;

import com.bogdan.onlinelibrary.entity.*;
import com.bogdan.onlinelibrary.entity.domain.MemberType;
import com.bogdan.onlinelibrary.exception.NotEnoughMoneyException;
import com.bogdan.onlinelibrary.repository.PurchaseRepository;
import com.bogdan.onlinelibrary.service.BookService;
import com.bogdan.onlinelibrary.service.MemberService;
import com.bogdan.onlinelibrary.service.PurchaseService;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class PurchaseServiceImplTest {
    @Mock
    private PurchaseRepository purchaseRepository;
    @Mock
    private MemberService memberService;
    @Mock
    private BookService bookService;
    @Mock
    private PurchaseService purchaseService;

    private Book book;
    private Member member;
    private UserEntity user;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);

        // Test data
        CreditCard creditCard = new CreditCard();
        creditCard.setId(1);
        creditCard.setBalance(2000.0);

        user = new UserEntity();
        user.setId(1);
        user.setUsername("test");
        user.setCreditCard(creditCard);

        book = new Book();
        book.setId(1);
        book.setName("Test Book");
        book.setPrice(1000.0);
        book.setAmount(5);

        member = new Member();
        member.setId(1);
        member.setUser(user);
        member.setType(MemberType.PREMIUM);
        member.setDiscount(10);
    }

    @AfterEach
    public void tearDown() {
        verifyNoMoreInteractions(purchaseRepository);
        verifyNoMoreInteractions(memberService);
        verifyNoMoreInteractions(bookService);
        verifyNoMoreInteractions(purchaseService);
    }

    @Test
    public void testSavePurchase_WithSufficientBalance() {
        int initialAmount = 5;
        double initialBalance = 2000.0;
        int discount = 10;

        book.setAmount(initialAmount);
        member.setType(MemberType.PREMIUM);
        member.setDiscount(discount);
        member.getUser().getCreditCard().setBalance(initialBalance);

        when(memberService.findByUserId(user.getId())).thenReturn(member);
        when(bookService.findById(book.getId())).thenReturn(book);
        when(purchaseRepository.save(any(Purchase.class))).thenReturn(new Purchase());

        // Capture the Book object passed to bookService.save()
        ArgumentCaptor<Book> bookCaptor = ArgumentCaptor.forClass(Book.class);

        purchaseService.savePurchase(user.getId(), book.getId());

        // Verify the modified book amount and price
        assertEquals(initialAmount - 1, book.getAmount());
        double expectedPrice = book.getPrice() * discount / 100;
        assertEquals(expectedPrice, book.getPrice());

        // Verify member's credit card balance after the purchase
        assertEquals(initialBalance - expectedPrice, member.getUser().getCreditCard().getBalance());

        // Verify that the save method is called on the book and member services
        verify(bookService, times(1)).save(bookCaptor.capture());
        verify(memberService, times(1)).save(member);

        // Verify that the captured book has the modified amount
        Book capturedBook = bookCaptor.getValue();
        assertEquals(initialAmount - 1, capturedBook.getAmount());

        // Verify that the purchase is saved
        verify(purchaseRepository, times(1)).save(any(Purchase.class));
    }


    @Test
    public void testSavePurchase_WithInsufficientBalance() {
        Integer userId = 1;
        Integer bookId = 1;
        double bookPrice = 10.0;
        double insufficientBalance = 5.0;

        Member member = new Member();
        member.setId(userId);

        Book book = new Book();
        book.setId(bookId);
        book.setPrice(bookPrice);

        member.getUser().getCreditCard().setBalance(insufficientBalance);

        when(memberService.findByUserId(userId)).thenReturn(member);
        when(bookService.findById(bookId)).thenReturn(book);

        assertThrows(NotEnoughMoneyException.class, () -> purchaseService.savePurchase(userId, bookId));

        assertEquals(insufficientBalance, member.getUser().getCreditCard().getBalance());
        verify(memberService, never()).save(any(Member.class));
        verify(purchaseRepository, never()).save(any(Purchase.class));
    }

    @Test
    public void testFindAllByLoggedInMember() {
        Integer memberId = 1;
        String username = "test";

        Member member = new Member();
        member.setId(memberId);

        when(memberService.findByUsername(username)).thenReturn(member);
        when(purchaseRepository.findAllByMemberId(memberId)).thenReturn(Collections.emptyList());

        List<Purchase> purchases = purchaseService.findAllByLoggedInMember();

        assertNotNull(purchases);
        assertEquals(0, purchases.size());
        verify(memberService, times(1)).findByUsername(username);
        verify(purchaseRepository, times(1)).findAllByMemberId(memberId);
    }
}

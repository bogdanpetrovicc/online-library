package com.bogdan.onlinelibrary.service.impl;

import com.bogdan.onlinelibrary.entity.*;
import com.bogdan.onlinelibrary.entity.domain.MemberType;
import com.bogdan.onlinelibrary.exception.NotEnoughMoneyException;
import com.bogdan.onlinelibrary.repository.*;
import com.bogdan.onlinelibrary.service.BookService;
import com.bogdan.onlinelibrary.service.MemberService;
import com.bogdan.onlinelibrary.service.PurchaseService;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.test.context.ContextConfiguration;

import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.*;

@SpringBootTest
@ContextConfiguration(classes = {PurchaseServiceImpl.class})
public class PurchaseServiceImplTest {
    @MockBean
    private PurchaseRepository purchaseRepository;

    @MockBean
    private MemberRepository memberRepository;

    @MockBean
    private MemberService memberService;

    @MockBean
    private BookService bookService;

    @MockBean
    private BookRepository bookRepository;

    @MockBean
    private CreditCardRepository creditCardRepository;

    @MockBean
    private RoleRepository roleRepository;

    @MockBean
    private UserRepository userRepository;

    @MockBean
    private AuthorRepository authorRepository;

    @Autowired
    private PurchaseService purchaseService;

    private static final String USERNAME = "test";

    Book book;
    Member member;
    UserEntity user;
    CreditCard creditCard;
    Role role;
    Author author;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);

        // Test data
        author = new Author(
                1,
                "John",
                "Doe",
                35,
                "SRB"
        );
        when(authorRepository.save(any(Author.class))).thenReturn(author);

        role = new Role(
                1,
                "USER"
        );
        when(roleRepository.save(any(Role.class))).thenReturn(role);

        creditCard = new CreditCard(
                1,
                "123",
                "Bank",
                2000.00
        );
        when(creditCardRepository.save(any(CreditCard.class))).thenReturn(creditCard);

        user = new UserEntity(
                1,
                role,
                creditCard,
                "test",
                "test"
        );
        when(userRepository.save(any(UserEntity.class))).thenReturn(user);

        book = new Book(
                1,
                author,
                "Test Book",
                1000.00,
                5
        );
        when(bookRepository.save(any(Book.class))).thenReturn(book);

        member = new Member(
                1,
                user,
                125,
                10,
                MemberType.PREMIUM
        );
        when(memberRepository.save(any(Member.class))).thenReturn(member);
    }

    @AfterEach
    public void tearDown() {
        bookRepository.deleteAll();
        memberRepository.deleteAll();
        userRepository.deleteAll();
        creditCardRepository.deleteAll();
        roleRepository.deleteAll();
        authorRepository.deleteAll();
    }

    @Test
    public void testSavePurchase_WithSufficientBalance() {
        // Create a mock Purchase object with a valid Book object
        Purchase purchase = new Purchase();
        purchase.setBook(book);

        // Mock the behavior of the memberService
        when(memberService.findByUserId(anyInt())).thenReturn(member);

        // Mock the behavior of the bookService
        when(bookService.findById(anyInt())).thenReturn(book);

        // Mock the behavior of the genericRepository.save() method
        when(purchaseRepository.save(any(Purchase.class))).thenReturn(purchase);

        int initialAmount = book.getAmount();
        double initialBalance = member.getUser().getCreditCard().getBalance();

        Purchase savedPurchase = purchaseService.savePurchase(user.getId(), book.getId());

        assertNotNull(savedPurchase);  // Ensure that the purchase is not null

        assertEquals(initialAmount - 1, savedPurchase.getBook().getAmount());
        double expectedPrice = book.getPrice();
        assertEquals(initialBalance - expectedPrice, member.getUser().getCreditCard().getBalance());

        // Verify that the necessary methods were called
        verify(memberService, times(1)).findByUserId(anyInt());
        verify(bookService, times(1)).findById(anyInt());
        verify(bookService, times(1)).save(any(Book.class));
        verify(memberService, times(1)).save(any(Member.class));
        verify(purchaseRepository, times(1)).save(any(Purchase.class));
    }


    @Test
    public void testSavePurchase_WithInsufficientBalance() {
        // Set the member's credit card balance to an insufficient amount
        member.getUser().getCreditCard().setBalance(0.0);

        // Mock the behavior of the memberService
        when(memberService.findByUserId(anyInt())).thenReturn(member);

        // Mock the behavior of the bookService
        when(bookService.findById(anyInt())).thenReturn(book);

        assertThrows(NotEnoughMoneyException.class, () -> purchaseService.savePurchase(user.getId(), book.getId()));

        // Verify that no changes were made
        assertEquals(5, book.getAmount());
        assertEquals(0.0, member.getUser().getCreditCard().getBalance());

        // Verify that the necessary methods were called
        verify(memberService, times(1)).findByUserId(anyInt());
        verify(bookService, times(1)).findById(anyInt());
        verifyNoMoreInteractions(bookService);
        verifyNoMoreInteractions(memberService);
        verifyNoMoreInteractions(purchaseRepository);
    }

    @Test
    public void testFindAllByLoggedInMember() {
        // Set up the SecurityContext with the authentication
        Authentication authentication = new UsernamePasswordAuthenticationToken(USERNAME, null);
        SecurityContext securityContext = mock(SecurityContext.class);
        when(securityContext.getAuthentication()).thenReturn(authentication);
        SecurityContextHolder.setContext(securityContext);

        // Mock the behavior of the memberService.findByUsername() method
        when(memberService.findByUsername(USERNAME)).thenReturn(member);

        // Mock the behavior of the purchaseRepository.findAllByMemberId() method
        when(purchaseRepository.findAllByMemberId(member.getId())).thenReturn(Collections.emptyList());

        List<Purchase> purchases = purchaseService.findAllByLoggedInMember();

        assertNotNull(purchases);
        assertEquals(0, purchases.size());

        // Verify that the necessary methods were called
        verify(memberService, times(1)).findByUsername(anyString());
        verify(purchaseRepository, times(1)).findAllByMemberId(anyInt());

        // Reset the SecurityContext after the test
        SecurityContextHolder.clearContext();
    }
}

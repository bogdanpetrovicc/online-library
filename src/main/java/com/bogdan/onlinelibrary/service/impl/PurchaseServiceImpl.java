package com.bogdan.onlinelibrary.service.impl;

import com.bogdan.onlinelibrary.entity.Book;
import com.bogdan.onlinelibrary.entity.Member;
import com.bogdan.onlinelibrary.entity.Purchase;
import com.bogdan.onlinelibrary.entity.domain.MemberType;
import com.bogdan.onlinelibrary.exception.NotEnoughMoneyException;
import com.bogdan.onlinelibrary.repository.PurchaseRepository;
import com.bogdan.onlinelibrary.service.BookService;
import com.bogdan.onlinelibrary.service.MemberService;
import com.bogdan.onlinelibrary.service.PurchaseService;
import com.bogdan.onlinelibrary.service.generic.impl.GenericServiceImpl;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public class PurchaseServiceImpl extends GenericServiceImpl<Purchase> implements PurchaseService {

    private final MemberService memberService;
    private final BookService bookService;

    protected PurchaseServiceImpl(PurchaseRepository purchaseRepository, MemberService memberService, BookService bookService) {
        super(purchaseRepository);
        this.memberService = memberService;
        this.bookService = bookService;
    }

    @Override
    public List<Purchase> findAllByMemberId(Integer memberId) {
        return ((PurchaseRepository) genericRepository).findAllByMemberId(memberId);
    }

    @Override
    public void savePurchase(Integer userId, Integer bookId) {
        Member member = memberService.findByUserId(userId);
        Book book = bookService.findById(bookId);

        if (member.getType() == MemberType.PREMIUM) {
            book.setPrice(book.getPrice() * member.getDiscount() / 100);
        }

        if (member.getUser().getCreditCard().getBalance() >= book.getPrice()) {
            genericRepository.save(new Purchase(
                    book,
                    member,
                    LocalDate.now(),
                    book.getPrice()
            ));
        } else {
            throw new NotEnoughMoneyException();
        }
    }
}

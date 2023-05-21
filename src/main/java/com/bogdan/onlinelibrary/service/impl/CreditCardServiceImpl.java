package com.bogdan.onlinelibrary.service.impl;

import com.bogdan.onlinelibrary.entity.CreditCard;
import com.bogdan.onlinelibrary.entity.UserEntity;
import com.bogdan.onlinelibrary.repository.UserRepository;
import com.bogdan.onlinelibrary.repository.generic.GenericRepository;
import com.bogdan.onlinelibrary.security.SecurityUtil;
import com.bogdan.onlinelibrary.service.CreditCardService;
import com.bogdan.onlinelibrary.service.generic.impl.GenericServiceImpl;
import org.springframework.stereotype.Service;

@Service
public class CreditCardServiceImpl extends GenericServiceImpl<CreditCard> implements CreditCardService {

    private final UserRepository userRepository;

    protected CreditCardServiceImpl(GenericRepository<CreditCard> genericRepository, UserRepository userRepository) {
        super(genericRepository);
        this.userRepository = userRepository;
    }

    @Override
    public CreditCard findByLoggedInUser() {
        String username = SecurityUtil.getSessionUser();
        UserEntity user = userRepository.findByUsername(username).orElse(null);
        return user.getId() != null ? userRepository.findById(user.getId()).get().getCreditCard() : new CreditCard();
    }
}

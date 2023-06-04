package com.bogdan.onlinelibrary.service.impl;

import com.bogdan.onlinelibrary.entity.CreditCard;
import com.bogdan.onlinelibrary.entity.UserEntity;
import com.bogdan.onlinelibrary.repository.UserRepository;
import com.bogdan.onlinelibrary.repository.generic.GenericRepository;
import com.bogdan.onlinelibrary.security.SecurityUtil;
import com.bogdan.onlinelibrary.service.CreditCardService;
import com.bogdan.onlinelibrary.service.generic.impl.GenericServiceImpl;
import org.springframework.stereotype.Service;

import java.util.Optional;

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
        Optional<UserEntity> optionalUser = userRepository.findByUsername(username);

        if (optionalUser.isPresent()) {
            UserEntity user = optionalUser.get();
            return userRepository.findById(user.getId())
                    .map(UserEntity::getCreditCard)
                    .orElse(new CreditCard());
        } else {
            return new CreditCard();
        }
    }
}

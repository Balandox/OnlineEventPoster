package org.suai.courceWork.services.implementations;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.suai.courceWork.models.entities.ConfirmationToken;
import org.suai.courceWork.repositories.ConfirmationTokenRepository;
import org.suai.courceWork.services.interfaces.ConfirmationTokenService;

import javax.transaction.Transactional;

@Service
@Transactional
public class ConfirmationTokenServiceImpl implements ConfirmationTokenService {

    private final ConfirmationTokenRepository confirmationTokenRepository;

    @Autowired
    public ConfirmationTokenServiceImpl(ConfirmationTokenRepository confirmationTokenRepository) {
        this.confirmationTokenRepository = confirmationTokenRepository;
    }

    @Override
    public void saveConfirmationToken(ConfirmationToken confirmationToken) {
        this.confirmationTokenRepository.save(confirmationToken);
    }

    @Override
    public ConfirmationToken findByConfirmationToken(String confirmationToken) {
        return this.confirmationTokenRepository.findByConfirmationToken(confirmationToken);
    }
}

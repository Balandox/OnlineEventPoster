package org.suai.courceWork.services.interfaces;

import org.suai.courceWork.models.entities.ConfirmationToken;

public interface ConfirmationTokenService {

    void saveConfirmationToken(ConfirmationToken confirmationToken);

    ConfirmationToken findByConfirmationToken(String confirmationToken);

}

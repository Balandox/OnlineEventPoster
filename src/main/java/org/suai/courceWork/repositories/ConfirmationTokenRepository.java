package org.suai.courceWork.repositories;

import com.sun.mail.imap.protocol.INTERNALDATE;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.suai.courceWork.models.entities.ConfirmationToken;

@Repository
public interface ConfirmationTokenRepository extends JpaRepository<ConfirmationToken, Integer> {

    ConfirmationToken findByConfirmationToken(String confirmationToken);
}

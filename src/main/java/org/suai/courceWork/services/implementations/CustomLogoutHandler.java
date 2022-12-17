package org.suai.courceWork.services.implementations;

import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.logout.LogoutHandler;
import org.springframework.stereotype.Service;
import org.suai.courceWork.dto.BucketDTO;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Service
public class CustomLogoutHandler implements LogoutHandler {

    private final BucketServiceImpl bucketServiceImpl;
    private final UserServiceImpl userServiceImpl;

    public CustomLogoutHandler(BucketServiceImpl bucketServiceImpl, UserServiceImpl userServiceImpl) {
        this.bucketServiceImpl = bucketServiceImpl;
        this.userServiceImpl = userServiceImpl;
    }

    @Override
    public void logout(HttpServletRequest request, HttpServletResponse response,
                       Authentication authentication) {

        try {
            String name = this.userServiceImpl.getPrincipalName();
            BucketDTO bucketDTO = this.bucketServiceImpl.getBucketByUserName(name);
            if(bucketDTO.getTotalAmount() != 0)
                this.bucketServiceImpl.clearBucket(name);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
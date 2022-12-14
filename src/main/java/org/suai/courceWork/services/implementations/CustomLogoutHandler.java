package org.suai.courceWork.services.implementations;

import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.logout.LogoutHandler;
import org.springframework.stereotype.Service;
import org.suai.courceWork.dto.BucketDTO;
import org.suai.courceWork.services.interfaces.BucketService;
import org.suai.courceWork.services.interfaces.UserService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Service
public class CustomLogoutHandler implements LogoutHandler {

    private final BucketService bucketService;
    private final UserService userService;

    public CustomLogoutHandler(BucketService bucketService, UserService userService) {
        this.bucketService = bucketService;
        this.userService = userService;
    }

    @Override
    public void logout(HttpServletRequest request, HttpServletResponse response,
                       Authentication authentication) {

        try {
            String name = this.userService.getPrincipalName();
            BucketDTO bucketDTO = this.bucketService.getBucketByUserName(name);
            if(bucketDTO.getTotalAmount() != 0)
                this.bucketService.clearBucket(name);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
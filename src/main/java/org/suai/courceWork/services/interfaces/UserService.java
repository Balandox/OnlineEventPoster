package org.suai.courceWork.services.interfaces;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.suai.courceWork.models.entities.User;

import java.util.List;

public interface UserService extends UserDetailsService {

    UserDetails loadUserByUsername(String username) throws UsernameNotFoundException;

    User findByEmail(String email);

    User findFirstByName(String name);

    List<User> allUsers();

    User findUserById(int userId);

    User saveUser(User user);

    boolean deleteUser(int userId);

    String getPrincipalName() throws Exception;

}

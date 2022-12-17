package org.suai.courceWork.services.implementations;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.suai.courceWork.models.entities.User;
import org.suai.courceWork.models.enums.Role;
import org.suai.courceWork.repositories.UserRepository;
import org.suai.courceWork.services.interfaces.UserService;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public UserServiceImpl(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findFirstByName(username);
        if(user == null){
            throw new UsernameNotFoundException("User not found with name: " + username);
        }

        List<GrantedAuthority> roles = new ArrayList<>();
        roles.add(new SimpleGrantedAuthority(user.getRole().name()));

        return new org.springframework.security.core.userdetails.User(
                user.getName(),
                user.getPassword(),
                roles);
    }

    private User findByName(String name) {

        List<User> users = userRepository.findAll();

        for(User tempUser : users)
            if(tempUser.getName().equals(name))
                return tempUser;

        return null;
    }

    public User findByEmail(String email){

        return this.userRepository.findUserByEmail(email);

    }

    public User findFirstByName(String name){
        return this.userRepository.findFirstByName(name);
    }

    public User findUserById(int userId) {
        Optional<User> user = userRepository.findById(userId);
        return user.orElse(null);
    }

    public List<User> allUsers() {
        return userRepository.findAll();
    }

    public User saveUser(User user) {


        if (findByName(user.getName()) != null) {
            return null;
        }

        user.setRole(Role.USER);
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        user = userRepository.save(user);

        return user;
    }

    public boolean deleteUser(int userId) {
        if (userRepository.findById(userId).isPresent()) {
            userRepository.deleteById(userId);
            return true;
        }
        return false;
    }

    public String getPrincipalName() throws Exception{

        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth.isAuthenticated()) {
            return auth.getName();
        } else {
            throw new Exception("Not authenticated");
        }

    }

}


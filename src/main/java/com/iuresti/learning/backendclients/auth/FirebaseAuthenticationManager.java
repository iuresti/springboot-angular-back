package com.iuresti.learning.backendclients.auth;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.iuresti.learning.backendclients.models.User;
import com.iuresti.learning.backendclients.repository.IRoleDAO;
import com.iuresti.learning.backendclients.services.IUserService;

@Service
public class FirebaseAuthenticationManager implements AuthenticationManager {

    @Autowired
    private IUserService userService;

    @Autowired
    private IRoleDAO roleDAO;

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        UserDetails userDetails;
        try {
            userDetails = userService.loadUserByUsername(authentication.getName());
        } catch (UsernameNotFoundException ex) {
            userDetails = tryToRegister((FirebaseTokenHolder) authentication.getDetails());
        }

        return new FirebaseAuthenticationToken(authentication, userDetails.getAuthorities(), true);
    }

    @Transactional
    public UserDetails tryToRegister(FirebaseTokenHolder details) {

        validateEmail(details.getEmail());

        User user = new User();

        user.setUsername(details.getUid());
        user.setEmail(details.getEmail());
        user.setEnabled(true);
        user.setName(details.getName());

        return userService.tryToRegister(user, "ROLE_TECKER", "ROLE_USER");
    }

    private void validateEmail(String email) {
        if (!(email.equals("iuresti@nearsoft.com") || email.equals("ivan.uresti@gmail.com"))) {
            throw new UsernameNotFoundException("User not allowed");
        }
    }
}

package com.iuresti.learning.backendclients.services.impl;

import java.util.List;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.iuresti.learning.backendclients.models.User;
import com.iuresti.learning.backendclients.repository.IRoleDAO;
import com.iuresti.learning.backendclients.repository.IUserDAO;
import com.iuresti.learning.backendclients.services.IUserService;

@Service
public class UserService implements IUserService {

    private static final Logger logger = LoggerFactory.getLogger(UserService.class);

    private IUserDAO userDAO;

    private IRoleDAO roleDAO;

    public UserService(IUserDAO userDAO, IRoleDAO roleDAO) {
        this.userDAO = userDAO;
        this.roleDAO = roleDAO;
    }

    @Override
    @Transactional(readOnly = true)
    public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {

        User user = userDAO.findByUsername(s);

        if (user == null) {
            logger.warn("El usuario {} no existe", s);
            throw new UsernameNotFoundException("No existe el usuario: " + s);
        }

        List<GrantedAuthority> authorities = user.getRoles()
                .stream().map(role -> new SimpleGrantedAuthority(role.getName())).collect(Collectors.toList());

        return new org.springframework.security.core.userdetails.User(user.getUsername(), "", authorities);
    }

    @Override
    @Transactional(readOnly = true)
    public User findByUsername(String username) {
        return userDAO.findByUsername(username);
    }

    @Override
    public UserDetails tryToRegister(User user, String... roleNames) {

        user.setRoles(roleDAO.findAllByNameIn(roleNames));
        userDAO.save(user);

        List<GrantedAuthority> authorities = user.getRoles()
                .stream().map(role -> new SimpleGrantedAuthority(role.getName())).collect(Collectors.toList());

        return new org.springframework.security.core.userdetails.User(user.getUsername(), "", authorities);
    }
}

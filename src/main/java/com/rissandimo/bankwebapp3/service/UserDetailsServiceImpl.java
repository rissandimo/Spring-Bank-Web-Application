package com.rissandimo.bankwebapp3.service;

import com.rissandimo.bankwebapp3.dao.User;
import com.rissandimo.bankwebapp3.repository.UserRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class UserDetailsServiceImpl implements UserDetailsService
{
    private UserRepository userRepository;

    public UserDetailsServiceImpl(UserRepository userRepository)
    {
        this.userRepository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException
    {
        User user = userRepository.findUserByUsername(username);
        UserDetailsImpl userDetails = new UserDetailsImpl(user);
        return userDetails;
    }
}

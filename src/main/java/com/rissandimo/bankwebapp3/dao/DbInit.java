package com.rissandimo.bankwebapp3.dao;

import com.rissandimo.bankwebapp3.repository.UserRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class DbInit implements CommandLineRunner
{
    private UserRepository userRepository;
    private BCryptPasswordEncoder passwordEncoder;

    public DbInit(UserRepository userRepository, BCryptPasswordEncoder passwordEncoder)
    {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public void run(String... args) throws Exception
    {
/*        User userOmid = new User();
        userOmid.setFirstName("Omid");
        userOmid.setLastName("Nassir");
        userOmid.setUsername("rissandimo");
        userOmid.setPassword(passwordEncoder.encode("password"));
        userOmid.setActive(1);
        userOmid.setRoles("USER");
        userRepository.save(userOmid);*/
    }
}

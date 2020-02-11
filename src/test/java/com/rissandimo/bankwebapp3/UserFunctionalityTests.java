package com.rissandimo.bankwebapp3;

import com.rissandimo.bankwebapp3.dao.User;
import com.rissandimo.bankwebapp3.repository.UserRepository;
import org.junit.Assert;
import org.junit.Test;

import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.test.context.junit4.SpringRunner;

import java.beans.Transient;

@RunWith(SpringRunner.class)
@SpringBootTest
public class UserFunctionalityTests {

	@Autowired
	private UserRepository userRepository;

	@Autowired
	BCryptPasswordEncoder bCryptPasswordEncoder;

	Logger logger = LoggerFactory.getLogger(this.getClass());

	@Transient
	@Test
	public void createUserTest() {

		User user = new User("test name", "testusername", "test username", "testpassword");

		user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
		user.setActive(1);
		user.setRoles("USER");

		userRepository.save(user);
		User someUser = userRepository.findUserByUsername(user.getUsername());
		logger.info("User's full name: {}", someUser.getFullName());


		Assert.assertTrue(someUser.equals(user));
	}

}

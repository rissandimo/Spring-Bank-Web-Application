package com.rissandimo.bankwebapp3.repository;

import com.rissandimo.bankwebapp3.dao.User;
import org.springframework.data.repository.CrudRepository;

public interface UserRepository extends CrudRepository<User, Integer>
{
    User findUserByUsername(String username);
}

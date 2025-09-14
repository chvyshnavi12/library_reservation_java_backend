package com.login.loginpage.repo;

import com.login.loginpage.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.io.Serializable;

public interface UserRepo extends JpaRepository<User, Serializable> {
    User findByUsernameAndPassword(String username, String password);

}

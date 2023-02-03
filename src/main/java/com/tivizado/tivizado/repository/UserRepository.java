package com.tivizado.tivizado.repository;


import com.tivizado.tivizado.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, String> {
public User findByUserNameAndPassword(String userName, String password);
}
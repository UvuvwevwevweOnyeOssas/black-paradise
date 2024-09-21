package com.example.demo.repository;

import com.example.demo.entity.User;
import com.example.demo.entity.Role;
import org.springframework.data.jpa.repository.JpaRepository;

import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    boolean existsByEmailAndPhoneNum(String email, String phoneNum);


    List<User> findAllByUserTypes(Role userTypes);

    User findByEmail(String email);
}

package com.example.demo.repository;

import com.example.demo.entity.User;
import com.example.demo.entity.UserTypes;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    boolean existsByEmailAndPhoneNum(String email, String phoneNum);


    List<User> findAllByUserTypes(UserTypes userTypes);
}

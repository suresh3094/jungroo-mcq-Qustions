package com.jungroo.task.repository;

import com.jungroo.task.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User,String> {
    User findByuserId(String userId);
    User findByUserName(String username);
}

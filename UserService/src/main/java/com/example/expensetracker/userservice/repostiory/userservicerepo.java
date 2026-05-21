package com.example.expensetracker.userservice.repostiory;

import com.example.expensetracker.userservice.entites.UserInfo;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface userservicerepo extends JpaRepository<UserInfo, String > {
    Optional<UserInfo> findByUserId(String userId);
}

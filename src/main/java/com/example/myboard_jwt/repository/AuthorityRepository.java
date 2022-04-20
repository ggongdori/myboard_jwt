package com.example.myboard_jwt.repository;

import com.example.myboard_jwt.entity.Authority;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AuthorityRepository extends JpaRepository<Authority, String> {
}
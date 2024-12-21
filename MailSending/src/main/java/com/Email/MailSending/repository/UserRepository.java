package com.Email.MailSending.repository;

import com.Email.MailSending.module.Users;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<Users, Long> {
    Optional<Users> findByEmail(String email);  // Method to find a user by email

    Users findByName(String username);

//    Optional<Users> findById(Long id);

    Page<Users> findByNameContainingIgnoreCase(String name, Pageable pageable);

//    double countByStatus(String active);
}
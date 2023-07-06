package com.test.Test.Repo;

import org.springframework.data.jpa.repository.JpaRepository;

import com.test.Test.entity.Email;

public interface EmailRepo extends JpaRepository<Email, Long>{

	Email findByEmail(String email);
}

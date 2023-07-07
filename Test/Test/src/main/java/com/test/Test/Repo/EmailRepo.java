package com.test.Test.Repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.test.Test.entity.EmailMsg;

public interface EmailRepo extends JpaRepository<EmailMsg, Long>{

	@Query(value = "select * from Email_Verify where email = ? order by id DESC limit 1", nativeQuery = true)
	public EmailMsg findByEmail(String email);
}

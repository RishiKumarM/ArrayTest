package com.test.Test.Repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.test.Test.entity.LoginEvent;
import com.test.Test.entity.User;

@Repository
public interface LoginEventRepository extends JpaRepository<LoginEvent, Long>{

	List<LoginEvent> findByUserOrderByLoginTimeDesc(User user);
}

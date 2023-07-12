package com.test.Test.Service;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.test.Test.Repo.LoginEventRepository;
import com.test.Test.Repo.UserRepository;
import com.test.Test.entity.LoginEvent;
import com.test.Test.entity.User;

@Service
public class UserService {

	
	 private UserRepository userRepository;
	    private LoginEventRepository loginEventRepository;

	    @Autowired
	    public UserService(UserRepository userRepository, LoginEventRepository loginEventRepository) {
	        this.userRepository = userRepository;
	        this.loginEventRepository = loginEventRepository;
	    }
	    
	    public User login(String username, String password) {
	        User user = userRepository.findByUsername(username);
	        if (user != null && user.getPassword().equals(password)) {
	            // Save login event
	            LoginEvent loginEvent = new LoginEvent();
	            loginEvent.setUser(user);
	            loginEvent.setLoginTime(LocalDateTime.now());
	            loginEventRepository.save(loginEvent);
	            return user;
	        }
	        return null;
	    }

	    public void logout(User user) {
	    	
	    	    // Revoke authentication tokens
	    	    tokenService.revokeTokens(user.getId());
	    	    
	    	    // Clear user session data
	    	    sessionService.clearSessionData(user.getId());
	    	    
	    	    // Log the user out of any connected devices
	    	    deviceService.logoutAllDevices(user.getId());
	    	    
	    	    // Perform any additional necessary logout operations specific to your application
	    	    
	    	    // Update user's login status
	    	    user.setLoggedIn(false);
	    	    userRepository.save(user);
	    	}

	    public List<LoginEvent> getLoginHistory(User user) {
	        return loginEventRepository.findByUserOrderByLoginTimeDesc(user);
	    }
}

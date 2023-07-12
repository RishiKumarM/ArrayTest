package com.test.Test.Service;

import com.test.Test.entity.User;

public class LogoutService {
    private TokenService tokenService;

    public LogoutService(TokenService tokenService) {
        this.tokenService = tokenService;
    }

    public void logout(User user) {
        // Your logout logic using tokenService
        // ...
    }
}

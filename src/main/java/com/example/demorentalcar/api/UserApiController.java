package com.example.demorentalcar.api;

import com.example.demorentalcar.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping(path = "v1")
@RestController
public class UserApiController implements UserApi {

    @Autowired
    private UserApiService userApiService;

    @Override
    public User register(User user) {
        return userApiService.register(user.getEmail(), user.getFriendlyName());
    }

    @Override
    public User findByEmail(String email) {
        return userApiService.findByEmail(email);
    }

    @Override
    public User findById(String id) {
        return userApiService.findById(id);
    }

    @Override
    public void updateUserFriendlyName(User user, String userId) {
        userApiService.updateFriendlyName(userId, user.getFriendlyName());
    }

    @Override
    public void deleteUserById(String id, String apiKey) {
        userApiService.deleteUser(id, apiKey);
    }
}

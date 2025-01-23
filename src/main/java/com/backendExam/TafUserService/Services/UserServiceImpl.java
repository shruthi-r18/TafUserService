package com.backendExam.TafUserService.Services;

import com.backendExam.TafUserService.Models.User;
import com.backendExam.TafUserService.Services.Interface.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.HttpServerErrorException;
import org.springframework.web.client.RestTemplate;

@Service
public class UserServiceImpl implements UserService {
    @Autowired
    private final RestTemplate restTemplate;

    public UserServiceImpl(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }
    @Value("${datastore.ms.url}")
    String datastore_url ;

    @Override
    public User add(User user) {
        String datastoreUrl = datastore_url +"/users/register"  ;
        User userCreated = restTemplate.postForObject(datastoreUrl, user, User.class);
        return userCreated;
    }

    @Override
    public User get(Long userId) {
        String datastoreUrl = datastore_url +"/users/" + userId;
        User user = restTemplate.getForObject(datastoreUrl, User.class);
        return user;
    }

    @Override
    public User update(Long userId, User user){
        String datastoreUrl = datastore_url +"/users/" + userId;
        if (userId == null || user == null) {
            throw new IllegalArgumentException("UserId and User must not be null");
        }

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        HttpEntity<User> entity = new HttpEntity<>(user, headers);

        try {
            User response = restTemplate.exchange(
                    datastoreUrl, HttpMethod.PUT, entity, User.class
            ).getBody();
            return response;
        } catch (HttpClientErrorException | HttpServerErrorException ex) {
            // Log the error and rethrow or return a default value
//            logger.error("Error updating user with ID {}: {}", userId, ex.getMessage());
            throw new RuntimeException("Failed to update user", ex);
        } catch (Exception ex) {
            // Handle other unexpected exceptions
//            logger.error("Unexpected error: {}", ex.getMessage());
            throw new RuntimeException("Unexpected error occurred", ex);
        }
    }
}

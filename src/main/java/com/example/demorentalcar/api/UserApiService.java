package com.example.demorentalcar.api;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapper;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBQueryExpression;
import com.amazonaws.services.dynamodbv2.model.AttributeValue;
import com.example.demorentalcar.configuration.AppConfig;
import com.example.demorentalcar.constant.TableConstValue;
import com.example.demorentalcar.exception.*;
import com.example.demorentalcar.model.User;
import lombok.extern.log4j.Log4j2;
import org.apache.commons.validator.routines.EmailValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

import static com.example.demorentalcar.constant.ConstValue.UUID_LEN;

@Service
@Log4j2
public class UserApiService {

    @Autowired
    private AppConfig appConfig;

    @Autowired
    private DynamoDBMapper mapper;

    public User register(String email, String friendlyName) {

        if(!EmailValidator.getInstance().isValid(email)) {
            throw new InvalidInputException("Invalid Email");
        }

        User exist;
        try {
            exist = this.findByEmail(email);
        } catch (UserNotFountException e) {
            exist = null;
        }

        if (exist != null) {
            throw new UserExistException("Account already exist for ".concat(email));
        } else {
            User user = User.builder()
                    .id(UUID.randomUUID().toString())
                    .friendlyName(friendlyName)
                    .email(email)
                    .createdOn(new Date())
                    .build();
            try {
                mapper.save(user);
            } catch (RuntimeException e) {
                log.error("Failed to register user: " + e.getMessage());
                throw new UserDataAccessException("Failed to register new account for ".concat(email));
            }
            return user;
        }
    }

    public User findByEmail(String email) throws UserNotFountException {

        if (!EmailValidator.getInstance().isValid(email)) {
            throw new InvalidInputException("Invalid Email");
        }

        Map<String, AttributeValue> eav = new HashMap<>();
        eav.put(":val1", new AttributeValue().withS(email));

        DynamoDBQueryExpression<User> queryExpression = new DynamoDBQueryExpression<User>()
                .withIndexName(TableConstValue.USER_TABLE_EMAIL_INDEX)
                .withKeyConditionExpression(TableConstValue.USER_TABLE_ATTR_EMAIL.concat(" = :val1 "))
                .withConsistentRead(false)
                .withExpressionAttributeValues(eav);

        List<User> userList = mapper.query(User.class, queryExpression);
        if (userList == null || userList.size() == 0) {
            throw new UserNotFountException("User not found by ".concat(email));
        } else {
            return userList.get(0);
        }
    }

    public User findById(String id) {

        if (id == null || id.length() != UUID_LEN) {
            throw new InvalidInputException("Invalid User ID");
        }


        Map<String, AttributeValue> eav = new HashMap<>();
        eav.put(":val1", new AttributeValue().withS(id));

        DynamoDBQueryExpression<User> queryExpression = new DynamoDBQueryExpression<User>()
                .withKeyConditionExpression(TableConstValue.USER_TABLE_ATTR_ID.concat(" = :val1 "))
                .withExpressionAttributeValues(eav);

        List<User> userList = mapper.query(User.class, queryExpression);
        if (userList == null || userList.size() == 0) {
            throw new UserNotFountException("User not found by ".concat(id));
        } else {
            return userList.get(0);
        }
    }

    public User updateFriendlyName(String userId, String friendlyName) {

        if (userId == null || userId.length() != UUID_LEN) {
            throw new InvalidInputException("Invalid User ID");
        }

        User user = this.findById(userId);
        if (user == null) {
            throw new UserNotFountException("User not found by ".concat(userId));
        } else {
            user.setFriendlyName(friendlyName);
            mapper.save(user);
            return user;
        }
    }

    public void deleteUser(String userId, String apiKey) {

        if (!appConfig.getApiKey().equals(apiKey)) {
            throw new UserDataUnauthorizedException("Unauthorized request");
        }

        if (userId == null || userId.length() != UUID_LEN) {
            throw new InvalidInputException("Invalid User ID");
        }

        User user = this.findById(userId);
        if (user != null) {
            mapper.delete(user);
        }
    }
}
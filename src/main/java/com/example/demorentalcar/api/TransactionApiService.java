package com.example.demorentalcar.api;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapper;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBQueryExpression;
import com.amazonaws.services.dynamodbv2.datamodeling.PaginatedQueryList;
import com.amazonaws.services.dynamodbv2.model.AttributeValue;
import com.example.demorentalcar.constant.TableConstValue;
import com.example.demorentalcar.exception.InvalidInputException;
import com.example.demorentalcar.exception.UserNotFountException;
import com.example.demorentalcar.model.Transaction;
import com.example.demorentalcar.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.*;

@Service
public class TransactionApiService {

    @Autowired
    private DynamoDBMapper mapper;

    @Autowired
    private UserApiService userApiServicel;

    public List<Transaction> getAllTransaction(String userId, String stateParam) {

        User user = userApiServicel.findById(userId);
        if (user == null) {
            throw new UserNotFountException("User not found by Id: " .concat(userId));
        }

        Transaction.TransactionState state;
        try {
            if (StringUtils.hasLength(stateParam)) {
                state = Transaction.TransactionState.valueOf(stateParam);
            } else {
                state = null;
            }
        } catch (Exception e) {
            throw new InvalidInputException("State invalid");
        }

        Map<String, AttributeValue> eav = new HashMap<>();
        String keyConditionExpression;
        DynamoDBQueryExpression<Transaction> dynamoDBQueryExpression;

        eav.put(":val1", new AttributeValue().withS(userId));

        if (state != null) {
            eav.put(":val2", new AttributeValue().withS(state.toString()));
            keyConditionExpression = TableConstValue.TRANSACTION_TABLE_ATTR_USER_ID
                    .concat(" = :val1 and ")
                    .concat(TableConstValue.TRANSACTION_TABLE_ATTR_STATE)
                    .concat(" = :val2");
             dynamoDBQueryExpression = new DynamoDBQueryExpression<Transaction>()
                    .withIndexName(TableConstValue.TRANSACTION_TABLE_STATE_INDEX)
                    .withKeyConditionExpression(keyConditionExpression)
                    .withExpressionAttributeValues(eav);
        } else {
            keyConditionExpression = TableConstValue.TRANSACTION_TABLE_ATTR_USER_ID
                    .concat(" = :val1");
            dynamoDBQueryExpression = new DynamoDBQueryExpression<Transaction>()
                    .withKeyConditionExpression(keyConditionExpression)
                    .withExpressionAttributeValues(eav);
        }

        PaginatedQueryList<Transaction> paginatedQueryList = mapper.query(Transaction.class, dynamoDBQueryExpression);
        List<Transaction> transactionList = new ArrayList<>(paginatedQueryList);
        transactionList.sort(Comparator.comparing(Transaction::getOpenAt).reversed());
        return transactionList;
    }

    public Transaction getTransaction(String userId, String transactionId) {
        User user = userApiServicel.findById(userId);
        if (user == null) {
            throw new UserNotFountException("User not found by Id: " .concat(userId));
        }

        Map<String, AttributeValue> eav = new HashMap<String, AttributeValue>();
        eav.put(":val1", new AttributeValue().withS(userId));
        eav.put(":val2", new AttributeValue().withS(transactionId));

        String keyConditionExpression = TableConstValue.TRANSACTION_TABLE_ATTR_USER_ID
                .concat(" = :val1 and ")
                .concat(TableConstValue.TRANSACTION_TABLE_ATTR_ID)
                .concat(" = :val2");
        DynamoDBQueryExpression<Transaction> dynamoDBQueryExpression = new DynamoDBQueryExpression<Transaction>()
                .withKeyConditionExpression(keyConditionExpression)
                .withExpressionAttributeValues(eav);
        PaginatedQueryList<Transaction> paginatedQueryList = mapper.query(Transaction.class, dynamoDBQueryExpression);
        return paginatedQueryList.stream().findFirst().orElse(null);
    }

}

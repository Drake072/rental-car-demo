package com.example.demorentalcar.model;

import com.amazonaws.services.dynamodbv2.datamodeling.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

import static com.example.demorentalcar.constant.TableConstValue.*;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
@DynamoDBTable(tableName = TRANSACTION_TABLE_NAME)
public class Transaction {

    @DynamoDBHashKey(attributeName = TRANSACTION_TABLE_ATTR_USER_ID)
    private String userId;

    @DynamoDBRangeKey(attributeName = TRANSACTION_TABLE_ATTR_ID)
    private String id;

    @DynamoDBIndexRangeKey(localSecondaryIndexName = TRANSACTION_TABLE_STATE_INDEX, attributeName = TRANSACTION_TABLE_ATTR_STATE)
    private String state;

    @DynamoDBAttribute(attributeName = TRANSACTION_TABLE_ATTR_CAR_ID)
    private Long carId;

    @DynamoDBAttribute(attributeName = TRANSACTION_TABLE_ATTR_CLOSE_AT)
    private Date closeAt;

    @DynamoDBAttribute(attributeName = TRANSACTION_TABLE_ATTR_OPEN_AT)
    private Date openAt;

    public enum TransactionState {
        OPEN,
        CLOSE
    }
}

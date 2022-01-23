package com.example.demorentalcar.model;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBAttribute;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBHashKey;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBTable;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import static com.example.demorentalcar.constant.TableConstValue.*;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
@DynamoDBTable(tableName = CAR_TABLE_NAME)
public class Car {

    @DynamoDBHashKey(attributeName = CAR_TABLE_ATTR_ID)
    private Long id;

    @DynamoDBAttribute(attributeName = CAR_TABLE_ATTR_MODEL)
    private String model;

    @DynamoDBAttribute(attributeName = CAR_TABLE_ATTR_TRANSACTION_ID)
    @JsonInclude(value = JsonInclude.Include.NON_EMPTY)
    private String transactionId;

    @DynamoDBAttribute(attributeName = CAR_TABLE_ATTR_AVAILABILITY)
    private String status;

    public enum Status {
        IN_STOCK,
        RESERVED
    }

    public enum Model {
        CAMRY,
        BMW650
    }
}

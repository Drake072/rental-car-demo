package com.example.demorentalcar.api;

import com.amazonaws.services.dynamodbv2.datamodeling.*;
import com.amazonaws.services.dynamodbv2.model.*;
import com.example.demorentalcar.constant.TableConstValue;
import com.example.demorentalcar.exception.*;
import com.example.demorentalcar.model.Car;
import com.example.demorentalcar.model.Transaction;
import com.example.demorentalcar.model.User;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import static com.example.demorentalcar.constant.TableConstValue.CAR_TABLE_ATTR_AVAILABILITY;

@Log4j2
@Service
public class ReservationApiService {

    @Autowired
    private UserApiService userApiService;

    @Autowired
    private CarApiService carApiService;

    @Autowired
    private DynamoDBMapper mapper;

    public Transaction reserveCar(String userId, Long carId) {

        User user = userApiService.findById(userId);
        if (user == null) {
            throw new UserNotFountException("Uesr not found by id: " + userId);
        }

        Car car = carApiService.getCar(carId);
        if (car == null) {
            throw new InvalidInputException("Invalid car id");
        } else if (car.getStatus().equals(Car.Status.RESERVED.toString())) {
            throw new CarReservationFailedException("Car not available");
        }

        String transactionId = UUID.randomUUID().toString();
        Transaction newTransaction = Transaction.builder()
                .carId(carId)
                .id(transactionId)
                .userId(userId)
                .openAt(new Date())
                .state(Transaction.TransactionState.OPEN.toString())
                .build();

        car.setTransactionId(transactionId);
        car.setStatus(Car.Status.RESERVED.toString());
        car.setTransactionId(transactionId);

        Map<String, AttributeValue> eav = new HashMap<>();
        eav.put(":val1", new AttributeValue().withS(Car.Status.IN_STOCK.toString()));
        DynamoDBTransactionWriteExpression checkCarStateCondition = new DynamoDBTransactionWriteExpression()
                .withConditionExpression(CAR_TABLE_ATTR_AVAILABILITY.concat(" = :val1"))
                .withExpressionAttributeValues(eav);

        TransactionWriteRequest transactionWriteRequest = null;
        try {
            transactionWriteRequest = new TransactionWriteRequest();
            transactionWriteRequest.addUpdate(car, checkCarStateCondition);
            transactionWriteRequest.addPut(newTransaction);
        } catch (Exception e) {
            log.error("Exception: " + e.getMessage());
            throw new InternalErrorException("Internal error");
        }
        executeTransactionWrite(transactionWriteRequest);

        return newTransaction;
    }

    public void returnCar(String userId, Long carId) {

        User user = userApiService.findById(userId);
        if (user == null) {
            throw new UserNotFountException("Uesr not found by id: " + userId);
        }

        Car car = carApiService.getCar(carId);
        if (car == null) {
            throw new InvalidInputException("Invalid car id");
        } else if (car.getStatus().equals(Car.Status.IN_STOCK.toString())) {
            throw new CarReservationFailedException("Car not reserved");
        }

        String transactionId = car.getTransactionId();
        Transaction existTransaction = Transaction.builder().id(transactionId).userId(userId).build();
        Condition rangeKeyCondition = new Condition()
                .withComparisonOperator(ComparisonOperator.EQ.toString())
                .withAttributeValueList(new AttributeValue().withS(transactionId));
        DynamoDBQueryExpression<Transaction> transactionDynamoDBQueryExpression = new DynamoDBQueryExpression<Transaction>()
                .withHashKeyValues(existTransaction)
                .withRangeKeyCondition(TableConstValue.TRANSACTION_TABLE_ATTR_ID, rangeKeyCondition);

        PaginatedQueryList<Transaction> paginatedQueryList = mapper.query(Transaction.class, transactionDynamoDBQueryExpression);
        existTransaction = paginatedQueryList.stream().findFirst().orElse(null);
        existTransaction.setCloseAt(new Date());
        existTransaction.setState(Transaction.TransactionState.CLOSE.toString());

        car.setTransactionId(null);
        car.setStatus(Car.Status.IN_STOCK.toString());

        Map<String, AttributeValue> eav = new HashMap<>();
        eav.put(":val1", new AttributeValue().withS(Car.Status.RESERVED.toString()));
        DynamoDBTransactionWriteExpression checkCarStateCondition = new DynamoDBTransactionWriteExpression()
                .withConditionExpression(CAR_TABLE_ATTR_AVAILABILITY.concat(" = :val1"))
                .withExpressionAttributeValues(eav);

        TransactionWriteRequest transactionWriteRequest = null;
        try {

            transactionWriteRequest = new TransactionWriteRequest();
            transactionWriteRequest.addUpdate(car, checkCarStateCondition);
            transactionWriteRequest.addUpdate(existTransaction);

        } catch (Exception e) {
            log.error("Exception: " + e.getMessage());
            throw new InternalErrorException("Internal error");
        }
        executeTransactionWrite(transactionWriteRequest);
    }

    private void executeTransactionWrite(TransactionWriteRequest transactionWriteRequest) {
        try {
            mapper.transactionWrite(transactionWriteRequest);
        } catch (DynamoDBMappingException ddbme) {
            log.error("Client side error in Mapper, fix before retrying. Error: " + ddbme.getMessage());
            throw new InternalErrorException("Internal error");
        } catch (ResourceNotFoundException rnfe) {
            log.error("One of the tables was not found, verify table exists before retrying. Error: " + rnfe.getMessage());
            throw new InternalErrorException("Internal error");
        } catch (InternalServerErrorException ise) {
            log.error("Internal Server Error, generally safe to retry with back-off. Error: " + ise.getMessage());
            throw new RetryLaterException("Internal error");
        } catch (TransactionCanceledException tce) {
            log.error("Transaction Canceled, implies a client issue, fix before retrying. Error: " + tce.getMessage());
            for (CancellationReason cancellationReason : tce.getCancellationReasons()) {
                log.error(cancellationReason);
            }
            throw new CarReservationFailedException("Car Reservation Failed, please retry");
        } catch (Exception ex) {
            log.error("An exception occurred, investigate and configure retry strategy. Error: " + ex.getMessage());
            throw new InternalErrorException("Internal error");
        }
    }

}

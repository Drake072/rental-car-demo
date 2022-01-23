package com.example.demorentalcar.api;

import com.amazonaws.services.dynamodbv2.datamodeling.*;
import com.amazonaws.services.dynamodbv2.model.AttributeValue;
import com.example.demorentalcar.constant.TableConstValue;
import com.example.demorentalcar.exception.InvalidInputException;
import com.example.demorentalcar.model.Car;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@Log4j2
public class CarApiService {

    @Autowired
    private DynamoDBMapper mapper;

    public List<Car> getCars(String carStatus, String carModel) {
        Car.Model model = null;
        Car.Status status = null;
        try {
            if (StringUtils.hasLength(carModel)) {
                model = (Car.Model.valueOf(carModel.toUpperCase()));
            }
            if (StringUtils.hasLength(carStatus)) {
                status = Car.Status.valueOf(carStatus);
            }
        } catch (Exception e) {
            throw new InvalidInputException("Check model and status parameter");
        }

        DynamoDBScanExpression dynamoDBScanExpression = new DynamoDBScanExpression();
        if (status != null) {
            Map<String, AttributeValue> eav = new HashMap<>();
            eav.put(":val1", new AttributeValue().withS(status.toString()));
            dynamoDBScanExpression.setFilterExpression(TableConstValue.CAR_TABLE_ATTR_AVAILABILITY.concat(" = :val1"));
            dynamoDBScanExpression.setExpressionAttributeValues(eav);
        }

        if (model != null) {
            Map<String, AttributeValue> eav2 = new HashMap<>();
            eav2.put(":val2", new AttributeValue().withS(model.toString()));
            dynamoDBScanExpression.setFilterExpression(TableConstValue.CAR_TABLE_ATTR_MODEL.concat(" = :val2"));
            dynamoDBScanExpression.setExpressionAttributeValues(eav2);
        }

        PaginatedScanList<Car> paginatedQueryList = mapper.scan(Car.class, dynamoDBScanExpression);

        return paginatedQueryList.stream().peek(car -> car.setTransactionId(null)).collect(Collectors.toList());

    }

    public Car getCar(Long carId) {
        DynamoDBQueryExpression<Car> dynamoDBQueryExpression = new DynamoDBQueryExpression<>();
        dynamoDBQueryExpression.setHashKeyValues(Car.builder().id(carId).build());

        PaginatedQueryList<Car> paginatedQueryList = mapper.query(Car.class, dynamoDBQueryExpression);
        return paginatedQueryList.stream().findFirst().orElse(null);
    }

    public List<String> getCarModels() {
        return Arrays.stream(Car.Model.values()).map(Car.Model::toString).collect(Collectors.toList());
    }
}

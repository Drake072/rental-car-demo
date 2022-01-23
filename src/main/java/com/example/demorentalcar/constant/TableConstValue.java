package com.example.demorentalcar.constant;

public class TableConstValue {

    public static final String ATTR_CREATED_ON = "createdOn";

    public static final String USER_TABLE_NAME = "rental-user";
    public static final String USER_TABLE_EMAIL_INDEX = "email-index";
    public static final String USER_TABLE_ATTR_ID = "id";
    public static final String USER_TABLE_ATTR_EMAIL = "email";
    public static final String USER_TABLE_ATTR_FRIENDLY_NAME = "friendlyName";

    public static final String CAR_TABLE_NAME = "rental-car";
//    public static final String CAR_TABLE_MODEL_INDEX = "model-index";
    public static final String CAR_TABLE_ATTR_ID = "id";
    public static final String CAR_TABLE_ATTR_MODEL = "model";
    public static final String CAR_TABLE_ATTR_AVAILABILITY = "availability";
    public static final String CAR_TABLE_ATTR_TRANSACTION_ID = "transactionId";


    public static final String TRANSACTION_TABLE_NAME = "rental-transaction";
    public static final String TRANSACTION_TABLE_STATE_INDEX = "open-transaction-index";
    public static final String TRANSACTION_TABLE_ATTR_ID = "id";
    public static final String TRANSACTION_TABLE_ATTR_USER_ID = "userId";
    public static final String TRANSACTION_TABLE_ATTR_STATE = "transactionState";
    public static final String TRANSACTION_TABLE_ATTR_CAR_ID = "carId";
    public static final String TRANSACTION_TABLE_ATTR_OPEN_AT = "openAt";
    public static final String TRANSACTION_TABLE_ATTR_CLOSE_AT = "closeAt";


    public TableConstValue() {
        throw new IllegalStateException("Util class");
    }
}

aws dynamodb delete-table \
    --endpoint-url http://192.168.24.120:8000 \
    --table-name rental-transaction


aws dynamodb create-table \
    --endpoint-url http://192.168.24.120:8000 \
    --table-name rental-transaction \
    --attribute-definitions AttributeName=userId,AttributeType=S AttributeName=id,AttributeType=S AttributeName=transactionState,AttributeType=S\
    --key-schema AttributeName=userId,KeyType=HASH AttributeName=id,KeyType=RANGE \
    --provisioned-throughput ReadCapacityUnits=3,WriteCapacityUnits=3 \
    --tags Key=product,Value=rental-car-demo \
    --local-secondary-indexes \
      "[
         {
             \"IndexName\": \"open-transaction-index\",
             \"KeySchema\": [
                 {
                     \"AttributeName\": \"userId\",
                     \"KeyType\": \"HASH\"
                 },
                 {
                    \"AttributeName\": \"transactionState\",
                     \"KeyType\": \"RANGE\"
                 }
             ],
             \"Projection\": {
                 \"ProjectionType\": \"KEYS_ONLY\"
             }
         }
     ]"
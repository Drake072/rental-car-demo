aws dynamodb delete-table \
    --endpoint-url http://192.168.24.120:8000 \
    --table-name rental-user


aws dynamodb create-table \
    --endpoint-url http://192.168.24.120:8000 \
    --table-name rental-user \
    --attribute-definitions AttributeName=email,AttributeType=S AttributeName=id,AttributeType=S \
    --key-schema AttributeName=id,KeyType=HASH \
    --provisioned-throughput ReadCapacityUnits=5,WriteCapacityUnits=5 \
    --tags Key=product,Value=rental-car-demo \
    --global-secondary-indexes \
      "[
         {
             \"IndexName\": \"email-index\",
             \"KeySchema\": [
                 {
                     \"AttributeName\": \"email\",
                     \"KeyType\": \"HASH\"
                 }
             ],
             \"Projection\": {
                 \"ProjectionType\": \"ALL\"
             },
             \"ProvisionedThroughput\": {
                 \"ReadCapacityUnits\": 5,
                 \"WriteCapacityUnits\": 5
             }
         }
     ]"


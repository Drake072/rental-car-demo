aws dynamodb delete-table \
    --endpoint-url http://192.168.24.120:8000 \
    --table-name rental-car

aws dynamodb create-table \
    --endpoint-url http://192.168.24.120:8000 \
    --table-name rental-car \
    --attribute-definitions AttributeName=id,AttributeType=N  \
    --key-schema AttributeName=id,KeyType=HASH \
    --provisioned-throughput ReadCapacityUnits=10,WriteCapacityUnits=10 \
    --tags Key=product,Value=rental-car-demo


aws dynamodb put-item --table-name rental-car --item \
    "
      {
          \"id\": {\"N\": \"1\"},
          \"model\": {\"S\": \"CAMRY\"},
          \"availability\": {\"S\": \"IN_STOCK\"}
      }
    "
aws dynamodb put-item --table-name rental-car --item \
    "
      {
          \"id\": {\"N\": \"2\"},
          \"model\": {\"S\": \"CAMRY\"},
          \"availability\": {\"S\": \"IN_STOCK\"}
      }
    "

aws dynamodb put-item --table-name rental-car --item \
    "
      {
          \"id\": {\"N\": \"3\"},
          \"model\": {\"S\": \"BMW650\"},
          \"availability\": {\"S\": \"IN_STOCK\"}
      }
    "

aws dynamodb put-item --table-name rental-car --item \
    "
      {
          \"id\": {\"N\": \"4\"},
          \"model\": {\"S\": \"BMW650\"},
          \"availability\": {\"S\": \"IN_STOCK\"}
      }
    "

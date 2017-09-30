#!/bin/bash
LAMBDA_FUNCTION=$1

mvn -f ./$LAMBDA_FUNCTION clean package
JAR=$LAMBDA_FUNCTION-1.0-SNAPSHOT.jar

echo "updating lambda function..."
aws lambda update-function-code \
    --function-name $LAMBDA_FUNCTION \
    --zip-file fileb://./$LAMBDA_FUNCTION/target/$JAR

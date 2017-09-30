#!/bin/bash

BUCKET_NAME=$1

aws s3 cp --recursive MyLambdaFunction/testfiles/ s3://$BUCKET_NAME/

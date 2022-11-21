#!/bin/bash

echo "############## Iniciando #############"

hostname=http://localhost:4566

echo "############## Criando SNS #############"

aws sns create-topic --endpoint-url $hostname --name votacao-pauta --region us-east-1 --profile localstack

echo "############## Concluindo start #############"
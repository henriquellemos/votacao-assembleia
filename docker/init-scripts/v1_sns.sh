#!/bin/bash

echo "############## Executando script SNS #############"

for script_name in /config/*.sh; do
  bash $script_name
done

echo "############## Finalizando execução de Script SNS #############"
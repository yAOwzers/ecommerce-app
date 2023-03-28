#!/bin/zsh

set -e

kubectl_files=(
  "api-gateway/api-gateway.yaml"
  "product-service/product-service.yaml"
  "order-service/order-service.yaml"
  "inventory-service/inventory-service.yaml"
  "discovery-server/discovery-server.yaml"
)

for kubectl_file in "${kubectl_files[@]}"
do
  kubectl apply -f "$kubectl_file"

  if [ $? -eq 0 ]
  then
    echo "kubectl apply of $kubectl_file was successful!"
  else
    echo "FAILED apply of $kubectl_file"
  fi
done


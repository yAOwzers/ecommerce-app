#!/bin/zsh


set -e

kubectl apply -f https://github.com/kubernetes-sigs/metrics-server/releases/latest/download/components.yaml

if [ $? -eq 0 ];
then
  echo "Successful Application of metric server"
else
  echo "ERROR WITH SETTING UP METRIC SERVER"
fi
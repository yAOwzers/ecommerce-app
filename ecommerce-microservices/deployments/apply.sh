#!/bin/zsh

# Loop through all arguments
for arg in "$@"
do
    # Apply the deployment
    kubectl apply -f "${arg}.yaml"

    if [ $? -eq 0 ]
    then
      echo "Applied deployment for ${arg}"
    else
      echo "Application failed for ${arg}"
    fi
done

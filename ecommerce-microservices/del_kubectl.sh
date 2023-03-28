#!/bin/zsh

set -e

# Loop through all arguments
for arg in "$@"
do
    # Delete the deployment
    kubectl delete deployment "${arg}"

    # Check the exit code
    if [ $? -eq 0 ]; then
        echo "Deleted deployment for ${arg}"
    else
        echo "Failed to delete deployment for ${arg}"
    fi

    # Delete the service
    kubectl delete service "${arg}"

    # Check the exit code
    if [ $? -eq 0 ]; then
        echo "Deleted service for ${arg}"
    else
        echo "Failed to delete service for ${arg}"
    fi
done




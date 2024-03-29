#!/bin/zsh

set -e 

# Check that two command-line arguments were provided
if [ $# -ne 2 ]; then
  echo "Usage: $0 <total_requests> <concurrent_num>"
  exit 1
fi

# Set the variables based on the command-line arguments
total_request=$1
concurrent_num=$2

# Set the name of the JSON file to use
DUMMY="dummy_payload"

# Read the contents of the JSON file into a variable
json_data="$(cat ${DUMMY}.json)"

# Call the `hey` command with the variables and fixed file name
hey_output=$(hey -n "$total_request" -c "$concurrent_num" -m POST -d "$json_data" -H "Content-Type: application/json" http://localhost:8080/api/order)

# If the response file doesn't exist, create it and add headers
if [ ! -f response.txt ]; then
  echo "HEY OUTPUT:" > response.txt
fi

# Append hey output to response.txt file
echo "$hey_output" >> response.txt

# Print hey output to terminal
echo "$hey_output"

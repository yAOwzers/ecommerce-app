#!/bin/zsh

# Set the name of the CSV file
file_name="pod_metrics"

# Loop 3 times with 3 second delay between each iteration
for i in {1..3}
do
  # Run kubectl top command and save output to variable
  top_output=$(kubectl top pods --no-headers | awk '{print $1","$2","$3}')

  # If the CSV file doesn't exist, create it and add headers
  if [ ! -f "$file_name.csv" ]; then
    echo "NAME,CPU(cores),MEMORY(bytes)" >> "$file_name.csv"
  fi

  # Append output to CSV file
  echo "$top_output" >> "$file_name.csv"

  sleep 3
done

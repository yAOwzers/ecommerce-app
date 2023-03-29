#!/bin/zsh
# Set the name of the TXT file
file_name="pod_metrics"

# Loop 3 times with 3 second delay between each iteration
for i in {1..6}
do
  # Run kubectl top command and save output to variable
  top_output=$(kubectl top pods --no-headers | awk '{printf "%-40s | %-12s | %-12s\n", $1, $2, $3}')

  # If the TXT file doesn't exist, create it and add headers
  if [ ! -f "$file_name.txt" ]; then
    echo "NAME                                     | CPU(cores)   | MEMORY(bytes)" >> "$file_name.txt"
  fi

  # Append output to TXT file
  echo "$top_output" >> "$file_name.txt"
  echo "---------------------------------------- | ------------ | ------------" >> "$file_name.txt"

  # Print output to terminal
  echo "$top_output"
  echo "---------------------------------------- | ------------ | ------------"

  sleep 5
done





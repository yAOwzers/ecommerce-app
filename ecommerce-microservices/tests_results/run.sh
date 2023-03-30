#!/bin/zsh

set -e

# Run load_test in the background
./load_test.sh "$1" "$2" &

# Wait for a few seconds
sleep 2

# Call top.sh
./top.sh

#!/bin/zsh

set -e

nodeGroupYaml="nodegroup.yaml"

eksctl create nodegroup --config-file="$nodeGroupYaml"

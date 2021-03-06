#!/bin/bash
set -e
scp  -o StrictHostKeyChecking=no -r ./.env ./docker-compose.staging.yml ubuntu@$EC2_PUBLIC_IP_ADDRESS:/home/ubuntu/TradeProject/client-connection
ssh -o StrictHostKeyChecking=no ubuntu@$EC2_PUBLIC_IP_ADDRESS << 'ENDSSH'
  cd /home/ubuntu/TradeProject/client-connection
  export $(cat ./.env | xargs)
  echo "$DOCKER_PASSWORD" | docker login -u "$DOCKER_USERNAME" --password-stdin
  docker pull elijahahianyo/trade-project:client-connection-prod
  docker-compose -f docker-compose.staging.yml  up -d
ENDSSH


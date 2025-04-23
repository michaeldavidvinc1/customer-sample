#!/bin/bash
ssh -p "${SERVER_PORT}" "${SERVER_USERNAME}"@"${SERVER_HOST}" -i key.txt -t -t -o StrictHostKeyChecking=no << 'ENDSSH'
mkdir -p ~/customer-app
cd ~/customer-app
set +a
source .env
start=$(date +"%s")
if [ "$(docker ps -q -f name=$CONTAINER_NAME)" ]; then
    echo "Container is running -> stopping it..."
    docker system prune -af
    docker stop docker-logs;
    docker stop $CONTAINER_NAME;
    docker rm docker-logs
    docker rm $CONTAINER_NAME
fi

docker login $CLOUDRAYA_REGISTRY_URL -u $CLOUDRAYA_REGISTRY_USERNAME -p $CLOUDRAYA_REGISTRY_PASSWORD
docker run -d --restart unless-stopped --platform linux/amd64 -p $APP_SERVER_PORT:$APP_SERVER_PORT --env-file .env --name $CONTAINER_NAME  $CLOUDRAYA_REGISTRY_URL/$CONTAINER_NAME:$IMAGE_TAG
exit
ENDSSH

if [ $? -eq 0 ]; then
  exit 0
else
  exit 1
fi

end=$(date +"%s")

diff=$(($end - $start))

echo "Deployed in : ${diff}s"
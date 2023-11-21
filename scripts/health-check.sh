CURRENT_PORT=$(cat /home/ubuntu/service_url.inc | grep -Po '[0-9]+' | tail -1)
TARGET_PORT=0

if [ ${CURRENT_PORT} -eq 8081 ]; then
    TARGET_PORT=8082
elif [ ${CURRENT_PORT} -eq 8082 ]; then
    TARGET_PORT=8081
else
    echo "> No WAS is connected to nginx"
    exit 1
fi

echo "> Start health check of WAS at 'http://127.0.0.1:${TARGET_PORT}' ..."
for RETRY_COUNT in {1..10}
do
  RESPONSE=$(curl -s http://localhost:$TARGET_PORT/actuator/health)
  UP_COUNT=$(echo $RESPONSE | grep 'UP' | wc -l)

  if [ $UP_COUNT -ge 1 ]
  then
      echo "> Health check success"
      break
  else
      echo "> Health check response is unknown or status is not UP"
      echo "> Health check: ${RESPONSE}"
  fi

  if [ $RETRY_COUNT -eq 10 ]
  then
    echo "> Health check failed"
    echo "> Exit the deployment without connecting to Nginx"
    exit 1
  fi

  echo "> Health check connection failed. Retry..."
  sleep 10
done
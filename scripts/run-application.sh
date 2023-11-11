sudo pkill -6 java
source /home/ubuntu/.env
REPOSITORY=/home/ubuntu/yourssu_project
JAR_NAME=$(ls $REPOSITORY/build/libs/ | grep 'SNAPSHOT.jar' | tail -n 1)
JAR_PATH=$REPOSITORY/build/libs/$JAR_NAME
nohup java -jar $JAR_PATH 1>>/home/ubuntu/log/spring-log.log 2>>/home/ubuntu/log/spring-error.log &
sudo pkill -6 java
source /home/ubuntu/.env
nohup java -jar /home/ubuntu/yourssu_project/build/libs/*.jar 1>>/home/ubuntu/log/spring-log.log 2>>/home/ubuntu/log/spring-error.log &
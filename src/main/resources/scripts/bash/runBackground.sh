mkdir -p Logs
now=$(date +"%m_%d_%Y")
nohup java -jar target/Raspberry-furnace-temp-0.0.1-SNAPSHOT.jar > Logs/log_$now.log&

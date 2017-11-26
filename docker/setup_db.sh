#!/bin/bash
USER=root
PASSWORD=password
HOST=127.0.0.1
PORT=3306
LOGIN_PATH=docker
SQL_MODE=ONLY_FULL_GROUP_BY,STRICT_TRANS_TABLES,NO_ZERO_IN_DATE,ERROR_FOR_DIVISION_BY_ZERO,NO_AUTO_CREATE_USER,NO_ENGINE_SUBSTITUTION

nohup \docker-entrypoint.sh mysqld --sql-mode=$SQL_MODE --secure-file-priv="" &

echo "Waiting for mysql"
until mysql -h"$HOST" -P"$PORT" -uroot -p"$PASSWORD" &> /dev/null
do
  printf "."
  sleep 1
done

#setup "docker" login path
echo -e "$PASSWORD\n" | mysql_config_editor set --login-path=$LOGIN_PATH --host=$HOST -P$PORT --user=$USER --password

#run population scripts
./setup.sh $LOGIN_PATH

# Infinite cycle just to avoid container exit, because exit will occur when the main process will end
# ignoring another processes notification
while :
do
	sleep 10
done
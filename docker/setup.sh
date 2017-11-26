#!/bin/bash
declare loginpath=$1
declare multitenant=cinemapardy
declare MULTITENANT_FOLDER=/

echo "Creating schemas...."
mysql --login-path=$loginpath < create_schemas.sql
echo "Done."

echo "Creating Cinema database...."
for file in `find $MULTITENANT_FOLDER -name "v*.sql" | sort -t _ -nk 1.4`
do
  echo "Apply script $file..."
  mysql --login-path=$loginpath --database=$multitenant < $file
done
echo "Done."

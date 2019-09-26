#!/bin/sh
docker run -p 3306:3306 --name gt-db -e MYSQL_ROOT_PASSWORD=gtdb -d mysql
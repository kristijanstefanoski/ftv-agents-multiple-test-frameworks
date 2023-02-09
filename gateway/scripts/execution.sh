#!/bin/sh

## The variables are set in the docker-compose file. You can run in as well calling the
## docker run --env-file=FILE ...
# SL_TOKEN=token
# SL_LAB_ID=a
# SL_BUILD_NAME=b
# SL_BRANCH_NAME=c
# SL_APPNAME=d

CMD=$1

echo "java -jar sl-test-listener.jar $CMD -token $SL_TOKEN -testStage \"Man Stage\" -labid $SL_LAB_ID"

java -jar sl-test-listener.jar "$CMD" -token $SL_TOKEN -testStage "Man Stage" -labid "$SL_LAB_ID"
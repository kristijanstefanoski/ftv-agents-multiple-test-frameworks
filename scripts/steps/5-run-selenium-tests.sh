#!/bin/sh

LAB_ID=labId=`jq -r '.labId' ../slLabData.json`

cd ../../selenium-runner

PATTERN="s/<labId>\([a-zA-Z0-9_]\)*<\/labId>/<labId>$LAB_ID<\/labId>/g"
sed -i $PATTERN pom.xml

mvn clean test
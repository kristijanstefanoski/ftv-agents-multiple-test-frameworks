#!/bin/sh

if [ $# -lt 1 ]; then
    >&2
    echo
    echo "   LAB_ID must be provided. Provide the lab_id that was created in the backend application."
    echo
    exit 1
fi

LAB_ID=$1

echo "Modifying the test POM"

cd ../../selenium-runner

PATTERN="s/<labId>\([a-zA-Z0-9_]\)*<\/labId>/<labId>$LAB_ID<\/labId>/g"
sed -i $PATTERN pom.xml

mvn clean test
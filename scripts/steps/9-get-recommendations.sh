#!/bin/sh
bsid=`cat ../integrationBSID`
apiToken=`cat ../../sltoken.txt`
curl --location --request GET 'https://dev-kristijan-gw.dev.sealights.co/sl-api/v1/tia/builds/'"$bsid"'/test-stages/Gauge%20Tests/recommendations' --header 'Authorization: Bearer '"$apiToken" --header 'Content-Type: application/json' | json_pp
git checkout origin/main -- ../../calculator/src/main/java/i0/sealights/demo/calculator/service/CalculatorService.java
git checkout origin/main -- ../../parameters.env
git checkout origin/main -- ../../parameters_calculator.env
git checkout origin/main -- ../../gateway/src/main/resources/static/assets

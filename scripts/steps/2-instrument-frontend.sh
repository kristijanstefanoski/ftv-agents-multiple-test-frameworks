#!/bin/sh
cd ../../frontend && npm i && npm run build
buildName=`jq -r '.buildName' ../scripts/slLabData.json`
labId=`jq -r '.labId' ../scripts/slLabData.json`
npx slnodejs config --tokenfile ../sltoken.txt --appName "React Calculator" --branch "master" --build $buildName
npx slnodejs scan --workspacepath ./dist --tokenfile ../sltoken.txt --buildsessionidfile buildSessionId --labid $labId --scm none --instrumentForBrowsers --enableOpenTelemetry --outputpath "sl_web"
npm run merge && npm run deploy
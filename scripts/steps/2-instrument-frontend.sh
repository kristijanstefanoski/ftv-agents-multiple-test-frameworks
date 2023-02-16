#!/bin/sh
sudo docker stop sl_collector || true
sudo docker run -it --rm --name sl_collector -d -v $(pwd)/config.yaml:/collector/config.yaml -p 80:80 -p 16500:16500 -p 8080:8080 sealights/on-prem-collector:v0.10.5
cd ../../frontend && npm i && npm run build
buildName=`jq -r '.buildName' ../scripts/slLabData.json`
labId=`jq -r '.labId' ../scripts/slLabData.json`
npx clear-npx-cache
npx slnodejs config --tokenfile ../sltoken.txt --appName "React Calculator" --branch "master" --build $buildName
npx slnodejs scan --workspacepath ./dist --tokenfile ../sltoken.txt --buildsessionidfile buildSessionId --labid $labId --scm none --instrumentForBrowsers --enableOpenTelemetry --collectorUrl http://localhost:16500/api --outputpath "sl_web"
npm run merge && npm run deploy

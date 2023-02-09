#!/bin/sh
cp ../java-rerun/CalculatorService-replace.java ../../calculator/src/main/java/i0/sealights/demo/calculator/service/CalculatorService.java
cd ../js-scripts && node readAndReplace.js && cd ../..
sudo docker-compose down
sudo docker builder prune
sudo docker-compose build
sudo docker-compose up
#!/bin/sh
cd ../js-scripts || exit
npm i
node createLab.js --appName="ftv-demo-playwright-02"
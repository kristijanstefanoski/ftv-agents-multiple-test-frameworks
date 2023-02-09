# Simple mathematical Calculator/Evaluator

## Install dependencies
```shell
npm install
```
## Running for development
```shell
npm run dev
```
## Build for production
```shell
npm run build
```

## Scanning and instrumenting with OpenTelemetry enabled
First build the application with:
```shell
npm run build
```
In order to run this demo, you need a valid Agent Token stored in a file ex. `sltoken.txt`.
Then you can proceed to run `slnodejs` config command to generate a new `build session id`:
```shell
npx slnodejs config --tokenfile sltoken.txt --appName "React Calculator" --branch "master" --build 2.0.0
```
If this command ran successfully you should have `buildSessionId` file in the same folder and can continue to scan the build:
```shell
npx slnodejs scan --workspacepath ./dist --tokenfile sltoken.txt --buildsessionidfile buildSessionId --scm none --instrumentForBrowsers --enableOpenTelemetry --outputpath "sl_web"
```
**IMPORTANT**: Make sure you are running `slnodejs >= 6.1.278` with `npx`, to clear cache use `npx clear-npx-cache`.

After a successful build can you should have a resulting `sl_web` folder.

Run
```shell
npm run merge && npm run deploy
```
**IMPORTANT: Run the above command first before running `docker-compose up --build`** as this
will place the build files in the gateway (the gateway serves the project).

**IMPORTANT:** Do not delete `sltoken.txt` after the build scan, it is used in the Gauge tests.

## Running gauge tests
Make sure you have [gauge](https://docs.gauge.org/getting_started/installing-gauge.html?os=linux&language=javascript&ide=vscode) installed
on your system.

```shell
npm run test
```
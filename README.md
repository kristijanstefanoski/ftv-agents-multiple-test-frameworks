# Gauge + FTV Java agents demo

## TL;DR
Add your token in the root directory in a file `sltoken.txt` and fill in/replace the values in 
`parameters.env` and `parameters_calculator.env`.
Change directory into `/scripts/steps`.
Run the scripts one by one:
```shell
./1-create-lab.sh # Will create a new Lab and save it's details, like labId
./2-instrument-frontend.sh # Configure and instrument the frontend React application
./3-deploy-services.sh # Deploys all backend services using docker-compose, wait for services to be up and running
./4-add-frontend-component.sh # Creates a new Build Session Id by adding the frontend application to the integration build
./5-run-tests.sh # Runs the Gauge tests
# Step 6 - manually turn TIA on from your Sealights Dashboard
./7-change-backend-calculator-service.sh # Will make a small change in the Calculator service and redeploy, wait for it to be up and running again
./8-add-frontend-component.sh # Same as #4
./9-get-recommendations.sh # Get test recommendations using the API

```

## Running backend services and frontend manually TL;DR

To run the backend services, execute from the command line:

```shell
docker-compose up --build
```

Wait till the servers will be build and docker will run

To run the frontend application, see the [forontend/README.md](frontend/README.md):

## The example backend requests

Execute `curl` calls form the command line:

```shell
curl -X GET --location "http://localhost:9080/api/sum/geometric?first=1&ratio=0.5&count=5"

curl -X GET --location "http://localhost:9080/api/evaluate/3*7"
    
curl -X GET --location "http://localhost:9080/api/evaluate/(3*7+4)*0.2"
    
curl -X GET --location "http://localhost:9080/api/evaluate/3*cos(2*3.141592653589793238)"

curl -X GET --location "http://localhost:9080/api/evaluate/unknowFunction(2)"
```

Close running containers with

```shell
docker-compose down
```


## Prepare and run the applications

Run the following instructions to launch backend application running in the containers

### The Calculator application
Form the command line type

```shell
cd calculator
docker build -t sealights/backend-demo-calculator .
docker run -it  -p 9081:8080 sealights/backend-demo-calculator
```

Then you will be able to perform the calculations:

```shell
curl -X GET --location "http://localhost:9081/evaluate/(3*7+4)*0.25"

> {"result":6.25}
```

```shell
curl -X GET --location "http://localhost:9081/evaluate/3*cos(2*3.141592653589793238)"

> {"result":3.0}
```

```shell
curl -X GET --location "http://localhost:9081/evaluate/3*cos(2*3.141592653589793238)"

> {"result":3.0}
```

```shell
curl -X GET --location "http://localhost:9081/evaluate/tg(0)"

> {"result":"Invalid math exception: ' unknownFunction(127) '"}
```

### The Summator application

```shell
cd summator
docker build -t sealights/backend-demo-summator .
docker run -it  -p 9082:8080 sealights/backend-demo-summator
```

The summator application calculates the finite sum of the geometric series. Result are available under the endpoint URL http://<host>:<post>/sum/geometric.

Required parameters are:
- `first` - the first element of the series
- `ratio` - common ratio of the series
- `count` - number of terms to sum

#### Examples

```shell
curl -X GET --location "http://localhost:9082/sum/geometric?first=1&ratio=2&count=4"

> {"result":15.0}
```

```shell
curl -X GET --location "http://localhost:9082/sum/geometric?first=1&ratio=0.5&count=4"

> {"result":1.875}
```

Error responses

```shell
curl -X GET --location "http://localhost:9082/sum/geometric?first=1&ratio=1&count=4"

> HTTP/1.1 409
> {"result":"The ratio can not be 1.0"}
```

```shell
curl -X GET --location "http://localhost:9082/sum/geometric?first=1&ratio=1"

> HTTP/1.1 400
> {"result":"Missing required argument: 'count'"}
```

### The Gateway application

The gateway application is a proxy to Summator and Calsulator. It exposes the same API except that all the URLs are prefixed with the `/api` char sequence.

Form the command line type

```shell
cd calculator
docker build -t sealights/backend-demo-gateway .
docker run -it  -p 9080:8080 sealights/backend-demo-gateway
```


# A collection of scripts to help automate the demo flow


### 1. Creating a new lab using the Public API
```shell
node createLab.js --appName="Test App" --branchName="master" --testEnv="New Test Env" --exportFileName='slLabId.txt'
```
| Option           | Details                                                                                               |
|------------------|-------------------------------------------------------------------------------------------------------|
| --appName        | The app name to use                                                                                   |
| --branchName     | The name of the branch                                                                                |
| --testEnv        | Which test environment                                                                                |
| --exportFileName | The name of the file to export to (without extension, JSON is default)                                |
| --replace        | Default: `true` - Replaces the values in `parameters.env` file which is used for the backend services |
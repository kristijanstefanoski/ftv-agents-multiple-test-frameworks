const fs = require("fs");
const axios = require("axios");
const jwtDecode = require("jwt-decode");
const { exec } = require("child_process");

const DEFAULT_BRANCH = "master";
const DEFAULT_TEST_ENV = "testEnv";
const DEFAULT_LAB_ID_FILE_EXPORT_NAME = "slLabData";

const argv = require("minimist")(process.argv.slice(2));
const { appName, replace } = argv;
if (!appName?.length) {
  throw new Error('ERROR: Required argument "appName" not provided.');
}

const apiToken = fs.readFileSync(`${__dirname}/../../sltoken.txt`, "utf-8");

const decoded = jwtDecode(apiToken);
const baseUrl = decoded["x-sl-server"].replace("api", "sl-api");

const createLabRoute = `/v1/agent-apis/lab-ids`;

const branchName = argv.branchName ?? DEFAULT_BRANCH;
const testEnv = argv.testEnv ?? DEFAULT_TEST_ENV;

(async () => {
  const { data } = await axios.post(
    `${baseUrl}${createLabRoute}`,
    {
      appName,
      branchName,
      isHidden: false,
      isFtv: true,
    },
    {
      headers: {
        Authorization: `Bearer ${apiToken}`,
      },
    }
  );

  const labId = data.data.labId;
  const fileExport = argv.exportFileName ?? DEFAULT_LAB_ID_FILE_EXPORT_NAME;
  console.log(labId);
  console.log(`Successfully created lab with labId: ${labId}`);
  console.log(`Successfully saved labId to file: ${fileExport}.json`);
  exec(`date +"%y%m%d_%H%M"`, (error, stdout, stderr) => {
    fs.writeFileSync(
      `${__dirname}/../${fileExport}.json`,
      JSON.stringify(
        {
          labId,
          appName,
          branchName,
          testEnv,
          buildName: `build_${stdout.replace(/[\n\t\r]/g, "")}`,
        },
        null,
        4
      )
    );
    if (replace !== "false") {
      let parametersEnv = fs
        .readFileSync(`${__dirname}/../../parameters.env`)
        .toString();
      parametersEnv = parametersEnv
        .replace(new RegExp(/(?<=SL_LAB_ID=).*/gm), labId)
        .replace(new RegExp(/(?<=SL_BRANCH_NAME=).*/gm), branchName)
        .replace(new RegExp(/(?<=SL_APPNAME=).*/gm), appName)
        .replace(
          new RegExp(/(?<=SL_BUILD_NAME=).*/gm),
          `build_${stdout.replace(/[\n\t\r]/g, "")}`
        );
      fs.writeFileSync(`${__dirname}/../../parameters.env`, parametersEnv);
      fs.writeFileSync(
        `${__dirname}/../../parameters_calculator.env`,
        parametersEnv
      );
    }
  });

  return labId;
})();

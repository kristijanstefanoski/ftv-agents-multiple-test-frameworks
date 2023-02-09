const axios = require("axios");
const jwtDecode = require("jwt-decode");
const fs = require("fs");
const { exec } = require("child_process");

const apiToken = fs.readFileSync(`${__dirname}/../sltoken.txt`, "utf-8");

const decoded = jwtDecode(apiToken);
const baseUrl = decoded["x-sl-server"].replace("api", "sl-api");

const buildData = JSON.parse(fs.readFileSync("./slLabData.json").toString());
const bsid = fs.readFileSync("../frontend/buildSessionId").toString();

const createBSIDRoute = `/v1/agent-apis/lab-ids/${buildData.labId}/integration-build`;

(() => {
  exec(`date +"%y%m%d_%H%M"`, async (error, stdout, stderr) => {
    try {
      const { data } = await axios.post(
        `${baseUrl}${createBSIDRoute}`,
        {
          buildName: `integ_build_${stdout}`,
          forceFailedComponents: true,
          additionalComponents: [
            {
              bsid,
              appName: "React Calculator",
              branchName: buildData.branchName,
              buildName: buildData.buildName,
            },
          ],
        },
        {
          headers: {
            Authorization: `Bearer ${apiToken}`,
          },
        }
      );

      fs.writeFileSync(
        `${__dirname}/integrationBSID`,
        data.data.buildSessionId
      );
    } catch (e) {
      console.error(e);
    }
  });
})();

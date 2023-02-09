const axios = require("axios");
const jwtDecode = require("jwt-decode");
const fs = require("fs");

const apiToken = fs.readFileSync(`${__dirname}/../../sltoken.txt`, "utf-8");

const decoded = jwtDecode(apiToken); // Agent Token
const baseUrl = decoded["x-sl-server"]; // Base url of the backend

const testSessionsV1Instance = axios.create({
  baseURL: baseUrl.replace("/api", "/sl-api/v1/test-sessions"),
  headers: {
    Authorization: `Bearer ${apiToken}`,
  },
});

const testSessionsV2Instance = axios.create({
  baseURL: baseUrl.replace("/api", "/sl-api/v2/test-sessions"),
  headers: {
    Authorization: `Bearer ${apiToken}`,
  },
});

const bsId = fs
  .readFileSync(`${__dirname}/../../scripts/integrationBSID`)
  .toString();
const labData = JSON.parse(
  fs.readFileSync(`${__dirname}/../../scripts/slLabData.json`).toString()
);
module.exports = {
  createTestSession: async () => {
    const sessionData = {
      testStage: "Gauge Tests",
      labId: labData.labId,
      bsId,
    };
    console.log(baseUrl);
    console.log(`Starting test session with data:`, sessionData);
    const { data } = await testSessionsV1Instance.post("/", sessionData);
    return data;
  },
  endTestSession: (testSessionId) => {
    return testSessionsV1Instance.delete(`/${testSessionId}`);
  },
  sendTestEvent: (testSessionId, name, start, end, status) => {
    return testSessionsV2Instance.post(`/${testSessionId}`, [
      {
        name,
        start,
        end,
        status,
      },
    ]);
  },
};

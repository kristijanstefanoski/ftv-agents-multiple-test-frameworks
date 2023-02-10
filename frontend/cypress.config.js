const { defineConfig } = require("cypress");
const fs = require("fs");

module.exports = defineConfig({
  e2e: {
    testIsolation: false,
    setupNodeEvents(on, config) {
      on("task", {
        readSealightsConfig() {
          const buildSessionId = fs.readFileSync(
            `${__dirname}/../scripts/integrationBSID`,
            "utf-8"
          );
          const labData = JSON.parse(
              fs.readFileSync(`${__dirname}/../scripts/slLabData.json`).toString()
          );
          const apiToken = fs.readFileSync(`${__dirname}/../sltoken.txt`, "utf-8");
          return { buildSessionId, apiToken, labId: labData.labId };
        },
      });
    },
  },
});

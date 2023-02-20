const SLService = require("../tests/sealightsService");

module.exports = async () => {
  // Start a test session
  const { testSessionId } = (
    await SLService.createTestSession("Playwright Tests")
  ).data;
  process.env.testSessionId = testSessionId;
};

const SLService = require("../services/sealightsService");
const jwtDecode = require("jwt-decode").default;

let testSession;
let testStartTime;

describe("Calculator app tests", async () => {
  before(() => {
    cy.visit("http://localhost:9080");

    cy.task("readSealightsConfig").then(
      async ({ buildSessionId, apiToken, labId }) => {
        const decoded = jwtDecode(apiToken); // Agent Token
        const baseUrl = decoded["x-sl-server"]; // Base url of the backend
        SLService.setConfig(baseUrl, apiToken, buildSessionId, labId);

        // Start a test session
        const { testSessionId } = (await SLService.createTestSession()).data;
        testSession = testSessionId;
      }
    );
  });

  beforeEach(() => {
    cy.window().then((win) => {
      // Set the correct baggage before a scenario runs with testName and the current testSessionId
      const testName = Cypress.currentTest.title;
      const customEvent = new CustomEvent("set:baggage", {
        detail: {
          "x-sl-test-name": testName,
          "x-sl-test-session-id": testSession,
        },
      });
      win.dispatchEvent(customEvent);
      testStartTime = Date.now();
    });
  });

  afterEach(async function () {
    const testState = this.currentTest.state;
    const testName = this.currentTest.title;
    // Send test event to Sealights
    await SLService.sendTestEvent(
      testSession,
      testName,
      testStartTime,
      Date.now(),
      testState
    );
    testStartTime = undefined;

    cy.window().then((win) => {
      // Unset baggage after scenario
      const customEvent = new CustomEvent("delete:baggage");
      win.dispatchEvent(customEvent);
    });
  });

  after((done) => {
    // End the current test session after the running suite using Sealights Public API
    SLService.endTestSession(testSession).finally(() => {
      cy.window().then(async (win) => {
        win.$SealightsAgent.sendAllFootprints().finally(done);
      });
    });
  });

  it("Sums two numbers", () => {
    cy.get("#expression").type("5+5");
    cy.get("#evaluateSimple").click();

    cy.get(".Toastify__toast-body").should("contain.html", 10);
  });

  it("Summator test", () => {
    cy.get("[aria-label='close']").click();
    cy.get("#first").type(1);
    cy.get("#ratio").type(2);
    cy.get("#count").type(4);
    cy.get("#evaluateComplex").click();

    cy.get(".Toastify__toast-body").should("contain.html", 15);
  });
});

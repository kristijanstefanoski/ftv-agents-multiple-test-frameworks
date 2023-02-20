const { test, expect } = require("@playwright/test");
const SLService = require("./sealightsService");

let testStartTime;

test.beforeEach(async ({ page }, testInfo) => {
  // Capture and output logs from browser console
  page.on("console", (msg) => console.log(msg.text()));

  const title = testInfo.title;
  await page.evaluate(
    ({ title, testSession }) => {
      const customEvent = new CustomEvent("set:baggage", {
        detail: {
          "x-sl-test-name": title,
          "x-sl-test-session-id": testSession,
        },
      });
      window.dispatchEvent(customEvent);
    },
    { title, testSession: process.env.testSessionId }
  );
  await page.goto("http://localhost:9080");
  testStartTime = Date.now();
});

test.afterEach(async ({ page }, testInfo) => {
  // Unset baggage after scenario
  await page.evaluate(() => {
    const customEvent = new CustomEvent("delete:baggage");
    window.dispatchEvent(customEvent);
  });
  // Send test event to Sealights
  const { title, status } = testInfo;
  console.log(
    process.env.testSessionId,
    title,
    testStartTime,
    Date.now(),
    status
  );

  await SLService.sendTestEvent(
    process.env.testSessionId,
    title,
    testStartTime,
    Date.now(),
    status
  );
  testStartTime = undefined;
});

test.afterAll(async ({ page }) => {
  // Submit all footprints
  await page.evaluate(async () => {
    await window.$SealightsAgent.sendAllFootprints();
  });
});

test("Sum two numbers", async ({ page }) => {
  await page.locator("#expression").fill("5+3");
  await page.locator("#evaluateSimple").click();
  await expect(page.locator(".Toastify__toast-body")).toContainText("8");
});

test("Test summator", async ({ page }) => {
  await page.locator("#first").fill("1");
  await page.locator("#ratio").fill("2");
  await page.locator("#count").fill("4");
  await page.locator("#evaluateComplex").click();
  await expect(page.locator(".Toastify__toast-body")).toContainText("15");
});

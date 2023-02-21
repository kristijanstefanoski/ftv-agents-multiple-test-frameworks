package i0.sealights.selenium.steps;

import static org.junit.Assert.assertEquals;

import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

public class FiniteSumSteps extends BaseSteps {

    @Given("^Enter sum expression evaluator page$")
    public void enterSumExpressionEvaluatorPage() {
        waitForWhile(1_000);
    }

    @When("^Input the values of first \"([^\"]*)\", ratio \"([^\"]*)\" and count \"([^\"]*)\"$")
    public void inputTheValuesOfFirstFirstRatioRatioAndCountCount(String first, String ratio, String count) {
        // when - set the field values
        final WebElement firstInput = driver.findElement(By.id("first"));
        final WebElement ratioInput = driver.findElement(By.id("ratio"));
        final WebElement countInput = driver.findElement(By.id("count"));
        final WebElement evaluateComplexButton = driver.findElement(By.id("evaluateComplex"));

        firstInput.sendKeys(first);
        ratioInput.sendKeys(ratio);
        countInput.sendKeys(count);

        // and submit the form
        evaluateComplexButton.submit();

        waitForWhile(2_000);
    }

    @Then("^The sum result should be (\\d+)$")
    public void theSumResultShouldBeResult(int result) {
        final WebElement responseToast = driver.findElement(By.cssSelector("div.Toastify__toast-body div + div"));

        String actualResultText = responseToast.getText();

        // then
        assertEquals("The result of the summation is: " + result, actualResultText);
    }
}

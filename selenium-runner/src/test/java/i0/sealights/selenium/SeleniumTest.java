package i0.sealights.selenium;

import static i0.sealights.selenium.Properties.CHROME_DRIVER_LOCATION;
import static org.junit.Assert.assertEquals;

import java.util.concurrent.TimeUnit;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.remote.RemoteWebDriver;

public class SeleniumTest {

    private static ChromeDriver driver;

    static {
        if (System.getProperty("webdriver.chrome.driver") == null) {
            System.setProperty("webdriver.chrome.driver", CHROME_DRIVER_LOCATION);
        }
    }

     @Test
    public void shouldRUnTest() {
        // given
        int argument = 2;
        // then
        assertEquals(4, argument + argument);
    }

    @BeforeClass
    public static void getLocalWebDriver() {
        ChromeOptions options = new ChromeOptions();
        driver = new ChromeDriver(options);
        driver.manage().timeouts().implicitlyWait(2, TimeUnit.SECONDS);
    }

    @AfterClass
    public static void cleanup() {
        driver.quit();
    }

     @Test
    public void shouldEvaluateTheExpression() throws InterruptedException {
        // given
        driver.navigate().to("http://localhost:9080/");

         RemoteWebDriver remoteWebDriver;

        Thread.sleep(2000);

        // when
        final WebElement expressionInput = driver.findElement(By.id("expression"));
        expressionInput.sendKeys("3 * (4 + 3)");
        expressionInput.submit();

        Thread.sleep(2000);

        // and
        final WebElement responseToast = driver.findElement(By.cssSelector("div.Toastify__toast-body div + div"));
        final String actualResultText = responseToast.getText();

        // then
        assertEquals("The result is: 21", actualResultText);
    }

     @Test
    public void shouldSumTheFiniteGeometricSeries() throws InterruptedException {
        // given
        driver.navigate().to("http://localhost:9080/");

        Thread.sleep(2000);

        // when - set the field values
        final WebElement firstInput = driver.findElement(By.id("first"));
        final WebElement ratioInput = driver.findElement(By.id("ratio"));
        final WebElement countInput = driver.findElement(By.id("count"));
        final WebElement evaluateComplexButton = driver.findElement(By.id("evaluateComplex"));

        firstInput.sendKeys("12");
        ratioInput.sendKeys("0.5");
        countInput.sendKeys("3");

        // and submit the form
        evaluateComplexButton.submit();

        Thread.sleep(2000);  // Let the user actually see something!

        final WebElement responseToast = driver.findElement(By.cssSelector("div.Toastify__toast-body div + div"));

        String actualResultText = responseToast.getText();

        // then
        assertEquals("The result of the summation is: 21", actualResultText);

    }
}

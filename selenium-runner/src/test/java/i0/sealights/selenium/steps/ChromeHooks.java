package i0.sealights.selenium.steps;

import static i0.sealights.selenium.Properties.CHROME_DRIVER_LOCATION;

import cucumber.api.java.After;
import cucumber.api.java.Before;
import java.util.concurrent.TimeUnit;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

public class ChromeHooks {

    @Before
    public void beforeScenario() {

        if (System.getProperty("webdriver.chrome.driver") == null) {
            System.setProperty("webdriver.chrome.driver", CHROME_DRIVER_LOCATION);
        }

        ChromeOptions options = new ChromeOptions();
        BaseSteps.driver = new ChromeDriver(options);
        BaseSteps.driver.manage().timeouts().implicitlyWait(2, TimeUnit.SECONDS);

        BaseSteps.driver.navigate().to("http://localhost:9080/");
    }

    @After
    public void afterScenario() {
        BaseSteps.driver.quit();
    }
}

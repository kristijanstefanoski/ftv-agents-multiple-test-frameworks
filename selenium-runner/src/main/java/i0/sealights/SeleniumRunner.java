package i0.sealights;

import java.io.File;
import java.util.concurrent.TimeUnit;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

public class SeleniumRunner {

    private static final String CHROME_DRIVE_LOCATION = "c:\\Users\\grzeg\\sellenium-philips-mvn\\drivers\\chromedriver.exe";

    private WebDriver webDriver;

    public SeleniumRunner() {
        webDriver = getLocalWebDriver();
    }

    static WebDriver getLocalWebDriver() {
        if (System.getProperty("webdriver.chrome.driver") == null) {
            System.setProperty("webdriver.chrome.driver", CHROME_DRIVE_LOCATION);
        }

        ChromeOptions options = new ChromeOptions();
        ChromeDriver driver = new ChromeDriver(options);
        driver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);
        return driver;
    }

    public void run() throws InterruptedException {
        //        driver.get("http://localhost:9080/");
        webDriver.navigate().to("http://localhost:9080/");

        Thread.sleep(5000);  // Let the user actually see something!

        WebElement searchBox = webDriver.findElement(By.name("q"));

        searchBox.sendKeys("ChromeDriver");

        searchBox.submit();

        Thread.sleep(5000);  // Let the user actually see something!

        webDriver.quit();
    }
}

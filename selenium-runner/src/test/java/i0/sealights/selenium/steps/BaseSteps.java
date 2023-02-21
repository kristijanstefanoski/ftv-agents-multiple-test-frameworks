package i0.sealights.selenium.steps;

import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.chrome.ChromeDriver;

public class BaseSteps {

    public static ChromeDriver driver;

    protected void waitForWhile(int waitMilliseconds) {
        try {
            Thread.sleep(waitMilliseconds);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }
}

package gl.automate.common;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.edge.EdgeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;


public class WebDriverManager {
    private static WebDriver driver;

    public static WebDriver getDriver(BrowserType browserType) {
        if (driver == null) {
            switch (browserType) {
                case CHROME:
                    ChromeOptions options = new ChromeOptions();
                    // Set the path to the ChromeDriver executable
                    System.setProperty("webdriver.chrome.driver", "C:\\Users\\regie\\chromedriver\\win64-116.0.5793.0\\chromedriver-win64\\chromedriver.exe");
                    // Uncomment the following line to run Chrome in headless mode (no GUI)
                    //options.addArguments("--headless");
                    options.setBinary("C:\\Users\\regie\\chrome\\win64-116.0.5793.0\\chrome-win64\\chrome.exe");
                    options.addArguments("--start-maximized");
                    //options.addArguments("--window-size=1920,1080");
                    driver = new ChromeDriver(options);
                    //options.setAcceptInsecureCerts(true);
                    break;
                case FIREFOX:
                    FirefoxOptions firefoxOptions = new FirefoxOptions();
                    driver = new FirefoxDriver(firefoxOptions);
                    break;
                case EDGE:
                    EdgeOptions edgeOptions = new EdgeOptions();
                    driver = new EdgeDriver(edgeOptions);
                    break;
                // Add cases for other browsers as needed
                default:
                    throw new IllegalArgumentException("Unsupported browser: " + browserType);
            }

        }
        return driver;
    }

    public static void quitDriver() {
        if (driver != null) {
            driver.quit();
            driver = null;
        }
    }
    public enum BrowserType {
        CHROME,
        FIREFOX,
        EDGE,
        // Add more browser types as needed
    }
}

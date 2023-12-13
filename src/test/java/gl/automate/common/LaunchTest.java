package gl.automate.common;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;

import com.aventstack.extentreports.reporter.ExtentSparkReporter;
import com.aventstack.extentreports.reporter.configuration.Theme;
import org.openqa.selenium.WebDriver;
import org.testng.ITestResult;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeSuite;


public class LaunchTest {
    public static WebDriver driver;
    public static ExtentReports extent;
    public static ExtentTest test;

    @BeforeSuite
    public void LaunchBrowser() {
        driver = WebDriverManager.getDriver(WebDriverManager.BrowserType.CHROME);

        extent = new ExtentReports();
        ExtentSparkReporter htmlReporter = new ExtentSparkReporter("C:\\Users\\regie\\IdeaProjects\\GL-Automation\\Test Reports\\GL-Automation Reports.html");
        extent.attachReporter(htmlReporter);
        extent.setSystemInfo("OS", "Windows");
        extent.setSystemInfo("Environment", "UAT");
        extent.setSystemInfo("Version", "1");

        htmlReporter.config().setTimelineEnabled(true);
        htmlReporter.config().setDocumentTitle("GL Test Reports");
        htmlReporter.config().setReportName("GL Test Automation Reports");
        htmlReporter.config().setTimelineEnabled(true);
        htmlReporter.config().setTheme(Theme.DARK);

    }

    @AfterSuite()
    public void tearDown() throws InterruptedException {
        extent.flush();
        WebDriverManager.quitDriver();
    }
}

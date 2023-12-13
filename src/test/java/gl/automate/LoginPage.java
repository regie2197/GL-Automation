package gl.automate;

import com.aventstack.extentreports.MediaEntityBuilder;
import com.aventstack.extentreports.Status;
import gl.automate.common.CaptureTransaction;
import gl.automate.common.LaunchTest;
import org.apache.poi.ss.usermodel.*;
import org.openqa.selenium.By;
import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

public class LoginPage extends LaunchTest {

    private void sleep(int seconds) throws InterruptedException {
        Thread.sleep(seconds * 1000L);
    }
    @Test(dataProvider = "loginData")
    public void loginUAT(String email, String password) throws InterruptedException {
        test = extent.createTest("Login to UAT");
        String pulsecxURL = "https://uat-ibcbank.boostcx.com/Identity/Account/Login";
        driver.get(pulsecxURL);
        test.log(Status.INFO, "Open the Pulse CX UAT");

        WebElement emailField = driver.findElement(By.id("Input_Email"));

        WebElement passwordField = driver.findElement((By.id("Input_Password")));

        WebElement loginBtn = driver.findElement(By.id("btn-login"));

        sleep(2);
        emailField.sendKeys(email);
        test.log(Status.INFO, "Inputted the Email");
        try {
            sleep(2);
            passwordField.sendKeys(password);
            test.log(Status.INFO, "Inputted the Password");
        } catch(StaleElementReferenceException e) {
            passwordField = driver.findElement(By.id("Input_Password"));
        }
        passwordField.sendKeys(password);
        try {
            sleep(3);
            loginBtn.click();
            test.log(Status.INFO, "Clicked the Sign In Button");
        } catch(StaleElementReferenceException e) {
            loginBtn.click();
        }
        sleep(10);
        CaptureTransaction captureTransaction = new CaptureTransaction(driver, System.getProperty("user.home") + File.separator + "IdeaProjects" + File.separator + "GL-Automation");
        captureTransaction.screenCapture("After_Login_");
        // Get the screenshot path
        String screenshot = "\\After_Login_Transactions\\" + captureTransaction.getScreenshotPath();
        System.out.println(screenshot);
        test.info("Screenshot Below:", MediaEntityBuilder.createScreenCaptureFromPath(screenshot).build());
    }
    @DataProvider(name = "loginData")
    public Object[][] testData() throws IOException {
        File file = new File("LoginTestData.xlsx");
        FileInputStream inputStream = new FileInputStream(file);
        Workbook workbook = WorkbookFactory.create(inputStream);
        Sheet sheet = workbook.getSheetAt(0);
        int rowCount = sheet.getLastRowNum();
        int colCount = sheet.getRow(0).getLastCellNum();
        Object[][] data = new Object[rowCount][colCount];

        // Start from row 1 to skip the header row (row 0)
        for (int i = 1; i <= rowCount; i++) {
            Row row = sheet.getRow(i);
            for (int j = 0; j < colCount; j++) {
                Cell cell = row.getCell(j);
                data[i - 1][j] = cell.getStringCellValue();
            }
        }
        workbook.close();
        return data;
    }

}

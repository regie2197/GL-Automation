package gl.automate.common;

import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;


import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.SimpleDateFormat;
import java.util.Date;
public class CaptureTransaction implements CaptureService {
    private final WebDriver driver;
    private final String screenshotDirectory;

    private String myScreenshot; // Added a class-level variable

    public CaptureTransaction(WebDriver driver, String screenshotDirectory) {
        this.driver = driver;
        this.screenshotDirectory = screenshotDirectory;
    }
    @Override
    public void screenCapture(String transactionType) {

        try {
            // Generate the file name based on the transaction type and date time
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd_HH-mm-ss");
            String currentDateTime = dateFormat.format(new Date());
            String screenshotFileName = transactionType + "_" + currentDateTime + ".png";

            // Define the directory path and create directories if necessary
            String directoryPath = screenshotDirectory + File.separator + transactionType + "Transactions";
            Files.createDirectories(Paths.get(directoryPath));

            // Take a screenshot and save it to the specified directory
            File screenshotFile = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
            Path screenshotPath = Paths.get(directoryPath, screenshotFileName);

            Files.copy(screenshotFile.toPath(), screenshotPath);

            myScreenshot = String.valueOf(Paths.get(screenshotFileName));

            System.out.println("Screenshot saved as: " + screenshotPath.toAbsolutePath());
            //System.out.println(myScreenshot);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    // Method to retrieve the screenshot path
    public String getScreenshotPath() {
        return myScreenshot;
    }
}

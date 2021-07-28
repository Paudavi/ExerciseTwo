package listeners;

import java.io.IOException;
import java.util.Objects;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;

import resources.Base;
import resources.ExtentReporterNG;

public class Listeners extends Base implements ITestListener {
	ExtentTest test;
	ExtentReports extent = ExtentReporterNG.getReportObject();
	ThreadLocal<ExtentTest> extentTest = new ThreadLocal<ExtentTest>();
	public static Logger log = LogManager.getLogger(Base.class.getName());

	private static String getTestMethodName(ITestResult result) {
		return result.getMethod().getConstructorOrMethod().getName();
	}

	@Override
	public void onTestStart(ITestResult result) {
		// TODO Auto-generated method stub
		test = extent.createTest(result.getMethod().getMethodName());
		extentTest.set(test);
		log.info(getTestMethodName(result)+ " test is starting");

	}

	@Override
	public void onTestSuccess(ITestResult result) {
		// TODO Auto-generated method stub
		extentTest.get().log(Status.PASS, "Test passed");
		log.info(getTestMethodName(result)+ " test is succeed.");

	}

	@Override
	public void onTestFailure(ITestResult result) {
		log.info(getTestMethodName(result) + " test is failed.");
		extentTest.get().fail(result.getThrowable());
		extentTest.get().fail(result.getMethod().getDescription());
		String testMethodName = result.getMethod().getMethodName();
		try {
			driver = (WebDriver) result.getTestClass().getRealClass().getDeclaredField("driver")
					.get(result.getInstance());
		} catch (Exception e) {
		}
		try {
			getScreenShotPath(testMethodName, driver);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		// getTest().addScreenCaptureFromBase64String(base64Screenshot).getModel().getMedia().get(0));
		// extentTest.get().addScreenCaptureFromBase64String(base64Screenshot).getModel().getMedia().get(0);
		extentTest.get().addScreenCaptureFromBase64String(getBase64()).getModel().getMedia().get(0);

	}

	@Override
	public void onTestSkipped(ITestResult result) {
		// TODO Auto-generated method stub
		log.info(getTestMethodName(result) + " test is skipped.");
		extentTest.get().log(Status.SKIP, "Test Skipped");
	}

	@Override
	public void onTestFailedButWithinSuccessPercentage(ITestResult result) {
		// TODO Auto-generated method stub
		log.info(getTestMethodName(result) + " test failed but it is in defined success ratio");
		

	}

	@Override
	public void onStart(ITestContext context) {
		// TODO Auto-generated method stub
		log.info("I am on onStart method" + context.getName());
		context.setAttribute("WebDriver", this.driver);

	}

	@Override
	public void onFinish(ITestContext context) {
		// TODO Auto-generated method stub
		log.info("I am in onFinish method" + context.getName());
		extent.flush();
	}

}

package testCases;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;
import org.apache.commons.collections4.MultiValuedMap;
import org.openqa.selenium.WebDriver;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import pageObjects.WSRbuilder_body;
import pageObjects.signInPage;
import resources.Base;
import resources.Utilities;

public class BodyWSRs_Test extends Base {

	private WebDriver driver;
	private WSRbuilder_body ws;

	@BeforeMethod
	public void initalize() throws IOException {
		driver = initializeDriver();
		// ---------enter WSR builder and WSR new Body
		signInPage sg = new signInPage(driver);
		ws = sg.LoginPage();
		Utilities.jsClick(ws.tab("Body WSRs"));
		Utilities.waitURL("Recent", 7);
		ws.New().click();

	}

	@Test(alwaysRun = true,description = "Checking if the required fields are present under their correspondent titles in the new WSRs body. "
			+ "Check that the fields only accept the correct input.", enabled = true)
	public void Story03_Test1() throws IOException, InterruptedException {
		// ------------- checking titles and subtitles
		MultiValuedMap<String, String> data = Utilities.MapData(path, "WSRbody");
		data.asMap().forEach((k, v) -> ws.assertingTitlesMap(v, k));
		// --------------- filling the data
		HashMap<String, String> data1 = Utilities.hashData(path, "WSRbody", 1);
		data1.forEach((k, v) -> ws.fillingNewWSR(k, v));
		// ---------- saving and asserting
		ws.save().click();
		ws.assertSuccess();
		ws.edit().click();
		// -------- checking hours fields with string values
		List<String> daysHours = Utilities.loadPorpertiesList("days").stream().map(s -> s + " Hours").collect(Collectors.toList());
		for (String i : daysHours) {
			ws.checkingDayHours(i, "eighth", i);
		}
		// -------- checking hours fields with values over 8
		for (String i : daysHours) {
			ws.checkingDayHours(i, "9", "No extra hours allowed.");
		}
		// ---------- checking hours fields with negative values
		for (String i : daysHours) {
			ws.checkingDayHours(i, "-1", "No negative values.");
		}
	}

	@Test (alwaysRun = true,dependsOnMethods = "Story03_Test1", description = "Checking if the fields accept valid input. "
			+ "Checking if obligatory fields cannot be saved empyt.", enabled = false)
	public void Story03_Test2() throws IOException, InterruptedException {
		// --------------- filling the data
		HashMap<String, String> data1 = Utilities.hashData(path, "WSRbody",1);
		data1.forEach((k, v) -> ws.fillingNewWSR(k, v));
		//---------- saving and asserting
		ws.save().click();
		ws.assertSuccess();
		ws.edit().click();
		// -------- checking obligatory fields
		List<String> obligatoryFields = ws.obligatoryFields();
		for (String i : obligatoryFields) {
			ws.clearingNewWSR(i);
		}
	
}	
	
	
	@Test (alwaysRun = true,dependsOnMethods = "Story03_Test2", description = "Creating a new WSRs body checking that it only accepts the correct "
			+ "and necessary input", enabled = false)
	public void Story04_Test1() throws IOException, InterruptedException {
		// --------------- filling the data
		HashMap<String, String> data1 = Utilities.hashData(path, "WSRbody", 1);
		data1.forEach((k, v) -> ws.fillingNewWSR(k, v));
		// ---------- saving and asserting
		ws.save().click();
		ws.assertSuccess();
		ws.edit().click();
		// -------- checking "Stories Information" fields with "0"
		MultiValuedMap<String, String> data = Utilities.MapData(path, "WSRbody");
		ArrayList<String> storiesInfo = new ArrayList<String>(data.get("Stories Information"));
		for (String i : storiesInfo) {
			ws.checkingStories(i, "0");
		}
		// --------- checking "Stories Information" fields with negative values
		for (String i : storiesInfo) {
			ws.turnNegative(i);
		}
		ws.saveAndAssert();

	}
	
	@Test (alwaysRun = true,dependsOnMethods = "Story04_Test1",description= "Creating an new WSRs body and checking that it only accepts valid input",
			enabled = false)
	public void Story04_Test2() throws IOException, InterruptedException {
		// --------------- filling the data
		HashMap<String, String> data1 = Utilities.hashData(path, "WSRbody", 1);
		data1.forEach((k, v) -> ws.fillingNewWSR(k, v));
		// ---------- saving and asserting
		ws.save().click();
		ws.assertSuccess();
		ws.edit().click();
		// -------- changing the start date
		String date = prop.getProperty("newdate");
		ws.Calendar("Sprint End Date", date);
		ws.saveAndAssert();
		// ----- changing to same date
		ws.sameDate();
		ws.saveAndAssert();		
	}
	
	
	@Test (alwaysRun = true,dependsOnMethods = "Story04_Test2",description = "Creating a new WSRs body with valid input and sending it to the "
			+ "Manager when the information required is present.", enabled = false)
	public void Story05_Test1() throws IOException, InterruptedException {
		// --------------- filling the data
		HashMap<String, String> data1 = Utilities.hashData(path, "WSRbody", 1);
		data1.forEach((k, v) -> ws.fillingNewWSR(k, v));
		// ---------- saving and asserting
		ws.save().click();
		ws.assertSuccess();
		ws.edit().click();
		// -------- clearing User box, saving
		ws.clearingInputBox("User");
		ws.save().click();
		ws.assertSuccess();
		// ------ sending to manager, asserting error message
		ws.sumbitToManager();
		ws.assertErrorMessage("Review the errors on this page.");
		// ------- editing user and sending it again
		ws.editUser().click();
		ws.fillingUser("Paula Dávila");
		ws.sumbitManager().click();
		Utilities.waitWebElement(ws.saveChanges(), 10);
		ws.saveChanges().click();
		Utilities.waitWebElement(ws.saveSumbit(), 10);
		ws.saveSumbit().click();
		ws.assertSuccess();
		// clicking once again to send to manager expecting error message
		ws.sumbitToManager();
		ws.assertErrorMessage("Review the errors on this page.");
	}
		
	
	
	@AfterMethod
	public void tearDown() {
		driver.quit();
	}
}
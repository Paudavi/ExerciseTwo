package testCases;

import org.openqa.selenium.WebDriver;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import pageObjects.WSRbuilder_body;
import pageObjects.WSRbuilder_header;
import pageObjects.signInPage;
import resources.Base;
import resources.Utilities;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Headers_Test extends Base {
    private WebDriver driver;
    private WSRbuilder_body ws;
    private WSRbuilder_header hr;
    // private static JavascriptExecutor executor;

    @BeforeMethod
    public void initalize() throws IOException {
        driver = initializeDriver();
        // ---------enter WSR builder
        signInPage sg = new signInPage(driver);
        ws = sg.LoginPage();
        Utilities.jsClick(ws.tab("Headers"));
        Utilities.waitURL("Recent", 7);
        // creating new Header
        ws.New().click();
        Utilities.waitWebElement(ws.titleWindow("New Header"), 10);
    }

    @Test(enabled = true, description = "Checking if the required fields are present under their "
            + "correspondent titles in the New Header window. Assuring that the input fields are according to the expected.")
    public void Story01_Test1() throws IOException, InterruptedException {
        hr = new WSRbuilder_header(driver);
        List<String> listInfo = Utilities.loadPorpertiesList("listInfo");
        List<String> listWSR = Utilities.loadPorpertiesList("listWSR");
        // -----------------asserting the titles in the New Header
        hr.assertingTitles(listInfo, "Information");
        hr.assertingTitles(listWSR, "WSR Static Info");
        List<String> newList = Stream.concat(listInfo.stream(), listWSR.stream()).collect(Collectors.toList());
        // -----------------loading the data for each field
        HashMap<String, String> data1 = Utilities.hashData(path, "NewHeader", 0);
        /* ----------filling the fields with the data required */
        for (int i = 0; i < newList.size(); i++) {
            String value = newList.get(i);
            hr.fillingNewHeader(newList.get(i), data1.get(value));
        }
        // -------------- saving and checking if the information is present
        ws.save().click();
        ws.assertSuccess();
        for (String i : newList) {
            hr.assertingNewHeader(i, data1.get(i));
        }
        ws.edit().click();
        Utilities.waitWebElement(ws.titleWindow("Edit New1"), 10);
        // ----------checking the email input and asserting the text error message
        ArrayList<String> emailData = Utilities.getData(path, "Email");
        hr.checkingEmail(newList, emailData);
        // ----- clearing obligatory fields
        List<String> obligatoryFields = newList.stream().filter(x -> !x.contains("User")).collect(Collectors.toList());
        for (String i : obligatoryFields) {
            hr.clearingObligatoryFields(i, data1);

        }
        // -----clearing all fields, saving and asserting if error message is displayed
        for (String i : newList) {
            hr.clearingNewHeader(i);
            ws.saveAndAssert();

        }

    }

    @Test(enabled = false, description = "Creating Header record and trying to create an identical one expecting an error.")
    public void Story02_Test1() throws IOException, InterruptedException {
        // -----------------loading the data for each field
        List<String> newList = Utilities.getDataHeader(path, "NewHeader");
        HashMap<String, String> data1 = Utilities.hashData(path, "NewHeader", 0);
        // ----------filling the fields with the data required
        for (int i = 0; i < newList.size(); i++) {
            String value = newList.get(i);
            hr.fillingNewHeader(newList.get(i), data1.get(value));
        }
        // -------------- saving and checking if the information is present
        ws.save().click();
        ws.assertSuccess();
        for (String i : newList) {
            hr.assertingNewHeader(i, data1.get(i));
        }
        // -------------- creating a new Header
        Utilities.jsClick(ws.tab("Headers"));
        Utilities.waitURL("Recent", 7);
        ws.New().click();
        Utilities.waitWebElement(ws.titleWindow("New Header"), 10);
        // --------using the same input
        for (int i = 0; i < newList.size(); i++) {
            String value = newList.get(i);
            hr.fillingNewHeader(newList.get(i), data1.get(value));
        }
        // ------------ saving and expecting an error
        ws.saveAndAssert();
    }

    @AfterMethod
    public void tearDown() {
        driver.quit();
    }

}

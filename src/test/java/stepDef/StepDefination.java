package stepDef;

import io.cucumber.java.en.*;
import io.cucumber.junit.Cucumber;
import org.apache.commons.collections4.MultiValuedMap;
import org.junit.runner.RunWith;
import org.openqa.selenium.WebDriver;
import org.testng.annotations.AfterMethod;
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

@RunWith(Cucumber.class)
public class StepDefination extends Base {
    private WSRbuilder_body ws;
    public WebDriver driver;
    private WSRbuilder_header hr;
    public static List<String> listWSR;
    public static List<String> listInfo;
    public static List<String> newList;
    public static HashMap<String, String> data1;
    public static List<String> obligatoryFields;

    @Given("^initilize browser with chrome and going to Salesforce$")
    public void initilize_browser_with_chrome_and_going_to_salesforce() throws IOException {
        driver = initializeDriver();
        listWSR = Utilities.loadPorpertiesList("listWSR");
        listInfo = Utilities.loadPorpertiesList("listInfo");
        newList = Stream.concat(listInfo.stream(), listWSR.stream()).collect(Collectors.toList());
        data1 = Utilities.hashData(path, "NewHeader", 0);
        obligatoryFields = newList.stream().filter(x -> !x.contains("User")).collect(Collectors.toList());

    }

    @When("^user enters with username and password$")
    public void user_enters_with_username_and_password() throws IOException {
        signInPage sg = new signInPage(driver);
        ws = sg.LoginPage();
        hr = new WSRbuilder_header(driver);
    }




    @Then("^checks the title and subtitles$")
    public void checks_the_title_and_subtitles() throws IOException {
        MultiValuedMap<String, String> data = Utilities.MapData(path, "WSRbody");
        data.asMap().forEach((k, v) -> ws.assertingTitlesMap(v, k));
    }

    @Then("^check the hours fields with different values$")
    public void check_the_hours_fields_with_different_values() throws InterruptedException {
        // -------- checking hours fields with string values
        List<String> daysHours = Utilities.loadPorpertiesList("days").stream().map(s -> s + " Hours")
                .collect(Collectors.toList());
        System.out.println(daysHours);
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

    @Then("^fills all the fields with valid data, save and edit$")
    public void fills_all_the_fields_with_valid_data_save_and_edit() throws IOException {
        HashMap<String, String> data1 = Utilities.hashData(path, "WSRbody", 1);
        data1.forEach((k, v) -> ws.fillingNewWSR(k, v));
        // ---------- saving and asserting
        ws.save().click();
        ws.assertSuccess();
        ws.edit().click();
    }

    @And("^for each obligatory field, leave empty and try to save$")
    public void for_each_obligatory_field_leave_empty_and_try_to_save() throws InterruptedException {
        List<String> obligatoryFields = ws.obligatoryFields();
        for (String i : obligatoryFields) {
            ws.clearingNewWSR(i);
        }
    }

    @And("^checking Stories Information fields with zero and negative values$")
    public void checking_stories_information_fields_with_zero_and_negative_values()
            throws InterruptedException, IOException {
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

    @And("^checking the dates fields$")
    public void checking_the_dates_fields() {
        // -------- changing the start date
        String date = prop.getProperty("newdate");
        ws.Calendar("Sprint End Date", date);
        ws.saveAndAssert();
        // ----- changing to same date
        ws.sameDate();
        ws.saveAndAssert();

    }

    @And("^clear the user box, save and send to Manager, close error message$")
    public void clear_the_user_box_save_and_send_to_manager_close_error_message() {
        // -------- clearing User box, saving
        ws.clearingInputBox("User");
        ws.save().click();
        ws.assertSuccess();
        // ------ sending to manager, asserting error message
        ws.sumbitToManager();
        ws.assertErrorMessage("Review the errors on this page.");
    }

    @Then("^edit user and send it again, check success message$")
    public void edit_user_and_send_it_again_check_success_message() {
        // ------- editing user and sending it again
        ws.editUser().click();
        ws.fillingUser("Paula D??vila");
        ws.sumbitManager().click();
        Utilities.waitWebElement(ws.saveChanges(), 10);
        ws.saveChanges().click();
        Utilities.waitWebElement(ws.saveSumbit(), 10);
        ws.saveSumbit().click();
        ws.assertSuccess();
    }

    @And("^click once again to send to manager, expect error$")
    public void click_once_again_to_send_to_manager_expect_error() {
        ws.sumbitToManager();
        ws.assertErrorMessage("Review the errors on this page.");
    }

    @Then("edit the new header, and check the email input")
    public void editTheNewHeaderAndCheckTheEmailInput() throws IOException, InterruptedException {
        ws.edit().click();
        Utilities.waitWebElement(ws.titleWindow("Edit New1"), 10);
        // ----------checking the email input and asserting the text error message
        ArrayList<String> emailData = Utilities.getData(path, "Email");
        hr.checkingEmail(newList, emailData);
    }

    @And("assert the titles and subtitles in the New Header")
    public void assertTheTitlesAndSubtitlesInTheNewHeader() {
        // -----------------asserting the titles in the New Header
        hr.assertingTitles(listInfo, "Information");
        hr.assertingTitles(listWSR, "WSR Static Info");
    }

    @Then("filling with data the new header")
    public void fillingWithDataTheNewHeader() throws IOException {
        /* ----------filling the fields with the data required */
        for (int i = 0; i < newList.size(); i++) {
            String value = newList.get(i);
            hr.fillingNewHeader(newList.get(i), data1.get(value));
        }
    }

    @And("saving and checking if the information is present")
    public void savingAndCheckingIfTheInformationIsPresent() throws IOException {
        // -------------- saving and checking if the information is present
        ws.save().click();
        ws.assertSuccess();
        for (String i : newList) {
            hr.assertingNewHeader(i, data1.get(i));
        }
    }

    @Then("enters to WSRs application and Header")
    public void entersToWSRsApplicationAndHeader() {
        Utilities.jsClick(ws.tab("Headers"));
        Utilities.waitURL("Recent", 7);
    }

    @And("clear all the obligatorily fields and save. Then leave every field empty and save.")
    public void clearAllTheObligatorilyFieldsAndSaveThenLeaveEveryFieldEmptyAndSave() throws IOException, InterruptedException {
        // ----- clearing obligatory fields
        for (String i : obligatoryFields) {
            hr.clearingObligatoryFields(i, data1);

        }
        // -----clearing all fields, saving and asserting if error message is displayed
        for (String i : newList) {
            hr.clearingNewHeader(i);
            ws.saveAndAssert();
        }
    }

    @Then("enters to WSRs application and {string}")
    public void entersToWSRsApplicationAnd(String arg0) {
        Utilities.jsClick(ws.tab(arg0));
        Utilities.waitURL("Recent", 7);

    }

    @And("clicks on New and waits for the {string}")
    public void clicksOnNewAndWaitsForThe(String arg0) {
        ws.New().click();
        Utilities.waitWebElement((ws.titleWindow(arg0)), 10);
    }

    @AfterMethod
    public void tearDown() {
        driver.quit();
    }

    @And("creating a new header using the same input")
    public void creatingANewHeaderUsingTheSameInput() {
        Utilities.jsClick(ws.tab("Headers"));
        Utilities.waitURL("Recent", 7);
        ws.New().click();
        Utilities.waitWebElement(ws.titleWindow("New Header"), 10);
        // --------using the same input
        for (int i = 0; i < newList.size(); i++) {
            String value = newList.get(i);
            hr.fillingNewHeader(newList.get(i), data1.get(value));
        }
    }

    @But("saving expecting an error")
    public void savingExpectingAnError() {
        ws.saveAndAssert();
    }


}
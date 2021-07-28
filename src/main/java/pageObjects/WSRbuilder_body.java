package pageObjects;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.Select;
import org.testng.Assert;
import resources.Base;
import resources.Utilities;

import java.util.*;
import java.util.stream.Collectors;

public class WSRbuilder_body extends Shared_elements {
    // private WebDriver driver;
    public static Logger log = LogManager.getLogger(Base.class.getName());

    public WSRbuilder_body(WebDriver driver) {
        // this.driver = driver;
        PageFactory.initElements(driver, this);
    }

    @FindBy(xpath = "//div[@title='New']")
    private WebElement New;

    public WebElement New() {
        return New;
    }

    @FindBy(xpath = "//button[@name='SaveEdit']")
    private WebElement save;

    public WebElement save() {
        return save;
    }

    @FindBy(xpath = "//button[@name='Edit']")
    private WebElement edit;

    public WebElement edit() {
        return edit;
    }

    @FindBy(xpath = "//*[@title='required']/parent::label")
    private List<WebElement> obligatoryFields;

    public void assertingTitlesMap(Collection<String> v, String title) {
        v.stream().map(s -> subtitleHeaders(title, s).isDisplayed()).forEach(Assert::assertTrue);

    }

    /*dynamic paths */
    private static final String tab = "//a[@title='$value']";
    private static final String tagsDrop = "//label[text()='$value']/following-sibling::div/lightning-base-combobox";
    private static final String titleWindow = "//h2[text()='$value']";
    private static final String searchDropBox = "//label[text()='$value']/following-sibling::div/div/lightning-base-combobox";
    private static final String dayBox1 = "//*[text()='$value']/parent::span/following-sibling::div/div/div[2]/div/p";
    private static final String dayBox = "//*[text()='$value']/following::div/div";


    public WebElement tab(String headerName) {
        return Utilities.elementReplace(tab, headerName);
    }

    public WebElement titleWindow(String title) {
        return Utilities.elementReplace(titleWindow, title);
    }


    public void assertingWSRbdoy(String header, String valueHeader) {

        WebElement scope = Utilities.elementReplace(scopeHeader, header);
        String path = nameScopeHeader.replace("$value", header);
        if (header.contains("Email") | header.contains("User")) {
            String pathName = nameScopeHeaderOne.replace("$value", header);
            String expected = scope.findElement(By.xpath(pathName)).getText();
            Assert.assertEquals(valueHeader, expected);

        } else {
            String expected = scope.findElement(By.xpath(path)).getText();
            Assert.assertEquals(valueHeader, expected);

        }

    }

    public void saveAndAssert() {
        save().click();
        try {
            Utilities.waitWebElement(errorMessage, 10);
            Utilities.jsClick(closeErrorMessage);
        } catch (Exception e) {
            e.printStackTrace();
            log.info("Error message is expected but not found.");
        }
    }

    public void saveAndAssertMessage(String expected) throws InterruptedException {
        save().click();
        Utilities.waitWebElement(errorMessage, 10);
        Assert.assertTrue(errorMessage.isDisplayed());
        WebElement element = Utilities.elementReplace(textErrorMessageLink, expected);
        String text = element.getText();
        Assert.assertEquals(expected, text);
        Utilities.jsClick(closeErrorMessage);

    }

    public void assertSuccess() {
        Utilities.waitWebElement(messageSuccess, 10);
        closeMessageSuccess.click();
    }


    public void fillingNewWSR(String title, String j) {
        List<String> tagsDrops = Arrays.asList("Sprint Deliverables", "Status");
        List<String> daysBoxes = Arrays.asList("Monday", "Tuesday", "Wednesday", "Thursday", "Friday");
        List<String> searchDrop = Arrays.asList("User", "Header");
        List<String> dates = Arrays.asList("Sprint Start Date", "Sprint End Date");
        if (tagsDrops.contains(title)) {
            WebElement element = Utilities.elementReplace(tagsDrop, title);
            Utilities.scrollToElement(element);
            element.click();
            WebElement option = Utilities.elementReplace(dropOption, j);
            Utilities.waitWebElement(option, 10);
            Utilities.jsClick(option);

        } else if (searchDrop.contains(title)) { // Header
            WebElement element = Utilities.elementReplace(searchDropBox, title);
            Utilities.scrollToElement(element);
            element.sendKeys(j);
            element.click();
            WebElement option = Utilities.elementReplace(dropOption, j);
            Utilities.waitWebElement(option, 10);
            Utilities.jsClick(option);

        } else if (daysBoxes.contains(title)) {
            WebElement element = Utilities.elementReplace(dayBox, title);
            Utilities.scrollToElement(element);
            element.click();
            WebElement box = Utilities.elementReplace(dayBox1, title);
            Utilities.scrollToElement(box);
            box.sendKeys(j);

        } else if (dates.contains(title)) {
            WebElement element = Utilities.elementReplace(tagsInput, title);
            Utilities.scrollToElement(element);
            Calendar(title, j);
        } else {
            WebElement element = Utilities.elementReplace(tagsInput, title);
            Utilities.scrollToElement(element);
            element.clear();
            element.sendKeys(j);

        }
    }

    public void checkingDayHours(String days, String num, String message) throws InterruptedException {
        WebElement element = Utilities.elementReplace(tagsInput, days);
        Utilities.scrollToElement(element);
        String input = element.getAttribute("value");
        element.clear();
        element.sendKeys(num);
        saveAndAssertMessage(message);
        element.clear();
        element.sendKeys(input);

    }

    public List<String> obligatoryFields() {
        List<String> obligatoryFieldsText = obligatoryFields.stream().map(x -> x.getText()).map(x -> x.substring(1))
                .collect(Collectors.toList());
        return obligatoryFieldsText;
    }

    @FindBy(xpath = "//*[@data-key='check']/following::span/span")
    private WebElement optionSelected;


    public void clearingNewWSR(String title) throws InterruptedException {
        List<String> tagsDrops = Arrays.asList("Sprint Deliverables", "Status");
        List<String> daysBoxes = Arrays.asList("Monday", "Tuesday", "Wednesday", "Thursday", "Friday");
        if (tagsDrops.contains(title)) {
            WebElement element = Utilities.elementReplace(tagsDrop, title);
            Utilities.scrollToElement(element);
            element.click();
            Utilities.waitWebElement(optionSelected, 10);
            String input = optionSelected.getText();
            WebElement option = Utilities.elementReplace(dropOption, "--None--");
            Utilities.jsClick(option);
            saveAndAssertMessage(title);
            element.click();
            Utilities.waitWebElement(optionSelected, 10);
            WebElement firstOp = Utilities.elementReplace(dropOption, input);
            Utilities.jsClick(firstOp);

        } else if (daysBoxes.contains(title)) {
            WebElement element = Utilities.elementReplace(dayBox, title);
            Utilities.scrollToElement(element);
            element.click();
            WebElement box = Utilities.elementReplace(dayBox1, title);
            Utilities.scrollToElement(box);
            String input = box.getText();
            box.clear();
            saveAndAssertMessage(title);
            element.click();
            box.sendKeys(input);

        } else {
            WebElement element = Utilities.elementReplace(tagsInput, title);
            Utilities.scrollToElement(element);
            String input = element.getAttribute("value");
            element.clear();
            saveAndAssertMessage(title);
            element.sendKeys(input);

        }
    }

    public void checkingStories(String title, String j) throws InterruptedException {

        WebElement element = Utilities.elementReplace(tagsInput, title);
        Utilities.scrollToElement(element);
        String input = element.getAttribute("value");
        element.clear();
        element.sendKeys(j);
        saveAndAssert();
        element.clear();
        element.sendKeys(input);

    }

    public void turnNegative(String title) {
        WebElement element = Utilities.elementReplace(tagsInput, title);
        Utilities.scrollToElement(element);
        String input = "-" + element.getAttribute("value");
        element.clear();
        element.sendKeys(input);
    }

    @FindBy(xpath = "//select[@class='slds-select']")
    private WebElement year;

    @FindBy(xpath = "//div[@class='slds-datepicker__filter slds-grid']/div/h2")
    private WebElement month;

    @FindBy(xpath = "//button[@title='Next Month']")
    private WebElement nextMonth;

    @FindBy(xpath = "//td[@aria-current='date']")
    private WebElement currentDate;

    @FindBy(xpath = "//span[@class='slds-day']")
    private List<WebElement> days;

    public void Calendar(String nameBox, String date) {
        HashMap<String, String> dataDate = Utilities.date(date);
        WebElement boxDate = Utilities.elementReplace(tagsInput, nameBox);
        boxDate.click();
        while (!month.getText().contains(dataDate.get("month"))) {
            nextMonth.click();
        }
        Select dropdown = new Select(year);
        dropdown.selectByVisibleText(dataDate.get("year"));
        for (int i = 0; i < days.size(); i++) {
            String intDay = days.get(i).getText();
            if (intDay.equalsIgnoreCase(dataDate.get("day"))) {
                days.get(i).click();
                break;
            }
        }

    }

    public void sameDate() {
        WebElement startDate = Utilities.elementReplace(tagsInput, "Sprint Start Date");
        String valueStart = startDate.getAttribute("value");
        WebElement endDate = Utilities.elementReplace(tagsInput, "Sprint End Date");
        endDate.clear();
        endDate.sendKeys(valueStart);
    }

    /*dynamic paths*/
    private static final String inputClearButton = "//label[text()='$value']/following-sibling::div//button[@title='Clear Selection']";
    public static final String buttonsSumbit = "//footer[@class='slds-modal__footer']//button//span[text()='$value']";

    public void clearingInputBox(String field) {
        Utilities.elementReplace(inputClearButton, field).click();
    }

    @FindBy(xpath = "//button[text()='Submit to Manager']")
    private WebElement sumbitManager;

    public WebElement sumbitManager() {
        return sumbitManager;
    }


    public WebElement saveSumbit() {
        return Utilities.elementReplace(buttonsSumbit, "Save");
    }

    public void sumbitToManager() {
        sumbitManager().click();
        Utilities.elementReplace(buttonsSumbit, "Save").click();
    }

    @FindBy(xpath = "//div[@class='pageLevelErrors']/div/div/span")
    private WebElement errorMessageSumbit;



    public void assertErrorMessage(String message) {
        Utilities.waitWebElement(errorMessageSumbit, 10);
        String textError = errorMessageSumbit.getText();
        Assert.assertEquals(message, textError);
        Utilities.elementReplace(buttonsSumbit, "Cancel").click();
    }

    @FindBy(xpath = "//button[@title='Edit User']")
    private WebElement editUser;

    public WebElement editUser() {
        return editUser;
    }

    @FindBy(xpath = "//label[text()='User']/following::input[contains(@placeholder,'Search People')]")
    private WebElement editUserInput;


    public void fillingUser(String user) {
        editUserInput.sendKeys(user);
        WebElement option = Utilities.elementReplace(dropOption, user);
        Utilities.waitWebElement(option, 10);
        option.click();
    }

    @FindBy(xpath = "//*[contains(@class,'slds-modal__footer')]//button[text()='Save']")
    private WebElement saveChanges;

    public WebElement saveChanges() {
        return saveChanges;
    }

}

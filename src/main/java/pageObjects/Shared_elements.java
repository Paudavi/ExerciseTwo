package pageObjects;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import resources.Utilities;

public class Shared_elements {

    protected static final String tagsInput = "//label[text()='$value']/following-sibling::div/input";
    protected static final String tagsDropNewHeader = "//label[text()='$value']/following-sibling::div/div/lightning-base-combobox";
    protected static final String dropOption = "//*[@title='$value']";
    protected static final String tagsTextarea = "//label[text()='$value']/following-sibling::div/textarea";
    protected static final String titleHeaders = "//span[text()='$value']/parent::h3/parent::div";
    protected static final String textErrorMessageLink = "//div[@class='panel-header']//following::*[text()='$value']";
    protected static final String scopeHeader = "//flexipage-component2//*[text()='$value']/parent::div/parent::div/parent::div";
    protected static final String nameScopeHeader = "//flexipage-component2//*[text()='$value']/following::lightning-formatted-text";
    protected static final String nameScopeHeaderOne = "//flexipage-component2//*[text()='$value']/following::a";

    public WebElement subtitleHeaders(String title, String subtitle) {
        WebElement Title = Utilities.elementReplace(titleHeaders, title);
        String path = "//*[text()='" + subtitle + "']";
        return Title.findElement(By.xpath(path));

    }

    @FindBy(xpath = "//*[contains(@class,'PageError')]")
    protected WebElement errorMessage;

    @FindBy(xpath = "//div[@class='panel-header']/force-record-edit-error-header/lightning-button-icon")
    protected WebElement closeErrorMessage;

    @FindBy(xpath = "//div[@data-key='success']")
    protected WebElement messageSuccess;

    @FindBy(xpath = "//button[@title='Close']")
    protected WebElement closeMessageSuccess;
}

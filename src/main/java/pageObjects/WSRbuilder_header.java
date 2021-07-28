package pageObjects;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.testng.Assert;
import resources.Utilities;

import java.util.HashMap;
import java.util.List;

public class WSRbuilder_header extends Shared_elements{
	private WSRbuilder_body bd;
public WSRbuilder_header(WebDriver driver){
    PageFactory.initElements(driver,this);
    bd = new WSRbuilder_body (driver);
}

@FindBy(xpath = "//button[@title='Clear Selection']")
private WebElement clearSelection;

    public void fillingNewHeader(String title, String j) {
        if (title.contains("Email") | title.contains("Header")) {
            WebElement element = Utilities.elementReplace(tagsInput, title);
            element.sendKeys(j);
        } else if (title.contains("User")) {
            WebElement element = Utilities.elementReplace(tagsDropNewHeader, title);
            element.sendKeys(j);
            element.click();
            WebElement option = Utilities.elementReplace(dropOption, j);
            Utilities.waitWebElement(option, 10);
            element.click();
            option.click();
        } else {
            WebElement element = Utilities.elementReplace(tagsTextarea, title);
            element.sendKeys(j);
        }

    }

    public void clearingNewHeader(String title) {
		if (title.contains("Email") | title.contains("Header")) {
			WebElement element = Utilities.elementReplace(tagsInput, title);
			element.clear();
		} else if (title.contains("User")) {
			clearSelection.click();
		} else {
			WebElement element = Utilities.elementReplace(tagsTextarea, title);
			element.clear();
		}

	}

    public void clearingObligatoryFields(String title, HashMap<String, String> hashMap) throws InterruptedException {
		if (title.contains("Email") | title.contains("Header")) {
			WebElement element = Utilities.elementReplace(tagsInput, title);
			String input = element.getAttribute("value");
			element.clear();
			bd.saveAndAssert();
			element.sendKeys(input);
		} else {
			WebElement element = Utilities.elementReplace(tagsTextarea, title);
			String input = element.getText();
			element.clear();
			bd.saveAndAssert();
			element.sendKeys(input);
		}
	}

	public void assertingTitles(List<String> list, String title) {
		list.stream().map(name -> subtitleHeaders(title,name).isDisplayed()).forEach(Assert::assertTrue);
	}

	public void checkingEmail(List<String> newList, List<String> emailData) throws InterruptedException {

		for (String i : emailData) {
			WebElement emailBox = Utilities.elementReplace(tagsInput, newList.get(1));
			emailBox.clear();
			emailBox.sendKeys(i);
			bd.save().click();
			Thread.sleep(2000);
			Utilities.waitWebElement(errorMessage, 10);
			Assert.assertTrue((Utilities.elementReplace(textErrorMessageLink, newList.get(1))).isDisplayed());
			Utilities.jsClick(closeErrorMessage);
		}
	}

	public void assertingNewHeader(String header, String valueHeader) {

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
}

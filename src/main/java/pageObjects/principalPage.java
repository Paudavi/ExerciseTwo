package pageObjects;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import resources.Utilities;

public class principalPage {
	
	WebDriver driver;
	public principalPage(WebDriver driver) {
		this.driver = driver;
		PageFactory.initElements(driver, this);		
	}
	private final static String search= "//*[@class='slds-truncate']/*[text()='$value']";
	@FindBy(xpath="//*[@class='slds-icon-waffle']")
	WebElement iniciador;
	@FindBy(xpath="//input[@class='slds-input']")
	WebElement searchBox;
		@FindBy (xpath="//*[@class='slds-truncate']/*[text()='Servicio']")
	WebElement servicio;

	public WebElement iniciador() {
		return iniciador;
	}
	public WebElement buscador() {
		return searchBox;
	}	
	public WebElement search(String value) {
		return Utilities.elementReplace(search, value);
	}
	
}

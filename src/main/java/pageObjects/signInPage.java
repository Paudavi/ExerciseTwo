package pageObjects;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import resources.Base;
import resources.Utilities;

import java.io.IOException;

public class signInPage extends Base {
	
	private WebDriver driver;	
	public signInPage(WebDriver driver) {
		this.driver = driver;
		PageFactory.initElements(driver, this);
	}
	@FindBy (xpath="//div[@aria-label='Login']")
	private WebElement principalLogin;
	@FindBy(id="username") 
	private WebElement username;
	@FindBy (id="password")
	private WebElement password;
	@FindBy (id="Login")
	private WebElement login;
	
	public WebElement firstLogin() {
		return principalLogin;
	}

	public WebElement username() {
		return username;
	}
	public WebElement password() {
		return password;
	}
	public WebElement login() {
	return login;
		
	}
	public  WSRbuilder_body LoginPage() throws IOException {
		properties();		
		signInPage sg = new signInPage(driver);
		sg.principalLogin.click();
		Utilities.waitWebElement(username(), 7);
		sg.username().sendKeys(prop.getProperty("username"));
		sg.password().sendKeys(prop.getProperty("password"));
		sg.login().click();
		principalPage pp= new principalPage(driver);
		pp.iniciador().click();
		pp.buscador().sendKeys(prop.getProperty("search"));
		pp.search(prop.getProperty("search")).click();
		WSRbuilder_body ws = new WSRbuilder_body(driver);
		return ws;
		
	}
}	
	
	
	


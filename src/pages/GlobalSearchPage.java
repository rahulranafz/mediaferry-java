package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.testng.asserts.Assertion;
import org.testng.asserts.SoftAssert;

import BaseClasses.BasePage;
import constants.Constants;
import steps.UploadDownloadAssetsSteps;
import utilities.FileOperations;

public class GlobalSearchPage extends BasePage{
	
	String getCompaignText;
	UploadDownloadAssetsSteps uploadDownloadAssetsSteps = new UploadDownloadAssetsSteps();
	
	private By searchByProjectLocator = By.id("SearchBar");
	private By waitForProjectLocator = By.xpath("//li[@class='projectTitle']");
	private By waitForAssetLocator = By.xpath("//li[@class='assetTitle']");
	private By clickOnProjectLocator = By.xpath("//p[@class='projectimgheading']//span[@class='highlight']");
	private By getCompaignNameLocator = By.xpath("//div[@class='form-group']//div[@id='container_div_select_campaign']//a[@class='select2-choice']//span[1]");
	private By saveChangesButtonLocator = By.id("save_view_edit");
	
	public void searchByCompaign(WebDriver driver, String compaign)
	{
		driver.findElement(searchByProjectLocator).clear();
		driver.findElement(searchByProjectLocator).sendKeys(compaign);
		waitForElementVisibility(driver, waitForProjectLocator);	
		} 
	
	public void searchByAsset(WebDriver driver, String asset)
	{
		driver.findElement(searchByProjectLocator).clear();
		driver.findElement(searchByProjectLocator).sendKeys(asset);
		waitForElementVisibility(driver, waitForAssetLocator);	
	} 
	
	public void searchByProjectAndClick(WebDriver driver,SoftAssert softAssert, String projectName) throws InterruptedException {
		driver.findElement(searchByProjectLocator).clear();
		driver.findElement(searchByProjectLocator).sendKeys(projectName);
		waitForElementVisibility(driver, waitForProjectLocator);
		driver.findElement(waitForProjectLocator).click();	
		waitForElementVisibility(driver, clickOnProjectLocator);
		driver.findElement(clickOnProjectLocator).click();
		Thread.sleep(2000);
//		getCompaignText = driver.findElement(getCompaignNameLocator).getText();	
//		softAssert.assertEquals(getCompaignText, "SFA2212178");
		uploadDownloadAssetsSteps.uploadAssetsForProofSteps(driver);

	}
	
	public void clickSaveChangesButton(WebDriver driver) throws InterruptedException
	{
		driver.findElement(saveChangesButtonLocator).click();
		Thread.sleep(4000);
	}
	
}



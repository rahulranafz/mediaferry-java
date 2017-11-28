package steps;

import org.openqa.selenium.WebDriver;
import org.testng.asserts.SoftAssert;

import pages.CreateNewProjectPage;
import pages.GlobalSearchPage;

public class GlobalSearchSteps {
	
	SoftAssert softAssert = new SoftAssert();
	String projectName = null;
	GlobalSearchPage globalSearchPage = new GlobalSearchPage();
	CreateNewProjectSteps createNewProjectSteps = new CreateNewProjectSteps();
	CreateNewProjectPage createNewProjectPage = new CreateNewProjectPage();
	UploadDownloadAssetsSteps uploadDownlaodAssetsSteps = new UploadDownloadAssetsSteps();
	
	
	
	public void assetUpload(WebDriver driver,String filePath) throws InterruptedException {
	
		createNewProjectPage.fileUploadWhileCreating(driver, filePath);
		createNewProjectPage.acceptingWarnings(driver);		
	}
	
	public void GlobalSearchStepsByProjectName(WebDriver driver,SoftAssert softAssert,String compaign,String asset,String projectName,String filePath) throws InterruptedException 
	{	
		globalSearchPage.searchByCompaign(driver, compaign);
		globalSearchPage.searchByAsset(driver, asset);
		globalSearchPage.searchByProjectAndClick(driver, softAssert, projectName);
		uploadDownlaodAssetsSteps.uploadFileOnProofingWindow(driver, filePath, softAssert);
		globalSearchPage.clickSaveChangesButton(driver);
	}
}


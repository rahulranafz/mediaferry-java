package tests;

import java.io.IOException;

import org.openqa.selenium.WebDriver;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

import BaseClasses.Driver;
import steps.CreateNewProjectSteps;
import steps.GlobalSearchSteps;
import steps.HomeSteps;
import steps.LoginSteps;
import utilities.UtilityMethods;

public class GlobalSearchTest {
	Driver driverObj = new Driver();
	WebDriver driver = null;
	SoftAssert softAssert = new SoftAssert();
	LoginSteps loginSteps = new LoginSteps();
	String projectName = null;
	String asset = null;
	UtilityMethods utilityMethods = new UtilityMethods();
	int projectCountBefore;
	HomeSteps homeSteps = new HomeSteps();
	CreateNewProjectSteps createNewProjectSteps = new CreateNewProjectSteps();
	int projectCountAfter;
	GlobalSearchSteps globalSearchSteps = new GlobalSearchSteps();

	@BeforeTest
	public void start() throws IOException {
		driver = driverObj.createDriver();
		driver.get(driverObj.getUrl());
	}

	@Test(priority=1)
	public void ApproveActionFunctionality() throws Exception {
		loginSteps.login(driver, softAssert, driverObj.getUsername(), driverObj.getPassword());
		projectName = utilityMethods.createUniqueProjectName(driverObj.getProjectName());
		projectCountBefore = homeSteps.projectCountBefore(driver);
		homeSteps.creatingNewProject(driver);
		createNewProjectSteps.fillingDetails(driver, projectName, driverObj.getCampaign() ,driverObj.getBrandName(), driverObj.getCreativeLevel(), driverObj.getFilePath(), driverObj.getPriority(), driverObj.getProjectOwner(), driverObj.getInstructions(), driverObj.getTeam(), driverObj.getWidth(), driverObj.getHeight());
		globalSearchSteps.assetUpload(driver, driverObj.getFilePath());
		createNewProjectSteps.submittingJob(driver,softAssert, projectName);
		projectCountAfter = homeSteps.projectCountAfter(driver);
		globalSearchSteps.GlobalSearchStepsByProjectName(driver, softAssert, driverObj.getCampaign(),"Test.pdf", projectName,driverObj.getFilePath());
	}
	@AfterTest
    public void tearDown(){
        driver.quit();
        driver=null;
    }
}




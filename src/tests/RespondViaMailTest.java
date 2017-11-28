package tests;

import BaseClasses.Driver;
import org.openqa.selenium.WebDriver;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;
import steps.*;
import utilities.UtilityMethods;

import java.io.IOException;

public class RespondViaMailTest {

    Driver driverObj = new Driver();
    WebDriver driver = null;
    LoginSteps loginSteps = new LoginSteps();
    SoftAssert softAssert = new SoftAssert();
    UtilityMethods utilityMethods = new UtilityMethods();
    HomeSteps homeSteps = new HomeSteps();
    CreateNewProjectSteps createNewProjectSteps = new CreateNewProjectSteps();
    ActiveProjectsSteps activeProjectsSteps = new ActiveProjectsSteps();
    ActivityFeedSteps activityFeedSteps  = new ActivityFeedSteps();

    String projectName = null;
    int projectCountBefore, projectCountAfter;

    @BeforeTest
    public void start() throws IOException {
        driver = driverObj.createDriver();
        driver.get(driverObj.getUrl());
        loginSteps.login(driver, softAssert, driverObj.getUsername(), driverObj.getPassword());
        projectName = "My Test Project  412_2017-10-12";
    }

    @Test(priority = 1)
    public void sendQuery() throws InterruptedException {
        homeSteps.waitingForHeaderLnksSpinner(driver);
        homeSteps.goToActiveProjectsWithoutWait(driver);
        homeSteps.waitingForConstantLnksSpinner(driver);
        activeProjectsSteps.selectProject(driver, softAssert, projectName);
        activeProjectsSteps.sendQuerySteps(driver, softAssert, projectName, "This is query message text");
    }

   @Test(priority = 2)
    public void respondingViaMailWhileLogin() throws InterruptedException {
        activityFeedSteps.respondQueryViaEmailSteps(driver, "[FromName] EKCS has a query My Test Project  412_2017-10-12", "Response to the Test Query");
        activityFeedSteps.verifyStatus(driver, softAssert);
    }

    @Test(priority = 3)
    public void verifyQueryLink() throws InterruptedException {
        homeSteps.goToActiveProjectsWithoutWait(driver);
        homeSteps.waitingForConstantLnksSpinner(driver);
        activeProjectsSteps.selectProject(driver, softAssert, projectName);
        activeProjectsSteps.verifyingQueryLnkSteps(driver, softAssert, projectName, true, false);
    }

    @Test(priority = 4)
    public void verifyStatusLink() {
        activeProjectsSteps.verifyingStatusLnkSteps(driver, softAssert, projectName, true, false);
   
    }
    @Test(priority = 5)
    public void respondingViaMailNotLogin() throws InterruptedException{
        loginSteps.logout(driver, softAssert);
        activityFeedSteps.respondQueryViaEmailWhenNotLogin(driver, "[FromName] EKCS has a query My Test Project  412_2017-10-12");
        loginSteps.verifyValidation(driver, softAssert);
    }

    @AfterTest
    public void tearDown() {
        driver.quit();
        driver = null;
    }
}

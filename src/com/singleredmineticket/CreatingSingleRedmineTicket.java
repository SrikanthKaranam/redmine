package com.singleredmineticket;

import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

public class CreatingSingleRedmineTicket {
  private WebDriver driver;
  private String baseUrl;

  @BeforeClass
  public void setUp() throws Exception {
    driver = new FirefoxDriver();
    driver.manage().window().maximize();
    baseUrl = "http://redmine.cfteam.in/";
    driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
  }

  @Test
  public void testRedime() throws Exception {
    WebDriverWait wait = new WebDriverWait(driver, 30);
    String subject = "Prod-Nitro:Back to top arrow is not there in SRP";
    driver.get(baseUrl + "/");

    driver.findElement(By.linkText("Sign in")).click();
    driver.findElement(By.id("username")).clear();
    driver.findElement(By.id("username")).sendKeys("srikanth.k@commonfloor.com");
    driver.findElement(By.id("password")).clear();
    driver.findElement(By.id("password")).sendKeys("Sree_237237");
    driver.findElement(By.name("login")).click();

    new Select(driver.findElement(By.id("project_quick_jump_box")))
        .selectByVisibleText("Real Estate CommonFloor");

    driver.findElement(By.linkText("Issues")).click();
    driver.findElement(By.linkText("New issue")).click();

    new Select(driver.findElement(By.id("issue_tracker_id")))
        .selectByVisibleText("Bug");
    // driver.findElement(By.id("issue_subject")).clear();
    Thread.sleep(3000);
    driver.findElement(By.id("issue_subject")).sendKeys(
        "Prod-Nitro:Back to top arrow is not there in SRP");
    // wait.until(ExpectedConditions.textToBePresentInElement(locator, text))
    // Thread.sleep(10000);
    driver.findElement(By.id("issue_description")).clear();
    driver
        .findElement(By.id("issue_description"))
        .sendKeys(
            "Browser:All\n\nSteps: \n\n1).Go to nitro SRP\n\n2).Then verify the back to top arrow on srp page\n\nActual:Back to top arrow is not present\n\nExpected:Back to top arrow should present");
    new Select(driver.findElement(By.id("issue_priority_id")))
        .selectByVisibleText("High");
    new Select(driver.findElement(By.id("issue_assigned_to_id")))
        .selectByVisibleText("Rakesh Ranjan");
    new Select(driver.findElement(By.id("issue_category_id")))
        .selectByVisibleText("Production Bug-Real Estate");
    new Select(driver.findElement(By.id("issue_custom_field_values_22")))
        .selectByVisibleText("Seeker Experience");
    driver.manage().timeouts().implicitlyWait(5000, TimeUnit.SECONDS);
    new Select(driver.findElement(By.id("issue_custom_field_values_7")))
        .selectByVisibleText("Rakesh Ranjan");
    driver.findElement(By.xpath("//input[@name=\"commit\"]")).click();
    wait.until(ExpectedConditions.titleContains(subject));
    System.out.println(driver.getCurrentUrl());
  }

  @AfterClass
  public void tearDown() throws Exception {
    driver.quit();

  }

}

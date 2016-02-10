package com.creatingmuiltipleredminetickets;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

import jxl.Sheet;
import jxl.Workbook;
import jxl.read.biff.BiffException;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import com.common.Common;

public class CreatingMuiltipleRedmineTickets extends Common {
  public CreatingMuiltipleRedmineTickets() throws Exception {
    super();
  }

  private WebDriver driver;
  private String baseUrl;

  private static final String USER_NAME = "srikanth.k@commonfloor.com";
  private static final String PASSWORD = "Sree_233233";

  @BeforeClass
  public void setUpBrowser() throws Exception {
	DesiredCapabilities cap = DesiredCapabilities.chrome();
	System.setProperty("webdriver.chrome.driver", "E:\\Srikanth\\Srikanth\\Selenium\\chromedriver_win32\\chromedriver.exe");
    driver = new ChromeDriver(cap);
    baseUrl = "http://redmine.cfteam.in/";
    driver.navigate().to(baseUrl);
    driver.manage().window().maximize();
  }

  @Test(priority = 0)
  public void creatingTheRedmineTickets() throws BiffException,
      FileNotFoundException, IOException {
    driver.get(baseUrl + "/");

    // login
    Common.click(By.linkText("Sign in"), driver);
    Common.clear(By.id("username"), driver);
    Common.input(By.id("username"), USER_NAME, driver);
    Common.clear(By.id("password"), driver);
    Common.input(By.id("password"), PASSWORD, driver);
    Common.click(By.name("login"), driver);

    // Switching to RE
    Common.selectDropDown(By.id("project_quick_jump_box"), driver)
        .selectByVisibleText("Real Estate CommonFloor");

    // Insert For loop Here

    Workbook wb = Workbook.getWorkbook(new FileInputStream(
        "C:\\Users\\CFLAP471\\Desktop\\RedmineIssues\\CreatingMuiltipleRedmineTickets2.xls"));
    Sheet ws = wb.getSheet(0);

    for (int r = 1; r < ws.getRows(); r++) {
      int c = 0;

      // Common.click(By.linkText("Issues"), driver);
      Common.click(By.linkText("New issue"), driver);

      // Selecting Issue Tracker
      Common.selectDropDown(By.id("issue_tracker_id"), driver)
          .selectByVisibleText(ws.getCell(c, r).getContents());
      c = c + 1;

      // Entering Issue Subject
      Common.waitForTime(5000);
      Common.clear(By.id("issue_subject"), driver);
      Common.input(By.id("issue_subject"), ws.getCell(c, r).getContents(),
          driver);
      c = c + 1;

      // Entering the Issue Description
      Common.clear(By.id("issue_description"), driver);
      Common.input(By.id("issue_description"), ws.getCell(c, r).getContents(),
          driver);
      c = c + 1;

      // Entering bug Status
      Common.selectDropDown(By.id("issue_status_id"), driver)
          .selectByVisibleText(ws.getCell(c, r).getContents());
      c = c + 1;

      // Entering bug Priority
      Common.selectDropDown(By.id("issue_priority_id"), driver)
          .selectByVisibleText(ws.getCell(c, r).getContents());
      c = c + 1;

      // Entering Assigned To
      Common.selectDropDown(By.id("issue_assigned_to_id"), driver)
          .selectByVisibleText(ws.getCell(c, r).getContents());
      c = c + 1;

      // Issue Category
      Common.selectDropDown(By.id("issue_category_id"), driver)
          .selectByVisibleText(ws.getCell(c, r).getContents());
      c = c + 1;

      // Release Pm Owner
      Common.selectDropDown(By.id("issue_custom_field_values_7"), driver)
          .selectByVisibleText(ws.getCell(c, r).getContents());
      c = c + 1;

      // Parent Ticket
      Common.input(By.id("issue_parent_issue_id"), ws.getCell(c, r)
          .getContents(), driver);
      Common.click(By.id("issue_parent_issue_id"), driver);
      c = c + 1;

      // Focus Area
      Common.selectDropDown(By.id("issue_custom_field_values_22"), driver)
          .selectByVisibleText(ws.getCell(c, r).getContents());
      c = c + 1;

      // driver.findElement(By.xpath("//input[@name=\"commit\"]")).click();
      Common.click(By.xpath("//form[@id='issue-form']/input"), driver);

      Common.waitForPresenceOfElement(By.xpath("//div[@id='flash_notice']/a"),
          driver);

      String Bugid = Common.getText(By.xpath("//div[@id='flash_notice']/a"),
          driver);

      System.out.println("Current bug is :  [ " + Bugid + " ]");

    }

  }

  @AfterClass(alwaysRun = true)
  public void tearDown() throws Exception {
    driver.quit();
  }
}

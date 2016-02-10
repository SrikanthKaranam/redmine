package practice;

import java.util.concurrent.TimeUnit;

import org.testng.annotations.*;

import static org.testng.Assert.*;

import org.openqa.selenium.*;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.ui.Select;

public class PostYourRequirement {
  private WebDriver driver;
  private String baseUrl;
  private StringBuffer verificationErrors = new StringBuffer();

  @BeforeMethod(alwaysRun = true)
  public void setUp() throws Exception {
    driver = new FirefoxDriver();
    baseUrl = "https://stage22.commonfloor.com/post-public-property-requirement";
    driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
  }

  @Test
  public void testPost() throws Exception {
    driver.get(baseUrl + "/post-public-property-requirement");
    driver.findElement(By.id("rent")).click();
    driver.findElement(By.id("req_contact_name")).clear();
    driver.findElement(By.id("req_contact_name")).sendKeys("Swamy");
    driver.findElement(By.id("req_email_id")).clear();
    driver.findElement(By.id("req_email_id")).sendKeys("swamy.k@kmail.com");
    new Select(driver.findElement(By.id("req_country_code"))).selectByVisibleText("001");
    new Select(driver.findElement(By.id("req_country_code"))).selectByVisibleText("91");
    driver.findElement(By.id("req_contact_mobile")).clear();
    driver.findElement(By.id("req_contact_mobile")).sendKeys("8523698589");
    new Select(driver.findElement(By.id("req_city"))).selectByVisibleText("Bangalore");
    driver.findElement(By.id("req_property_location_input")).click();
    driver.findElement(By.id("req_property_location_input")).clear();
    driver.findElement(By.id("req_property_location_input")).sendKeys("domlur");
    driver.findElement(By.linkText("Domlur")).click();
    driver.findElement(By.id("property_type_Apartment")).click();
    driver.findElement(By.cssSelector("div.selectAll_wrap > label.propertyLabel > span")).click();
    driver.findElement(By.id("bhk2")).click();
    driver.findElement(By.cssSelector("a.chzn-single.chzn-single-with-drop > span")).click();
    driver.findElement(By.cssSelector("div.chzn-search > input[type=\"text\"]")).clear();
    driver.findElement(By.cssSelector("div.chzn-search > input[type=\"text\"]")).sendKeys("80");
    new Select(driver.findElement(By.id("req_min_inr"))).selectByVisibleText("80,000");
    driver.findElement(By.cssSelector("a.chzn-single.chzn-single-with-drop > span")).click();
    driver.findElement(By.cssSelector("#req_max_inr_chzn > div.chzn-drop > div.chzn-search > input[type=\"text\"]")).clear();
    driver.findElement(By.cssSelector("#req_max_inr_chzn > div.chzn-drop > div.chzn-search > input[type=\"text\"]")).sendKeys("90");
    new Select(driver.findElement(By.id("req_max_inr"))).selectByVisibleText("90,000");
    driver.findElement(By.id("termService")).click();
    driver.findElement(By.id("termService")).click();
    driver.findElement(By.id("req_submit")).click();
  }

  @AfterMethod(alwaysRun = true)
  public void tearDown() throws Exception {
    driver.quit();
    String verificationErrorString = verificationErrors.toString();
    if (!"".equals(verificationErrorString)) {
      fail(verificationErrorString);
    }
  }
}

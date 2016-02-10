package com.common;

import java.net.MalformedURLException;
import java.net.URL;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Random;
import java.util.Set;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.Cookie;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.NoSuchWindowException;
import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebDriverException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;

import com.thoughtworks.selenium.webdriven.JavascriptLibrary;

public class Common {

  private static Actions action;
  protected static int DEFAULT_TIMEOUT = 30;

  /**
   * @author Srikanth This Method is Used to Close Extra Window and Switch back to Original Parent
   *         Window
   */
  public static void closeExtraWindowAndSwitchBackToParentWindow(String WindowHandle,
      WebDriver driver) {
    driver.close();
    driver.switchTo().window(WindowHandle);
  }

  /**
   * @author Srikanth This Method is Used to get all the Active Windows Handlers and Store into a
   *         Set Iterate over it and Softly Close all Active Session.
   */
  public static void handleAfterClassCleanup(WebDriver driver) {
    Set<String> allActiveWindowsHandlers = driver.getWindowHandles();
    for (String currentWindowHandle : allActiveWindowsHandlers) {
      driver.switchTo().window(currentWindowHandle);
      driver.close();
    }
  }


  /**
   * Press escape on a page whenever required.
   * 
   * @author Deepak Gopal
   * @param driver
   */
  public static void pressEscape(WebDriver driver) {
    Actions action = new Actions(driver);
    action.sendKeys(Keys.ESCAPE).build().perform();
  }

  /**
   * @ set cookies to avoid NPS popup, welcome screen, etc.
   */
  public static void setCookiesToAvoidInitialPopups(WebDriver driver) {
    try {
      Set<Cookie> cookies = driver.manage().getCookies();
      if (!cookies.isEmpty()) {
        driver.manage().deleteAllCookies();
        cookies.clear();
      }
    } catch (WebDriverException w) {
    }

    try {
      Calendar c = Calendar.getInstance(); // starts with today's date and
      // time
      c.add(Calendar.DAY_OF_YEAR, 2); // advances day by 2
      // Date date = c.getTime();
      Cookie ck = new Cookie("NpsPopupTime", "1497132414722");
      driver.manage().addCookie(ck);
    } catch (WebDriverException w) {
    }
  }

  /**
   * @ Get default WebDriverWait object with default timeout. That way it is easier to increase
   * default timeout.
   */
  public static WebDriverWait getDefaultWebDriverWait(WebDriver driver) {
    return new WebDriverWait(driver, DEFAULT_TIMEOUT);
  }

  /**
   * @author Srikanth This method is used for the enter data into a Text box
   */
  public static void input(By locator, String input, WebDriver driver) {
    try {
      WebElement element = waitForPresenceOfElement(locator, driver);
      element.sendKeys(input);
    } catch (NoSuchElementException e) {
      nosuchElementException("" + locator, driver);
    } catch (TimeoutException e) {
      timeOutException("" + locator, driver);
    }
  }

  /**
   * @author Srikanth This Method is used for the Input of Keys like Enter; Tab ; Space etc
   */
  public static void inputKeyStrokes(By locator, Keys input, WebDriver driver) {
    try {
      WebElement element = waitForPresenceOfElement(locator, driver);
      element.sendKeys(input);
    } catch (NoSuchElementException e) {
      nosuchElementException("" + locator, driver);
    } catch (TimeoutException e) {
      timeOutException("" + locator, driver);
    } catch (StaleElementReferenceException e) {
      Assert.fail("StaleElementReferenceException at [" + locator + "] Because tried to enter "
          + "key strokes" + driver.getCurrentUrl());
    }
  }

  /**
   * @author Srikanth This method is used for click on button, Radio buttons, Links by passing
   *         locator to it
   */
  public static void click(By locator, WebDriver driver) {
    try {
      WebElement element = waitForPresenceOfElement(locator, driver);
      element.click();
    } catch (NoSuchElementException e) {
      nosuchElementException("" + locator, driver);
    } catch (TimeoutException e) {
      timeOutException("" + locator, driver);
    }
  }

  /**
   * @author Srikanth This method is used for clear an text box before entering any values into it
   */
  public static void clear(By locator, WebDriver driver) {
    try {
      WebElement element = waitForPresenceOfElement(locator, driver);
      element.clear();
    } catch (NoSuchElementException e) {
      nosuchElementException("" + locator, driver);
    } catch (TimeoutException e) {
      timeOutException("" + locator, driver);
    } catch (WebDriverException e) {
      Assert.fail("Web driver Exeception " + e.getMessage() + " while Clearing [" + locator
          + "] At url " + driver.getCurrentUrl());
    }
  }

  /**
   * @ This method is used for get text by passing the locator of an Element
   */
  public static String getText(By locator, WebDriver driver) {
    try {
      WebElement element = waitForPresenceOfElement(locator, driver);
      element.getText();
    } catch (NoSuchElementException e) {
      nosuchElementException("" + locator, driver);
    } catch (TimeoutException e) {
      timeOutException("" + locator, driver);
    } catch (StaleElementReferenceException e) {
      Assert.fail("StaleElementReferenceException at [" + locator + "] Because tried to enter "
          + "key strokes" + driver.getCurrentUrl());
    }
    return driver.findElement(locator).getText();
  }

  /**
   * @author Srikanth This method is used for selecting an element from droup down
   */
  public static Select selectDropDown(By locator, WebDriver driver) {
    waitForClickable(locator, driver);
    return new Select(driver.findElement(locator));
  }

  /**
   * @author Srikanth This method is used to scroll down to certain units
   */
  public static void scrollDown(String Value, WebDriver driver) {
    JavascriptExecutor jse = (JavascriptExecutor) driver;
    jse.executeScript("window.scrollBy(0," + Value + ")", "");
  }

  /**
   * This method is used for waiting till the element is Present using Webdriver wait
   * 
   * @param locator : holds the locator to be found.
   * @param driver : holds the driver reference.
   */
  public static WebElement waitForPresenceOfElement(By locator, WebDriver driver) {
    try {
      new WebDriverWait(driver, DEFAULT_TIMEOUT).until(ExpectedConditions
          .presenceOfElementLocated(locator));
    } catch (NoSuchElementException e) {
      nosuchElementException("" + locator, driver);
    } catch (TimeoutException e) {
      timeOutException("" + locator, driver);
    }
    return driver.findElement(locator);
  }


  /**
   * @author Srikanth This method is used for waiting till the element is Visible using Webdriver
   *         wait
   * 
   * @param locator : holds the locator to be found.
   * @param driver : holds the driver reference.
   */
  public static WebElement waitForVisibilityOfElement(By locator, WebDriver driver) {
    try {
      new WebDriverWait(driver, DEFAULT_TIMEOUT).until(ExpectedConditions
          .visibilityOfElementLocated(locator));
    } catch (NoSuchElementException e) {
      nosuchElementException("" + locator, driver);
    } catch (TimeoutException e) {
      timeOutException("" + locator, driver);
    }
    return driver.findElement(locator);
  }


  /**
   * @ This method is used for waiting until the specific text of element is Present using Webdriver
   * wait
   */
  public static void waitFortextpresentofelement(By locator, String text, WebDriver driver) {
    try {
      new WebDriverWait(driver, DEFAULT_TIMEOUT).until(ExpectedConditions
          .textToBePresentInElementLocated(locator, text));
    } catch (NoSuchElementException e) {
      nosuchElementException("" + locator, driver);
    } catch (TimeoutException e) {
      timeOutException("" + locator, driver);
    }
  }

  /**
   * @ This method is used for waiting until the element is clickable using Webdriver wait
   */
  public static void waitForClickable(By locator, WebDriver driver) {
    try {
      waitForPresenceOfElement(locator, driver);
      new WebDriverWait(driver, DEFAULT_TIMEOUT).until(ExpectedConditions
          .elementToBeClickable(locator));
    } catch (NoSuchElementException e) {
      nosuchElementException("" + locator, driver);
    } catch (TimeoutException e) {
      timeOutException("" + locator, driver);
    }
  }

  /**
   * @author Srikanth This method is used for waiting for given time.
   */
  public static void waitForTime(int time) {
    try {
      Thread.sleep(time);
    } catch (InterruptedException e) {
      e.printStackTrace();
    }
  }

  /**
   * @author Srikanth This method is used for waiting for 10 sec while the Discussion Mailer
   *         reaches the mailbox
   */
  public static void waitingInMailboxforTenSec() {
    try {
      Thread.sleep(10000);
    } catch (InterruptedException e) {
      e.printStackTrace();
    }
  }

  /**
   * @author Srikanth
   * @param locator
   * @param driver This Method is Used to Key Down Return Key at Auto-suggest.
   */
  public static void autoCompleteEnter(By locator, WebDriver driver) {
    try {
      driver.findElement(locator).sendKeys(Keys.ENTER);
    } catch (NoSuchElementException e) {
      nosuchElementException("" + locator, driver);
    } catch (TimeoutException e) {
      timeOutException("" + locator, driver);
    }

  }

  /**
   * @author Srikanth This method is used for waiting till the element is Hidden
   */
  public static void waitForInvisibilityOfElement(By locator, WebDriver driver) {
    try {
      new WebDriverWait(driver, DEFAULT_TIMEOUT).until(ExpectedConditions
          .invisibilityOfElementLocated(locator));
    } catch (NoSuchElementException e) {
      nosuchElementException("" + locator, driver);
    } catch (TimeoutException e) {
      timeOutException("" + locator, driver);
    }
  }

  /**
   * @ This method is used for the is Element Displayed by Passing Element Locator
   */
  public static boolean isElementDisplayed(By locator, WebDriver driver) {
    try {
      return Common.waitForPresenceOfElement(locator, driver).isDisplayed();
    } catch (NoSuchElementException n) {
      nosuchElementException("" + locator, driver);
    } catch (TimeoutException e) {
      timeOutException("" + locator, driver);
    }
    return false;
  }

  /**
   * @ This method is used for handling of maximum time out exception
   */
  public static void timeOutException(String locator, WebDriver driver) {
    Assert.fail("Maximum Time waited for [" + locator + "] But Not Found in url "
        + driver.getCurrentUrl());
  }

  /**
   * @author Srikanth This method is used for handlinlg no such element exception
   */
  public static void nosuchElementException(String locator, WebDriver driver) {
    Assert.fail("Element [" + locator + "] is not found at url : " + driver.getCurrentUrl());
  }


  /**
   * @author Srikanth This method is used for the assertTrue Condition Call
   */
  public static void assertTrueIfContains(By locator, String conatinsString, WebDriver driver) {
    String actualTextFromLocator = waitForPresenceOfElement(locator, driver).getText();
     Assert.assertTrue(actualTextFromLocator.contains(conatinsString), "At This page string ->"
        + actualTextFromLocator + " with locator -> " + locator
        + " is Not Containing ActualString -> " + conatinsString + "; Please Check at url "
        + driver.getCurrentUrl());
  }


  /**
   * @author Srikanth This method is used for the assertFalse Condition Call
   */
  public static void assertFalseIfPageSourceContains(String conatinsString, WebDriver driver) {
    Assert.assertFalse(driver.getPageSource().contains(conatinsString),
        "At This Url [ " + driver.getCurrentUrl() + "] Page should not contain [ " + conatinsString
            + " ] But it contains. There Might Be some Error Please Look");
  }


  /**
   * @author Srikanth This method is used for the assertFalse Condition Call is a Locator contains
   *         Some Text
   */
  public static void assertFalseIfContains(String expectedString, String conatinsString,
      String locator, WebDriver driver) {
    Assert.assertFalse(expectedString.contains(conatinsString),
        "At This Url [ " + driver.getCurrentUrl() + "] Page should not contain [ " + conatinsString
            + " ] But it contains. There Might Be some Error Please Look");
  }

  /**
   * @author Srikanth This method is used for the assert True is Element displayed
   */
  public static void assertTrueForIsDisplayed(By locator, WebDriver driver) {
    Assert.assertTrue(driver.findElement(locator).isDisplayed(), "Element with locator " + locator
        + " is not Displayed in url " + driver.getCurrentUrl());
  }

  /**
   * This method checks if the element is displayed on a page
   */
  public static void assertTrueForIsSelected(By locator, WebDriver driver) {
    Assert.assertTrue(driver.findElement(locator).getAttribute("class").contains("active"),
        "Element with locator " + locator + " is not Displayed in url " + driver.getCurrentUrl());
  }

  /**
   * @ This method is used for the printing current date and time
   */
  public static String dateAndTime() {
    DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
    Date date = new Date();
    String Date = dateFormat.format(date);
    return Date;
  }

  /**
   * @author Srikanth This method is used to Override the Certificate issue in IE browser
   */
  public static void fixingIECertificateIssue(WebDriver driver) {
    System.out.println("Gmail Frame http issue - IE - Accepting certificate ...");
    driver.navigate().refresh();
    driver.navigate().to("javascript:document.getElementById('overridelink').click()");
    System.out.println("Fix is Done For Gmail Login Frame Http");
  }


  /**
   * @author Sonali This method is used to wait until the Page title is not found for 60 seconds
   */
  public static void WebdriverWaitForPageTitle(String pageTitle, WebDriver driver) {
    WebDriverWait wait = getDefaultWebDriverWait(driver);
    wait.until(ExpectedConditions.titleContains(pageTitle));
  }



  /**
   * @author Deep This method waits until the windows for the current driver session are active
   */
  public static void waitForNumberOfWindowsGreaterOrEqualTo(final int numOFWindows, WebDriver driver) {
    // Making a new expected condition
    new WebDriverWait(driver, 60) {}.until(new ExpectedCondition<Boolean>() {
      public Boolean apply(WebDriver driver) {
        return (driver.getWindowHandles().size() >= numOFWindows);
      }
    });
  }

  /**
   * @author Sonali This method gets the number of windows for the driver ,switches to the new
   *         window and verifies page title
   */
  public static void switchWindowAndVerifyPageTitle(By locator, WebDriver driver) {
    try {
      String text;
      String ParentWindowHandle = driver.getWindowHandle();
      WebElement element = waitForPresenceOfElement(locator, driver);
      JavascriptExecutor js = (JavascriptExecutor) driver;
      text = (String) js.executeScript("return arguments[0].innerHTML", element);
      JavascriptExecutor jse = (JavascriptExecutor) driver;
      jse.executeScript("scroll(250, 0)");
      JavascriptLibrary jsLib = new JavascriptLibrary();
      jsLib.callEmbeddedSelenium(driver, "triggerMouseEventAt", element, "click", "0,0");
      waitForNumberOfWindowsGreaterOrEqualTo(2, driver);
      Set<String> setWindowHandles = driver.getWindowHandles();
      // Switch to new window opened
      for (String winHandle : setWindowHandles) {
        if (!winHandle.equals(ParentWindowHandle)) {
          driver.switchTo().window(winHandle);
          driver.getTitle().contains(text);
          driver.close();
          break;
        }
      }
      driver.switchTo().window(ParentWindowHandle);
      System.out.println("Switched to parent window :" + driver.getTitle());
    } catch (NoSuchElementException n) {
      nosuchElementException("" + locator, driver);
    } catch (NoSuchWindowException w) {
      System.out.println("No such window" + w);
    }
  }

  /**
   * @author Sonali This method is used to click an element using JS to avoid
   *         "Element not clickable" error
   */
  public static void clickUsingJS(By locator, WebDriver driver) {
    WebElement element = null;
    try {
      element = waitForPresenceOfElement(locator, driver);
      clickUsingJS(element, driver);
    } catch (NoSuchElementException e) {
      Common.nosuchElementException("" + element, driver);
    }
  }

  /**
   * @author Rajat Kachhara
   * @param element
   * @param driver Click on an element using JS.
   */
  public static long generateRandomMobileNo(int length) {
    Random random = new Random();
    char[] digits = new char[length];
    digits[0] = (char) (random.nextInt(1) + '8');
    for (int i = 1; i < length; i++) {
      digits[i] = (char) (random.nextInt(9) + '0');
    }
    return Long.parseLong(new String(digits));
  }

  /**
   * @author Deepak Gopal
   * @param element
   * @param driver Click on an element using JS.
   */
  public static void clickUsingJS(WebElement element, WebDriver driver) {
    try {
      ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", element);
      JavascriptLibrary jsLib = new JavascriptLibrary();
      jsLib.callEmbeddedSelenium(driver, "triggerMouseEventAt", element, "click", "0,0");
    } catch (TimeoutException e) {
      Common.timeOutException("" + element, driver);
    }
  }

  /**
   * @author Srikanth This method hovers on a element using Actions
   */
  public static void hoverOnElement(By locator, WebDriver driver) {
    WebElement hover = null;
    try {
      Actions action = new Actions(driver);
      hover = Common.waitForPresenceOfElement(locator, driver);
      action.moveToElement(hover).build().perform();
    } catch (NoSuchElementException e) {
      Common.nosuchElementException("" + locator, driver);
    } catch (TimeoutException e) {
      Common.timeOutException("" + locator, driver);
    }
  }

  /**
   * @author Srikanth This method is Used to Get Text from a locator and then apply Assert equals
   *         Condition
   */
  public static void assertTextUsingEquals(By locator, String expectedText, WebDriver driver) {
    try {
      String actualText = waitForPresenceOfElement(locator, driver).getText();
      Assert.assertEquals(actualText, expectedText);
    } catch (NoSuchElementException e) {
      Common.nosuchElementException("" + locator, driver);
    } catch (TimeoutException e) {
      Common.timeOutException("" + locator, driver);
    }
  }

  /**
   * @author Sonali This method asserts text using Assert true and JS
   */
  public static void assertTextUsingAssertTrueAndJS(By locator, String expectedText, String msg,
      WebDriver driver) {
    try {
      WebElement element = waitForPresenceOfElement(locator, driver);
      JavascriptExecutor js = (JavascriptExecutor) driver;
      String text = (String) js.executeScript("return arguments[0].innerHTML", element);
      Assert.assertTrue(text.contains(expectedText), msg);
    } catch (NoSuchElementException e) {
      Common.nosuchElementException("" + locator, driver);
    } catch (TimeoutException e) {
      Common.timeOutException("" + locator, driver);
    }
  }

  /**
   * @author Deepak Gopal There are instances where stage4.commonfloor.com redirects to
   *         stage4.commonfloor.com/chennai-city. We avoid that here.
   * @return The current Base URL.
   * @throws MalformedURLException
   */
  public static String changeURL(String currentUrl, String extension) throws MalformedURLException {
    URL url = new URL(currentUrl);
    currentUrl = url.getHost();
    if (currentUrl != null)
      currentUrl = "http://" + currentUrl + extension;
    return currentUrl;
  }

  /**
   * @author Sonali
   * @param locator - Locator for the element.
   * @param driver - The WebDriver instance.
   * @return True if the element exists. Else return false.
   * 
   *         Check if a given element exists.
   */
  public static boolean existsElement(By locator, WebDriver driver) {
    driver.manage().timeouts().implicitlyWait(100, TimeUnit.MILLISECONDS);
    try {
      driver.findElement(locator);
    } catch (NoSuchElementException e) {
      return false;
    }
    return true;
  }

  /**
   * Checks a page for new header,if yes, then resets it to old header
   * 
   * @param driver - Current webdriver instance.
   * @param baseURL - Current baseUrl.
   * @return The url after reset.
   */
  public static String resetNewHeaderToOldHeader(WebDriver driver, String baseURL) {
    String oldHeaderString = "/?bkting_cmp_name=header_redesign&bkting_bkt_name=old-header";
    // check if baseurl already has the bucketing query
    String pattern = "(\\s)(/?bkting)(\\s)"; // TODO: This pattern doesn't
    // work, get a better one.
    if (baseURL.matches(pattern)) {
      System.out.println("BaseURL - " + baseURL);
      baseURL = baseURL.split("/?")[0];
    }
    String url = baseURL + oldHeaderString;
    Boolean existElement = existsElement(By.id("new-header"), driver);
    if (existElement) {
      driver.navigate().to(url);
      if (existElement == existsElement(By.id("new-header"), driver)) {
        // Attempt 2 if we are still in the new header.
        driver.navigate().to(url);
      }
      isElementDisplayed(By.cssSelector(".header-top-nav"), driver);
    }
    return url;
  }

  /**
   * @author Saumya This method is use to handle the Alert
   */
  public static void switchToAlert(WebDriver driver) {
    Alert alt = driver.switchTo().alert();
    alt.accept();
  }

  /**
   * selects range from slider For instance the budget range from home page
   * 
   * @param MinRangelocator : locator for Minimum button
   * @param MaxRangelocator : locator for Minimum button
   * @param dragFrom : value from which the slider should be dragged 1.e 10,20...
   * @param dragTo : value to which the slider should be dragged usually 0
   */
  public static void selectRangeFromSlider(By MinRangelocator, By MaxRangelocator, int MindragFrom,
      int MindragTo, int MaxdragFrom, int MaxdragTo, WebDriver driver) {
    Common.waitForPresenceOfElement(MinRangelocator, driver);
    action = new Actions(driver);
    WebElement MinSlider = driver.findElement(MinRangelocator);
    Actions action1 = action.dragAndDropBy(MinSlider, MindragFrom, MindragTo);
    action1.build().perform();
    WebElement MaxSlider = driver.findElement(MaxRangelocator);
    Actions action2 = action.dragAndDropBy(MaxSlider, MaxdragFrom, MaxdragTo);
    action2.build().perform();

    // An example of how to call this function
    // Common.selectRangeFromSlider(By.xpath(XPATH_BUDGET_MINRANGE),
    // By.xpath(XPATH_BUDGET_MAXRANGE),10, 0, -20,0,driver);
  }

  /**
   * @author Sonali This method verifies String using Assert true
   * @param condition
   * @param string
   */
  public static void assertrueUsingContains(boolean condition, String message) {
    Assert.assertTrue(condition, message);
  }

  /**
   * @author Srikanth
   * @param Locator
   * @param driver This Method is used to Right Click and Open new Tab
   */
  public static void rightClickAndOpenInNewTab(By Locator, WebDriver driver) {
    // Perform the click operation of Mouse and opens new window
    WebElement Link = driver.findElement(Locator);
    Actions action = new Actions(driver);
    action.contextClick(Link).sendKeys(Keys.ARROW_DOWN).sendKeys(Keys.ARROW_DOWN)
        .sendKeys(Keys.RETURN).build().perform();
  }


  /**
   * @author Srikanth This method is used to get all the links in the tag by passing tag name and
   *         attribute
   * @param tagName
   * @param attribute
   * @param driver
   * @param startsWithRegex
   * @return 
   */
  public static List<String> getLinksBasedonParameters(String tagName, String attribute,
      String startsWithRegex, WebDriver driver) {
    List<WebElement> alllinks = driver.findElements(By.tagName(tagName));
    List<String> links = new ArrayList<String>();
    System.out.println("In Tag["+tagName+"]attribute ["+attribute+"] total count :"+ alllinks.size());
    for (int i = 0; i < alllinks.size(); i++) {
      WebElement lin = (WebElement) alllinks.get(i);
      String HrefLinksInPage = lin.getAttribute(attribute);
      links.add(HrefLinksInPage);
      if (HrefLinksInPage != null && HrefLinksInPage.startsWith(startsWithRegex)) {
        System.out.println(HrefLinksInPage);
      }
    }
    return links;
  }

}

package selenium_Scripts;

import java.time.Duration;
import java.util.Set;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Reporter;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

public class FisGlobal_Automation {
	
	@Test
	public void verifyItemsInCart() throws InterruptedException {
		
		WebDriver d=new ChromeDriver();
		d.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
		d.manage().window().maximize();
		d.get("https://www.ebay.com/");
		String pWh = d.getWindowHandle();
		d.findElement(By.xpath("//input[@id='gh-ac']")).sendKeys("book");
		d.findElement(By.xpath("//button[@id='gh-search-btn']/span[text()='Search']")).click();
		Thread.sleep(2000);
		
		WebElement e = d.findElement(By.xpath("(//ul[@class='srp-results srp-list clearfix']/descendant::span[@role='heading'])[1]"));
		int y = e.getLocation().getY();
		JavascriptExecutor jse= (JavascriptExecutor) d;
		jse.executeScript("window.scrollBy(0,"+y+")");
		Thread.sleep(1000);
		e.click();
		
		Set<String> allWH = d.getWindowHandles();
		for (String aWH : allWH) {
			if (pWh.equals(aWH)) {
				continue;
			} else {
				d.switchTo().window(aWH);
			}
		}
		WebElement addToCart = d.findElement(By.xpath("//span[.='Add to cart']/ancestor::a"));
		int y1 = addToCart.getLocation().getY();
		jse.executeScript("window.scrollBy(0,"+y1+")");
		addToCart.click();
		
		String addToCartNotification = "//div[@class='gh-cart']/descendant::span[@class='badge']";
		WebDriverWait wait=new WebDriverWait(d, Duration.ofSeconds(15));
		wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath(addToCartNotification)));
		String noOfItems = d.findElement(By.xpath(addToCartNotification)).getText();
		String expectedNoOfItems = "1";
		
		SoftAssert sa=new SoftAssert();
		sa.assertEquals(noOfItems, expectedNoOfItems);
		d.quit();
		sa.assertAll();
		Reporter.log("Test Passed.", true);
		
	}

}

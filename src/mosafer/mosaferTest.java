package mosafer;

import java.io.File;
import java.io.IOException;
import java.time.Duration;
import java.util.List;
import java.util.Random;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.support.ui.Select;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

public class mosaferTest {
	WebDriver driver = new EdgeDriver();
	SoftAssert myassertiion = new SoftAssert ();

	@BeforeTest
	public void startTest () {
		driver.get("https://www.almosafer.com/en");	
	}
	
	
	// --------------------------------------- to test default language 
	
	@Test(priority = 1) 
	public void defaultLanguageTest () {
		String title = driver.findElement(By.xpath("//*[@id=\"__next\"]/section[2]/div[2]/div/div/div/h1")).getText();
		System.out.println(title);
		Boolean titlemsg = title.contains("next trip!");
		SoftAssert myassertiion = new SoftAssert ();
		myassertiion.assertEquals(titlemsg, true);
		myassertiion.assertAll();
	}
	
	
	
	
	//--------------------------------------- test default currency 
	
	@Test(priority = 2) 
	public void defaultCurrency () {
		String expectedCurrency = "SAR" ; 
		String actualCurrency = driver.findElement(By.xpath("//*[@id=\"__next\"]/header/div/div[1]/div[2]/div/div[1]")).getText();
		myassertiion.assertEquals(actualCurrency, expectedCurrency);
		myassertiion.assertAll();

	}
	
	
	
	
	//---------------------------------------- check contant number
	
	@Test(priority = 3) 
	public void contantNumberTest () {
		String number = driver.findElement(By.xpath("//*[@id=\"__next\"]/header/div/div[1]/div[2]/div/a[2]/strong")).getText();
		System.out.println(number+"***************************************************");
		Boolean contantnumber = number.contains("+966554400000");
		myassertiion.assertEquals(contantnumber, true);
		myassertiion.assertAll();
	}

	
	
	//-------------------------------------------------- verify qitaf logo in footer 
	
	@Test(priority = 4)
	public void qitafLogoTest () {
		WebElement div = driver.findElement(By.xpath("//*[@id=\"__next\"]/footer/div[3]/div[3]/div[1]/div[2]/div/div[2]")); 
		List<WebElement> qitafdiv = div.findElements(By.tagName("svg")) ;
		String expectedQitafId = "Footer__QitafLogo" ;
		String actualQitafId = qitafdiv.get(0).getAttribute("data-testid"); 
		myassertiion.assertEquals(actualQitafId, expectedQitafId);
		myassertiion.assertAll();	
	}
	
	
	
	//---------------------------------------------------- 	Hotels search tab is NOT selected by default
	
	@Test(priority = 5) 
	public void hotelTab () {
		WebElement hotelTab = driver.findElement(By.xpath("//*[@id=\"uncontrolled-tab-example-tab-hotels\"]")); 
		String expectedvalue = "false" ; 
		String actualvalue = hotelTab.getAttribute("aria-selected");
		myassertiion.assertEquals(actualvalue, expectedvalue) ; 
		myassertiion.assertAll();
		
	}
	
	
	
	//------------------------------------------------ change language (sometime keep AR, sometime change to EN)
	
	@Test(priority = 5 , invocationCount = 5) 
	public void changeLanguage () {
		String [] websiteLanguages =  {"https://www.almosafer.com/en" , "https://www.almosafer.com/ar"} ; 
		Random rand = new Random () ; 
		int randomlanguage = rand.nextInt(2);
		driver.get(websiteLanguages[randomlanguage]);
		String actualtitle = driver.getTitle();
		if (randomlanguage==0) {
			myassertiion.assertEquals(actualtitle, " Almosafer: Flights, Hotels, Activities & Airlines Ticket Booking" , "the language in website is english");
		}
		
		else {
			myassertiion.assertEquals(actualtitle, "المسافر: رحلات، فنادق، أنشطة ممتعة و تذاكر طيران" , "the language in website is arabic");
		}
	}
	
	
	
	//----------------------------------------------------- test location field
	
	@Test (priority = 6 ) 
	public void citiesTest () throws IOException {
		String [] websiteLanguages =  {"https://www.almosafer.com/en" , "https://www.almosafer.com/ar"} ;
		String [] englishCities = {"Dubai" , "Jeddah" , "Riyadh"} ; 
		String [] arabicCities =  {"دﺑﻲ " , "جدة"} ;
		Random rand = new Random () ;
		int randomWebsiteLanguage = rand.nextInt(2) ; 
		int randomEnglish = rand.nextInt(3); 
		int randomArabic = rand.nextInt(2) ; 
		driver.get(websiteLanguages[randomWebsiteLanguage]);
		if (randomWebsiteLanguage==0) {
			driver.findElement(By.xpath("//*[@id=\"uncontrolled-tab-example-tabpane-flights\"]/div/div[2]/div[1]/div/div[2]/div[1]/div/div/div/input")).sendKeys(englishCities[randomEnglish]);
			driver.findElement(By.xpath("//*[@id=\"uncontrolled-tab-example-tabpane-flights\"]/div/div[2]/div[1]/div/div[2]/div[3]/div[1]/div/div/input")).sendKeys("Amman, Jordan") ; 
			driver.findElement(By.xpath("//*[@id=\"uncontrolled-tab-example-tabpane-flights\"]/div/div[2]/div[2]/div/div[2]/div/button")).click();
		}
		else {
			driver.findElement(By.xpath("//*[@id=\"uncontrolled-tab-example-tabpane-flights\"]/div/div[2]/div[1]/div/div[2]/div[1]/div/div/div/input")).sendKeys(arabicCities[randomArabic]);
			driver.findElement(By.xpath("//*[@id=\"uncontrolled-tab-example-tabpane-flights\"]/div/div[2]/div[1]/div/div[2]/div[3]/div[1]/div/div/input")).sendKeys("Amman, Jordan") ; 
			driver.findElement(By.xpath("//*[@id=\"uncontrolled-tab-example-tabpane-flights\"]/div/div[2]/div[2]/div/div[2]/div/button")).click();
		}
		}
		
	
	
	//--------------------------------------------------------- 5.	Randomly select “1 room, 2 adults, 0 children” or “1 room, 1 adult, 0 children” option.
	
	
	@Test (priority = 7 , invocationCount = 3 ) 
	public void randomSelect () {
		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(3)); 
		driver.get("https://www.almosafer.com/en");
		driver.findElement(By.xpath("//*[@id=\"uncontrolled-tab-example-tab-hotels\"]/div")).click();
		Random myrand = new Random (); 
		 int randoomRoom = myrand.nextInt(2) ; 
		WebElement roomSelector = driver.findElement(By.xpath("//*[@id=\"uncontrolled-tab-example-tabpane-hotels\"]/div/div/div/div[3]/div/select")); 
		Select myselector = new Select(roomSelector);
		myselector.selectByIndex(randoomRoom) ; 
		System.out.println(roomSelector.getText());
		
	}
	  
	
	// ----------------------------------------------------------- Search results page 
	@Test(priority = 8) 
	public void hotelSearchResult () throws InterruptedException {
		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(3)); 
		driver.get("https://www.almosafer.com/en");
		driver.findElement(By.xpath("//*[@id=\"uncontrolled-tab-example-tab-hotels\"]")).click();
		Thread.sleep(2000) ; 
		driver.findElement(By.xpath("//*[@id=\"uncontrolled-tab-example-tabpane-hotels\"]/div/div/div/div[1]/div/div[1]/div/div/input")).sendKeys("Ardetxal"); 
		driver.findElement(By.xpath("//*[@id=\"uncontrolled-tab-example-tabpane-hotels\"]/div/div/div/div[4]/button")).click(); 
		String actualmsg = driver.findElement(By.xpath("//*[@id=\"__next\"]/div[2]/section/span")).getText(); 
		System.out.println(actualmsg+ "****************************************************************");
		Boolean hotelname = actualmsg.contains("found in Ardetxal"); 
		myassertiion.assertEquals(hotelname, true  ) ;  
		myassertiion.assertAll(); 
	}
	
	
	//--------------------------------------------------------------- 
	
	@AfterTest 
	public void endTest () {
		
	}
}

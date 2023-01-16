package com.firstProject.webAutomation;

import java.io.FileReader;
import java.util.Iterator;
import java.util.List;
import java.util.Properties;
import java.util.Set;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.Select;

import org.testng.annotations.*;

public class HomePage {
	public static WebDriver driver;
	public static Properties p;
	public HomePage() {
		FileReader reader;
		try {
			reader = new FileReader("config.properties");
			p = new Properties();
			p.load(reader);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}
	
	@Test(priority = 0)
	@Parameters("Browser")
	public void getDriver(String browser) {
		//driver = DriverSetup.getDriver(browser);
		driver = DriverSetup.getDriver();
		driver.get(p.getProperty("baseUrl"));
	}
	
	@Test(priority = 1, dependsOnMethods = "getDriver")
	public void chkRadio() {
		driver.findElement(By.xpath(p.getProperty("rad"))).click();
	}
	
	@Test(priority = 2, dependsOnMethods = "getDriver")
	@Parameters("Country")
	public void slctCountry(String str) {
		driver.findElement(By.id("autocomplete")).sendKeys(str);
	}
	
	@Test(priority = 3, dependsOnMethods = "getDriver")
	public void dropDown() {
		Select sl = new Select(driver.findElement(By.id("dropdown-class-example")));
		sl.selectByVisibleText("Option1");
	}
	
	@Test(priority = 4, dependsOnMethods = "getDriver")
	public void check() {
		driver.findElement(By.id("checkBoxOption1")).click();
		driver.findElement(By.id("checkBoxOption2")).click();
	}
	
	@Test(priority = 5, dependsOnMethods = "getDriver")
	public void winHandle() {
		String parent = driver.getWindowHandle();
		driver.findElement(By.id("openwindow")).click();
		Set<String> wins = driver.getWindowHandles();
		Iterator<String> itr = wins.iterator();
		while(itr.hasNext()) { 
			String child = itr.next();
			if(!parent.equals(child)) {
				driver.switchTo().window(child);
			}
		}
		System.out.println("Switched to window name: "+ driver.getTitle());
		try {
			Thread.sleep(10000);
			driver.findElement(By.xpath("//button[text()='NO THANKS']")).click();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		driver.close();
		driver.switchTo().window(parent);
	}
	
	@Test(priority = 6, dependsOnMethods = "getDriver")
	public void openTab() {
		String parent = driver.getWindowHandle();
		driver.findElement(By.id("opentab")).click();
		Set<String> wins = driver.getWindowHandles();
		Iterator<String> itr = wins.iterator();
		while(itr.hasNext()) {
			String child = itr.next();
			if(!parent.equals(child)) {
				driver.switchTo().window(child);
			}
		}
		System.out.println("Switched to tab name: "+ driver.getTitle());
		try {
			Thread.sleep(3000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		driver.close();
		driver.switchTo().window(parent);
	}
	
	@Test(priority = 7, dependsOnMethods = "getDriver")
	public void chkAlert() {
		driver.findElement(By.id("name")).sendKeys("Amarta");
		driver.findElement(By.id("alertbtn")).click();
		try {
			Thread.sleep(3000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		driver.switchTo().alert().accept();
		driver.findElement(By.id("name")).sendKeys("Amarta");
		driver.findElement(By.id("confirmbtn")).click();
		try {
			Thread.sleep(3000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		driver.switchTo().alert().dismiss();
	}
	
	@Test(priority = 8, dependsOnMethods = "getDriver")
	public void tableView() {
		JavascriptExecutor js = (JavascriptExecutor)driver;
		js.executeScript("window.scrollTo(0,300)", "");
		System.out.println("Table 1:");
		List<WebElement> lst = driver.findElements(By.xpath(p.getProperty("tab1")));
		for(int i=1;i<lst.size();i++) {
			/*String data1 = driver.findElement(By.xpath("//table[@name='courses']/tbody/tr["+i+"]/td[1]")).getText();
			String data2 = driver.findElement(By.xpath("//table[@name='courses']/tbody/tr["+i+"]/td[2]")).getText();
			String data3 = driver.findElement(By.xpath("//table[@name='courses']/tbody/tr["+i+"]/td[3]")).getText();
			System.out.println(data1+"\t"+data2+"\t"+data3);*/
			System.out.println(lst.get(i).getText());
		}
	}
	
	@Test(priority = 9, dependsOnMethods = "getDriver")
	public void hideShow() {
		driver.findElement(By.id("hide-textbox")).click();
		try {
			Thread.sleep(3000);
			driver.findElement(By.id("show-textbox")).click();
			Thread.sleep(3000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
	
	@Test(priority = 10, dependsOnMethods = "getDriver")
	public void table2() {
		System.out.println("Table2:");
		List<WebElement> lst = driver.findElements(By.xpath(p.getProperty("tab2")));
		System.out.println(lst.get(1).getText());
	}
	
	@Test(priority = 11, dependsOnMethods = "getDriver")
	public void hover() {
		JavascriptExecutor js = (JavascriptExecutor)driver;
		js.executeScript("window.scrollTo(0,1200)", "");
		Actions ac = new Actions(driver);
		ac.moveToElement(driver.findElement(By.id("mousehover"))).build().perform();
		try {
			Thread.sleep(3000);
			driver.findElement(By.xpath(p.getProperty("hover"))).click();
			Thread.sleep(3000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	@Test(priority = 12, dependsOnMethods = "getDriver")
	public void Frame() {
		JavascriptExecutor js = (JavascriptExecutor)driver;
		js.executeScript("window.scrollTo(0,1500)", "");
		try {
			Thread.sleep(3000);
			driver.switchTo().frame(driver.findElement(By.id("courses-iframe")));
			//driver.findElement(By.id("closeHeader")).click();
			js.executeScript("window.scrollTo(0,200)", "");
			System.out.println();
			System.out.println(driver.findElement(By.xpath(p.getProperty("frame"))).getText());
			Thread.sleep(3000);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	@AfterClass
	public void closeDriver() {
		driver.quit();
	}

	/*public static void main(String[] args) {
		Scanner sc = new Scanner(System.in);
		HomePage hp = new HomePage();
		System.out.println("Enter Chrome or Edge: ");
		String str = sc.nextLine();
		hp.getDriver(str);
		hp.chkRadio();
		hp.slctCountry("India");
		hp.dropDown();
		hp.check();
		hp.winHandle();
		hp.openTab();
		hp.chkAlert();
		hp.tableView();
		System.out.println();
		hp.hideShow();
		hp.table2();
		hp.hover();
		hp.Frame();
		driver.quit();
	}*/

}

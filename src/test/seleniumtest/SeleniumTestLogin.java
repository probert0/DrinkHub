package test.seleniumtest;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;

public class SeleniumTestLogin {

	
	public String logTest(String u, String p) {
		// @Roberto Prete
		System.setProperty("webdriver.chrome.driver","Drivers\\chromedriver.exe");

		WebDriver driver = new ChromeDriver();
		
		driver.get("localhost:8080/DrinkHub/");
		driver.findElement(By.xpath("//*[@id=\"username\"]")).sendKeys(u);
		driver.findElement(By.xpath("//*[@id=\"password\"]")).sendKeys(p);
		driver.findElement(By.xpath("//*[@id=\"login\"]/fieldset/input")).click();
		driver.findElement(By.xpath("//*[@id=\"container\"]/form[2]/input")).click();
		WebElement txtBoxContent = driver.findElement(By.xpath("/html/body/h2[2]"));
		driver.close();
		
		return txtBoxContent.getText();
	}
}

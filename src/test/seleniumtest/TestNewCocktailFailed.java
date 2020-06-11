package test.seleniumtest;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;


public class TestNewCocktailFailed {
	
	
	public String newCocktailFailedT(String username, String password) {
		// Francesca Carpineta
		System.setProperty("webdriver.chrome.driver", "Drivers\\chromedriver.exe");
		
		WebDriver driver = new ChromeDriver();
		
		driver.get("localhost:8080/DrinkHub/");
		
		driver.findElement(By.xpath("//*[@id=\"username\"]")).sendKeys(username);
		driver.findElement(By.xpath("//*[@id=\"password\"]")).sendKeys(password);
		driver.findElement(By.xpath("//*[@id=\"login\"]/fieldset/input")).click();
		driver.findElement(By.xpath("//*[@id=\"container\"]/form[3]/input")).click();
		driver.findElement(By.xpath("//*[@id=\"formPost\"]/form/input")).click();
		WebElement txtBoxContent = driver.findElement(By.xpath("/html/body/h4[2]/font"));
		String notValid = txtBoxContent.getText();
		
		driver.close();

		return notValid;
	
	}

}

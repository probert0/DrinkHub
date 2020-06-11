package test.seleniumtest;


import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;

public class TestPosit {
	
	public String positionT(String username, String password) {
		// Adriano Morini
		System.setProperty("webdriver.chrome.driver","Drivers\\chromedriver.exe");
	
		WebDriver driver = new ChromeDriver();
    
		driver.get("localhost:8080/DrinkHub/");
		
		driver.findElement(By.xpath("//*[@id=\"username\"]")).sendKeys(username);
		driver.findElement(By.xpath("//*[@id=\"password\"]")).sendKeys(password);
		driver.findElement(By.xpath("//*[@id=\"login\"]/fieldset/input")).click();
		
		driver.findElement(By.xpath("//*[@id=\"container\"]/form[5]/input")).click();
		
		WebElement txtBoxContent = driver.findElement(By.xpath("//*[@id=\"map\"]/div/div/div[1]/div[3]/div/div[4]/div[6]/div/div/div/div/div")); //legge contenuto label
		String h = txtBoxContent.getText();
		driver.close();
		return h;
	}
}

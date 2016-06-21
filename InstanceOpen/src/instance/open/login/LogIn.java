package instance.open.login;

import org.jdom.Element;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.htmlunit.HtmlUnitDriver;
import com.gargoylesoftware.htmlunit.BrowserVersion;

public class LogIn {
	public static String devstant =  "https://developer.servicenow.com/app.do#!/instance?wu=true",
			 ur = null;
	public static Element urlin;
	
	public static void main(String[] args) throws Exception {
		String[] textuser = new String[] {"leury.sanchez@softtek.com", "dev.team.app2@gmail.com"};
		String[] textpass = new String[] {"Test.123", "T35t@test"};
        
        for(int nu = 0; nu <= 1; nu++) {
        	System.out.println("Start instance wake up");
        	System.out.println(textuser[nu]);
			final WebDriver driver = new HtmlUnitDriver(BrowserVersion.CHROME);
	        ((HtmlUnitDriver) driver).setJavascriptEnabled(true);
	        driver.manage().window().maximize();
        	driver.get(devstant);
	        Thread.sleep(5000);
	        do {
	        	ur = driver.getCurrentUrl();
	        }while(ur == devstant);
	        Thread.sleep(15000);
			WebElement user = driver.findElement(By.xpath("//*[@id='username']"));
			user.sendKeys(textuser[nu]);
			WebElement pass = driver.findElement(By.xpath("//*[@id='password']"));
			pass.sendKeys(textpass[nu]);
			driver.findElement(By.xpath("//*[@id='submitButton']")).click();
			Thread.sleep(40000);
			if(driver.findElement(By.xpath("//*[@id='instanceWakeUpBtn']")).isDisplayed()) {
				System.out.println("Instance was sleeping, waking up");
				driver.findElement(By.xpath("//*[@id='instanceWakeUpBtn']")).click();
			} else {
				System.out.println("Instance is already up");
			}
			Thread.sleep(20000);
			System.out.println("END");
			driver.quit();
        }
	}
}
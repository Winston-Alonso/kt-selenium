package createtickets;

import jxl.Cell;
import jxl.Sheet;
import jxl.Workbook;
import java.io.File;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.firefox.FirefoxBinary;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxProfile;

public class CreateSNTickets {
	public static String devstant =  "https://developer.servicenow.com/app.do#!/instance?wu=true",
						 sernow = "https://dev25981.service-now.com", //"https://dev17387.service-now.com/", "https://dev22804.service-now.com/",
						 ur = null,
			 			 name = null,
			 			 browser = "C:\\Users\\winston.alonso\\AppData\\Local\\Mozilla Firefox\\firefox.exe";
	public static WebElement user, pass, log, nwticket, numticket, selinput, texts, submittk, openby, assigto, tabopt;
	public static Select selbox, opclosed;
	public static int opt = 0, acti = 0, oldate = 0;
	public static JavascriptExecutor js;
    
	public static void main(String[] args) throws Exception {
		Workbook workbook = Workbook.getWorkbook(new File(".\\src\\ARCA-DemoServicenow-datos.xls"));
	    Sheet sheet = workbook.getSheet("Demo ARCA - datos");
	    
	    File pathToBinary = new File(browser);
        FirefoxBinary ffBinary = new FirefoxBinary(pathToBinary);
        FirefoxProfile firefoxProfile = new FirefoxProfile();
        FirefoxDriver driver = new FirefoxDriver(ffBinary,firefoxProfile);
        driver.manage().window().maximize();
        
        driver.get(devstant);
        Thread.sleep(10000);
        do {
        	ur = driver.getCurrentUrl();
        }while(ur == sernow);
        //Start the Instance
        WebElement user = driver.findElement(By.xpath("//*[@id='username']"));
		user.sendKeys("leury.sanchez@softtek.com");
        //user.sendKeys("dev.team.app2@gmail.com");
		WebElement pass = driver.findElement(By.xpath("//*[@id='password']"));
		pass.sendKeys("Test.123");
		//pass.sendKeys("T35t@test");
		driver.findElement(By.xpath("//*[@id='submitButton']")).click();
		acti++;
		/*if(driver.findElement(By.xpath("//*[@id='instanceWakeUpBtn']")).isDisplayed()) {
			driver.findElement(By.xpath("//*[@id='instanceWakeUpBtn']")).click();
		}*/
		Thread.sleep(40000);
        
		driver.get(sernow);
        
		//Access to the Instance
        driver.switchTo().defaultContent();
        driver.switchTo().frame(driver.findElement(By.xpath("//*[@id='gsft_main']")));
        Thread.sleep(5000);
        user = driver.findElement(By.xpath("//*[@id='user_name']"));
        //user.sendKeys("Admin");
        user.sendKeys("admin");
        pass = driver.findElement(By.xpath("//*[@id='user_password']"));
        pass.sendKeys("Test.123");
        log = driver.findElement(By.xpath("//*[@id='sysverb_login']"));
        log.click();
        
        //----------------- https://dev22804.service-now.com/ and https://dev17387.service-now.com/ -----------------
        /*js = (JavascriptExecutor) driver;
        clise(js);*/
        
        //----------------- https://dev25981.service-now.com -----------------
        Thread.sleep(20000);
        driver.findElement(By.xpath("//*[@id='gsft_nav']/div/magellan-favorites-list/ul/li[7]/div/ul/li[1]/a/div")).click();
        Thread.sleep(10000);
        
        driver.switchTo().defaultContent();
        driver.switchTo().frame(driver.findElement(By.xpath("//*[@id='gsft_main']")));
	    for(int j = 2; j < sheet.getRows(); j++) {
	    	for(int k = 2; k < sheet.getColumns(); k++) {
	            Cell cell = sheet.getCell(k, j);
	            name = cell.getContents();
	            if(!name.isEmpty()) {
	            	switch(k) {
	            		case 2:
	            			selbox = new Select(driver.findElement(By.xpath("//*[@id='incident.category']")));
		            		selbox.selectByValue(name.toLowerCase());
		            		break;
	            		case 3:
	            			selbox = new Select(driver.findElement(By.xpath("//*[@id='incident.urgency']")));
	            			opt = leve(name);
		            		selbox.selectByValue(Integer.toString(opt));
		            		break;
	            		case 4:
		            		break;
	            		case 5:
	            			selbox = new Select(driver.findElement(By.xpath("//*[@id='incident.impact']")));
	            			opt = leve(name);
		            		selbox.selectByValue(Integer.toString(opt));
		            		break;
	            		case 6:
	        	            selbox = new Select(driver.findElement(By.xpath("//*[@id='incident.state']")));
	        	            name = name.replaceAll(" ", "");
	            			opt = statenum(name);
	            			selbox.selectByValue(Integer.toString(opt));
	            			/*openby = driver.findElement(By.xpath("//*[@id='sys_display.incident.opened_by']"));
	        	            openby.clear();
	        	            openby.sendKeys("Logan Muhl");
	        	            Thread.sleep(3000);
	        	            driver.findElement(By.xpath("//*[@id='label.incident.opened_by']/label/span[2]")).click();
	        	            if(!name.equals("New")) {
	            				assigto = driver.findElement(By.xpath("//*[@id='sys_display.incident.assigned_to']"));
	            				assigto.clear();
	            				assigto.sendKeys("Oma Duffy");
	            				Thread.sleep(3000);
	            				driver.findElement(By.xpath("//*[@id='label.incident.assigned_to']/label/span[2]")).click();
	            			}*/
	        	            
	        	            //CLOSED
	            			tabopt = driver.findElement(By.xpath("//*[@id='tabs2_section']/span[3]/span/span[2]"));
	            			tabopt.click();
	            			Thread.sleep(1500);
	        	            opclosed = new Select(driver.findElement(By.xpath("//*[@id='incident.close_code']")));
	        	            opclosed.selectByValue("Solved (Work Around)");
	        	            texts = driver.findElement(By.xpath("//*[@id='incident.close_notes']"));
	            			texts.clear();
	            			texts.sendKeys("TEST CLOSED");
		            		break;
	            		case 7:
	            			selbox = new Select(driver.findElement(By.xpath("//*[@id='incident.escalation']")));
	            			opt = escatenum(name);
	            			selbox.selectByValue(Integer.toString(opt));
		            		break;
	            		case 8:
	            			texts = driver.findElement(By.xpath("//*[@id='incident.short_description']"));
	            			texts.clear();
	            			texts.sendKeys(name);
		            		break;
	            		case 9:
	            			tabopt = driver.findElement(By.xpath("//*[@id='tabs2_section']/span[1]/span/span[2]"));
	            			tabopt.click();
	            			Thread.sleep(1500);
	            			texts = driver.findElement(By.xpath("//*[@id='incident.comments']"));
	            			texts.clear();
	            			texts.sendKeys(name);
		            		break;
	            		case 10:
	            			if(name != "") {
		            			texts = driver.findElement(By.xpath("//*[@id='incident.opened_at']"));
		            			texts.clear();
		            			texts.sendKeys(name);
		            			Thread.sleep(2000);
	            				driver.findElement(By.xpath("//*[@id='label.incident.opened_at']/label/span[2]")).click();
	            				
	            				//CLOSED
	            				tabopt = driver.findElement(By.xpath("//*[@id='tabs2_section']/span[3]/span/span[2]"));
		            			tabopt.click();
		            			Thread.sleep(1500);
	            				texts = driver.findElement(By.xpath("//*[@id='incident.closed_at']"));
		            			texts.clear();
		            			texts.sendKeys(name);
		            			Thread.sleep(2000);
	            				driver.findElement(By.xpath("//*[@id='label.incident.closed_at']/label/span[2]")).click();
	            			}
		            		break;
	            	}
	            }
	    	}
	    	submittk = driver.findElement(By.xpath("//*[@id='sysverb_insert']"));
            submittk.click();
	    	
            //----------------- https://dev22804.service-now.com/ and https://dev17387.service-now.com/ -----------------
            /*Thread.sleep(10000);
            driver.get(sernow);
            Thread.sleep(10000);
            js = (JavascriptExecutor) driver;
            clise(js);
            Thread.sleep(10000);
            driver.switchTo().defaultContent();
            driver.switchTo().frame(driver.findElement(By.xpath("//*[@id='gsft_main']")));*/
            
            //----------------- https://dev25981.service-now.com -----------------
            Thread.sleep(10000);
            driver.get(sernow);
            Thread.sleep(10000);
            driver.findElement(By.xpath("//*[@id='gsft_nav']/div/magellan-favorites-list/ul/li[7]/div/ul/li[1]/a/div")).click();
            Thread.sleep(10000);
            driver.switchTo().defaultContent();
            driver.switchTo().frame(driver.findElement(By.xpath("//*[@id='gsft_main']")));
        }
	    driver.quit();
    }
	
	public static void clise(JavascriptExecutor js) {
        js.executeScript("kop = window.frames['gsft_nav'].document.getElementById('14641d70c611228501114133b3cc88a1').click();");
	} 
	
	public static int leve(String levsec) {
		int stval = 0;
		switch(levsec) {
			case "1 - High":
				stval = 1;
				break;
			case "2 - Medium":
				stval = 2;
				break;
			case "3 - Low":
				stval = 3;
				break;
		}
		return stval;
	}
	
	public static int statenum(String namtex) {
		int stval = 0;
		switch(namtex) {
			case "New":
				stval = 1;
				break;
			case "Active":
				stval = 2;
				break;
			case "AwaitingProblem":
				stval = 3;
				break;
			case "AwaitingUserInfo":
				stval = 4;
				break;
			case "AwaitingEvidence":
				stval = 5;
				break;
			case "Resolved":
				stval = 6;
				break;
			case "Closed":
				stval = 7;
				break;
		}
		return stval;
	}
	
	public static int escatenum(String namtex) {
		int stval = 0;
		switch(namtex) {
			case "Normal":
				stval = 0;
				break;
			case "Moderate":
				stval = 1;
				break;
			case "High":
				stval = 2;
				break;
			case "Overdue":
				stval = 3;
				break;
		}
		return stval;
	}
}
package emailssharepoint;
/*
 * @author winston.alonso
 */

import connection.ConnectionDB;
import java.io.File;
import java.util.List;
import javax.swing.JPanel;
import javax.swing.JLabel;
import java.awt.GridLayout;
import java.io.IOException;
import java.text.Normalizer;
import java.awt.BorderLayout;
import org.jdom.JDOMException;
import org.openqa.selenium.By;
import javax.swing.JTextField;
import javax.swing.JOptionPane;
import javax.swing.JPasswordField;
import javax.swing.SwingConstants;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxBinary;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxProfile;

public class EmailsSharepoint {
    public static String[] contArray = new String[4], conttArray = new String[5];
    public static final String nave = "C:\\Users\\winston.alonso\\AppData\\Local\\Mozilla Firefox\\firefox.exe";
    public static final String link = "intrasoft.softtek.com/intrasoft/PageLoader.aspx?PageId=164&ReportId=2&ReportVersionId=1&ResourceName=";
    public static final String link2 = "https://onesofttek.sharepoint.com/sites/SKCams/tm/Lists/PersonalPreferences/ExportContact.aspx#InplviewHash1d9c0919-8b7d-4f80-a3a1-d0a02355acf6=Paged%3DTRUE-p_Modified%3D20160314%252022%253a32%253a33-p_ID%3D116-ShowInGrid%3DTrue-PageFirstRow%3D1";
    public static String info = null, reloc = null, locat = null, movis = null, englev = null, gend = null;
    public static int co = 0, cos = 0 , i = 0, j = 0, js = 1, ind = 1, te = 0, sing = 0;
    public static FirefoxDriver driver;
    public static List<WebElement> names = null;
    
    public static void main(String[] args) throws JDOMException, IOException, ClassNotFoundException, InterruptedException {
        ConnectionDB nametable = new ConnectionDB();
        String username="", pas= "";
        File pathToBinary = new File(nave);
        FirefoxBinary ffBinary = new FirefoxBinary(pathToBinary);
        FirefoxProfile firefoxProfile = new FirefoxProfile();
        FirefoxDriver driver = new FirefoxDriver(ffBinary,firefoxProfile);
        driver.manage().window().maximize();
        JPanel panel = new JPanel(new BorderLayout(5, 5));
        JPanel label = new JPanel(new GridLayout(0, 1, 20, 20));
        label.add(new JLabel("E-Mail", SwingConstants.RIGHT));
        label.add(new JLabel("Password", SwingConstants.RIGHT));
        panel.add(label, BorderLayout.WEST);
        JPanel controls = new JPanel(new GridLayout(0, 1, 2, 2));
        JTextField usename = new JTextField();
        controls.add(usename);
        JPasswordField password = new JPasswordField();
        controls.add(password);
        panel.add(controls, BorderLayout.CENTER);
        JOptionPane.showMessageDialog(null, panel, "LOGIN", JOptionPane.QUESTION_MESSAGE);
        username = usename.getText() + "@softtek.com";
        pas = new String(password.getPassword());
        try {
            driver.get(link2);
            driver.findElement(By.xpath("//*[@id='cred_userid_inputtext']")).clear();
            driver.findElement(By.xpath("//*[@id='cred_userid_inputtext']")).sendKeys(username);
            driver.findElement(By.xpath("//*[@id='cred_password_inputtext']")).clear();
            Thread.sleep(1000);
            driver.findElement(By.xpath("//*[@id='aad_account_tile']/tbody/tr/td[2]/div[1]")).click();
            driver.findElement(By.xpath("//*[@id='cred_password_inputtext']")).sendKeys(pas);
            Thread.sleep(1000);
            driver.findElement(By.xpath("//*[@id='cred_sign_in_button']")).click();
            do{
                Thread.sleep(9000);
                names = driver.findElements(By.xpath("//*[@id='spgridcontainer_WPQ1_leftpane_mainTable']/tbody/tr/td[2]//a[@class='ms-subtleLink']"));
                FirefoxDriver driv = new FirefoxDriver(ffBinary,firefoxProfile);
                for(WebElement texname : names) {
                    String tname = texname.getText();
                    String name = delaccents(tname);
                    driv.manage().window().maximize();
                    driv.get("https://" + username + ":" + pas + "@" + link + name);
                    for(i=2; i<=5; i++) {
                        try {
                            info = driv.findElement(By.xpath("//*[@class='ItemStyle']//td[" + i + "]")).getText();
                            contArray[co] = info;
                            co++;
                        }  catch (Exception ioe) {
                            i = 6;
                        }
                    }
                    js++;
                    do {
                        switch (ind) {
                            case 1: te=5;
                                    break;
                            case 2: te=7;
                                    break;
                            case 3: te=9;
                                    break;
                            case 4: te=10;
                                    break;
                            case 5: te=13;
                                    break;
                        }
                        if(cos <= 5) {
                            reloc = driver.findElement(By.xpath("//*[@id='spgridcontainer_WPQ1_leftpane_mainTable']/tbody/tr[" + js + "]/td[" + te + "]/span")).getText();
                            conttArray[cos] = reloc;
                            cos++;
                            ind++;
                        }
                        if(te == 13) {
                            cos=0;
                            ind=1;
                            js=1;
                        }
                    } while(te != 13);
                    nametable.contable(contArray, conttArray);
                    co=0;
                    cos=0;
                }
                try {
                    driver.findElement(By.xpath("//*[@id='pagingWPQ1next']/a")).click();
                    sing = 1;
                } catch (Exception ioe) {
                    sing = 0;
                }
                driv.quit();
                names = null;
            }while(sing == 1);
        } catch (Exception ioe) {
            ioe.printStackTrace();
        }
        driver.quit();
    }
    
    public static String delaccents(String nam) {
        nam = Normalizer.normalize(nam, Normalizer.Form.NFD);
        nam = nam.replaceAll("[^\\p{ASCII}]", "");
        nam = nam.replaceAll("\\p{M}", "");
        return nam;
    }
}
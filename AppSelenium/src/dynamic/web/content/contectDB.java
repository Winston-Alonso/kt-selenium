package dynamic.web.content;
/*
 * @author winston.alonso
 */

import java.sql.*;
import java.io.File;
import java.net.URL;
import java.util.List;
import org.jdom.Element;
import org.jdom.Document;
import java.util.ArrayList;
import java.io.IOException;
import org.jdom.JDOMException;
import org.xml.sax.SAXException;
import org.jdom.input.SAXBuilder;
import java.net.HttpURLConnection;
import org.openqa.selenium.WebElement;
import java.net.MalformedURLException;
import org.openqa.selenium.firefox.FirefoxDriver;
import javax.xml.parsers.ParserConfigurationException;

public class contectDB {
    public FirefoxDriver driver;
    public static String list;
    private String db = "test", url = "jdbc:mysql://localhost/" + db, user = "root", pass = "", contt;
    private Connection conexion;
    public Statement st;
    public int conta;
    public String[] namdb;
    
    public void conection() throws SQLException, ClassNotFoundException {
	Class.forName("com.mysql.jdbc.Driver");
        conexion = DriverManager.getConnection(this.url, this.user, this.pass);
        st = conexion.createStatement();
        //st.executeUpdate("create database if not exists " + dbname + ";");
        return;
    }
    
    public void conectionuse(String dbname) throws SQLException, ClassNotFoundException {
	Class.forName("com.mysql.jdbc.Driver");
        url = "jdbc:mysql://localhost/" + dbname;
        conexion = DriverManager.getConnection(this.url, this.user, this.pass);
        st = conexion.createStatement();
        return;
    }

    public void dbname(String dbname) throws ClassNotFoundException, SQLException {
        conection();
        st.executeUpdate("create database if not exists " + dbname + ";");
        st.executeUpdate("use " + dbname + ";");
        st.close();
    }
    
    public void datetable(String table, String dbname) throws ClassNotFoundException, SQLException {
	conectionuse(dbname);
        st.executeUpdate("use " + dbname + ";");
        st.executeUpdate("CREATE TABLE IF NOT EXISTS " + table
                       + " (`Id` int(255) NOT NULL AUTO_INCREMENT, "
	               + "`Content` text(2550) NOT NULL, "
                       + "`Status` text(2550) NOT NULL, "
	               + "PRIMARY KEY (`Id`));");
        st.close();
    }

    public void subtable(List<WebElement> contsub, String inftab, String dbname) throws SQLException, ClassNotFoundException, MalformedURLException, IOException, JDOMException, ParserConfigurationException, SAXException {
        conectionuse(dbname);
        
        if("texts".equals(inftab)) {
            for(WebElement content : contsub) {
                String contt = content.getText();
                contt = contt.replaceAll("[:|'|\\(|\\)|-|_]", " ");
                if(!contt.isEmpty()) {
                    ResultSet rs = st.executeQuery("select * from " + inftab + " where `Content`='" + contt + "';");
                    if(rs.next() != true) {
                        st.executeUpdate("INSERT INTO " + inftab + " (`Content`,`Status`) "
                                       + "VALUES ('" + contt + "','OK');");
                    }
                }
            }
        }
        
        if("images".equals(inftab)) {
            for(WebElement content : contsub) {
                contt = content.getAttribute("src");
                String titstatus = statusof(contt);
                if(!contt.isEmpty()) {
                    String[] jo = contt.split("[\\.|\\:|\\?]");
                    for(int ko=0; ko<jo.length; ko++) {
                        if(!jo[ko].equals("svg")) {
                            ResultSet rs = st.executeQuery("select * from " + inftab + " where `Content`='" + contt + "';");
                            if(rs.next() != true) {
                                st.executeUpdate("INSERT INTO " + inftab + " (`Content`,`Status`) "
                                               + "VALUES ('" + contt + "','" + titstatus + "');");
                            }
                        } else {
                            ko = jo.length;
                        }
                    }
                }
            }
        }
        
        if("links".equals(inftab)) {
            for(WebElement content : contsub) {
                if(content.getAttribute("href") != null) {
                    contt = content.getAttribute("href");
                    if(!contt.equals("")) {
                        String[] jo = contt.split("[\\.|\\:]");
                        for(int ko=0; ko<jo.length; ko++) {
                            if(!jo[ko].equals("http")) {
                                if(!jo[ko].equals("https")) {
                                    if(!jo[ko].equals("javascript")) {
                                        if(!jo[ko].equals("pdf")) {
                                            if(!jo[ko].equals("docs")) {
                                                if(!jo[ko].equals("gif")) {
                                                    if(!jo[ko].equals("//localhost")) {
                                                        ResultSet rs = st.executeQuery("select * from " + inftab + " where `Content`='" + contt + "';");
                                                        if(rs.next() != true) {
                                                            String titstatus = statusof(contt);
                                                            st.executeUpdate("INSERT INTO " + inftab + " (`Content`,`Status`) "
                                                                           + "VALUES ('" + contt + "','" + titstatus + "');");
                                                        }
                                                    } else {
                                                        ko = jo.length;
                                                    }
                                                }
                                            }
                                        }
                                    } else {
                                        ko = jo.length;
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
        
        if("pdfs".equals(inftab)) {
            for(WebElement content : contsub) {
                if(content.getAttribute("href") != null) {
                    contt = content.getAttribute("href");
                    String[] jo = contt.split("\\.");
                    for(int ko=0; ko<jo.length; ko++) {
                        if(!jo[ko].equals("javascript")) {
                            if(jo[ko].equals("pdf")) {
                                ResultSet rs = st.executeQuery("select * from " + inftab + " where `Content`='" + contt + "';");
                                if(rs.next() != true) {
                                    String titstatus = statusof(contt);
                                    st.executeUpdate("INSERT INTO " + inftab + " (`Content`,`Status`) "
                                                   + "VALUES ('" + contt + "','" + titstatus + "');");
                                }
                            }
                        } else {
                            ko = jo.length;
                        }
                    }   
                }
            }
        }
        
        if("docs".equals(inftab)) {
            for(WebElement content : contsub) {
                if(content.getAttribute("href") != null) {
                    contt = content.getAttribute("href");
                    String[] jo = contt.split("\\.");
                    for(int ko=0; ko<jo.length; ko++) {
                        if(!jo[ko].equals("javascript")) {
                            if(jo[ko].equals("docx")) {
                                ResultSet rs = st.executeQuery("select * from " + inftab + " where `Content`='" + contt + "';");
                                if(rs.next() != true) {
                                    String titstatus = statusof(contt);
                                    st.executeUpdate("INSERT INTO " + inftab + " (`Content`,`Status`) "
                                                   + "VALUES ('" + contt + "','" + titstatus + "');");
                                }
                            }
                        } else {
                            ko = jo.length;
                        }
                    }
                }
            }
        }
        
	st.close();
    }
    
    public String statusof(String codst) throws MalformedURLException, IOException, JDOMException, ParserConfigurationException, SAXException {
        String sta = null, errtex = null;
        URL siteURL = new URL(codst);
        HttpURLConnection connection = (HttpURLConnection)siteURL.openConnection();
        int code = connection.getResponseCode();
        SAXBuilder builder = new SAXBuilder();
        File xmlFile = new File("./src/access/statusWeb.xml");
        Document document = (Document) builder.build(xmlFile);
        Element rootNode = document.getRootElement();
        sta = "ST" + code;
        list = rootNode.getChildText(sta);
        errtex = list;
        return errtex;
    }
    
    public ArrayList<String> searchdata(String textserch, String dbname) throws SQLException, ClassNotFoundException {
        conectionuse(dbname);
        String s = null;
        ArrayList<String> texts = new ArrayList<String>();
        int x = 0;
        PreparedStatement st = conexion.prepareStatement("select * from texts where Content like '%" + textserch + "%';");
        ResultSet rs = st.executeQuery();
        while(rs.next()) {
            s = rs.getString(2);
            texts.add(s);
        }
        st.close();
        return texts;
    }
    
    public ArrayList<String> showimages(String serchinfo, String dbname) throws SQLException, ClassNotFoundException {
        conectionuse(dbname);
        ArrayList<String> imagesview = new ArrayList<String>();
        String s = null;
        PreparedStatement st = conexion.prepareStatement("select * from " + serchinfo + "");
        ResultSet rs = st.executeQuery();
        while(rs.next()) {
            s = rs.getString(2);
            imagesview.add(s);
        }
        st.close();
        return imagesview;
    }
    
    public ArrayList<String> showdb(String serchinfo, String dbname) throws SQLException, ClassNotFoundException {
        conection();
        ArrayList<String> imagesview = new ArrayList<String>();
        String s = null;
        PreparedStatement st = conexion.prepareStatement("show databases;");
        ResultSet rs = st.executeQuery();
        while(rs.next()) {
            s = rs.getString(1);
            imagesview.add(s);
        }
        st.close();
        return imagesview;
    }
    
    public ArrayList<String> showtable(String dbname) throws SQLException, ClassNotFoundException {
        conection();
        ArrayList<String> imagesview = new ArrayList<String>();
        String s = null;
        PreparedStatement stab = conexion.prepareStatement("use " + dbname + ";");
        stab.executeQuery();
        PreparedStatement st = conexion.prepareStatement("show tables;");
        ResultSet rs = st.executeQuery();
        while(rs.next()) {
            s = rs.getString(1);
            imagesview.add(s);
        }
        st.close();
        return imagesview;
    }
}
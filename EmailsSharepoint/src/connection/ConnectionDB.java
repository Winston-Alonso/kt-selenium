package connection;
/*
 * @author winston.alonso
 */

//import jxl.Workbook;
//import java.io.File;
//import java.util.Locale;
//import java.io.FileWriter;
import java.sql.Statement;
import java.sql.ResultSet;
import java.sql.Connection;
import java.io.IOException;
import jxl.WorkbookSettings;
import java.sql.SQLException;
import java.sql.DriverManager;
import jxl.write.WritableSheet;
import jxl.write.WriteException;
import jxl.write.WritableWorkbook;
import org.openqa.selenium.firefox.FirefoxDriver;

public class ConnectionDB {
    public FirefoxDriver driver;
    public static String list;
    //private String db = "trace", url = "jdbc:mysql://linuxamsvm.cloudapp.net/" + db, user = "directory_user", pass = "p4ssw0rd";
    private String db = "sharepointdb", url = "jdbc:mysql://localhost/" + db, user = "root", pass = "", filename = "";
    private Connection conexion;
    public Statement st;
    public int conta = 0, fi = 0;
    public Statement stmt = null;
    public WorkbookSettings ws;
    public WritableWorkbook workbook;
    public WritableSheet s;
    
    public void conection() throws SQLException, ClassNotFoundException {
	Class.forName("com.mysql.jdbc.Driver");
        conexion = DriverManager.getConnection(this.url, this.user, this.pass);
        st = conexion.createStatement();
        return;
    }
    
    public void contable(String[] contt, String[] cont) throws SQLException, ClassNotFoundException, IOException, WriteException {
        //conection();
        /*if(fi == 0) {
            /*filename = ".\\src\\trace-db.xls";
            ws = new WorkbookSettings();
            ws.setLocale(new Locale("en", "EN"));
            workbook = Workbook.createWorkbook(new File(filename), ws);
            s = workbook.createSheet("Sheet1", 0);*
            fi++;
            workbook.close();
        }*/
        /*ResultSet rs = st.executeQuery("select * from users where `IS`='" + contt[0] + "';");
        if(rs.next() != true) {
            /*st.executeUpdate("INSERT INTO users (`first_name`, `last_name`, `phone`, `email`, `photo`, `latitude`, `longitude`, `allow_tracking`, `gcm_regid`, `apns_regid`, `status`, `IS`, `Role`, `Birthday`) "
                           + "VALUES ('" + contt[1] + "','" + contt[2] + "','" + cont[3] + "','" + contt[3] + "','img/avatar/default.png','0','0','true','','','active','"
                                         + contt[0] + "','','0000-00-00');");*/
            
            /*FileWriter salida = new FileWriter(filename);
            salida.write("INSERT INTO users (`first_name`, `last_name`, `phone`, `email`, `photo`, `latitude`, `longitude`, `allow_tracking`, `gcm_regid`, `apns_regid`, `status`, `IS`, `Role`, `Birthday`) "
                       + "VALUES ('" + contt[1] + "','" + contt[2] + "','" + cont[3] + "','" + contt[3] + "','img/avatar/default.png','0','0','true','','','active','"
                                     + contt[0] + "','','0000-00-00');");
            salida.write("\n");
            salida.close();*
        }*/
        System.out.println("INSERT INTO users (`first_name`, `last_name`, `phone`, `email`, `photo`, `latitude`, `longitude`, `allow_tracking`, `gcm_regid`, `apns_regid`, `status`, `IS`, `Role`, `Birthday`) "
                         + "VALUES ('" + contt[1] + "','" + contt[2] + "','" + cont[3] + "','" + contt[3] + "','img/avatar/default.png','0','0','true','','','active','"
                                       + contt[0] + "','','0000-00-00');");
	//st.close();
    }
}
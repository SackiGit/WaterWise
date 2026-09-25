
import java.sql.*;
import java.util.Properties;

public class PortalConnection {

    // Set this to e.g. "portal" if you have created a database named portal
    // Leave it blank to use the default database of your database user
    static final String DBNAME = "daniel";
    // For connecting to the portal database on your local machine
    static final String DATABASE = "jdbc:postgresql://localhost/"+DBNAME;
    static final String USERNAME = "postgres";
    static final String PASSWORD = "postgres";

    private Connection conn;

    public PortalConnection() throws SQLException, ClassNotFoundException {
        this(DATABASE, USERNAME, PASSWORD);  
    }

    // Initializes the connection
    public PortalConnection(String db, String user, String pwd) throws SQLException, ClassNotFoundException {
        Class.forName("org.postgresql.Driver");
        Properties props = new Properties();
        props.setProperty("user", user);
        props.setProperty("password", pwd);
        conn = DriverManager.getConnection(db, props);
    }


    // Register a new user, returns a tiny JSON document (as a String)
    public String register(String UserName, String UserPassword){
      try (PreparedStatement ps = conn.prepareStatement(
        "INSERT INTO Users (UserName, UserPassword) VALUES (?, ?)"
      )){
        ps.setString(1, UserName);
        ps.setString(2, UserPassword);
        ps.executeUpdate();
        return "{\"success\":true}";
       } catch (SQLException e) {
          return "{\"success\":false, \"error\":\""+getError(e)+"\"}";
       }     
    }


     // Register water usage, returns a tiny JSON document (as a String)
    public String registerwater (int userId, String activity, String duration, String datum){
      try (PreparedStatement ps = conn.prepareStatement(
        "INSERT INTO ConsMinutes (Userid, Activity, Duration, Datum) VALUES (?, ?, ?, ?)"
      )){
        ps.setInt(1, userId);
        ps.setString(2, activity);
        ps.setString(3, duration);
        ps.setString(4, datum);
        ps.executeUpdate();
        return "{\"success\":true}";
       } catch (SQLException e) {
          return "{\"success\":false, \"error\":\""+getError(e)+"\"}";
       }     
    }


    public String getDailyConsumption (int userId, Date date){
      try (PreparedStatement ps = conn.prepareStatement(
        "SELECT TotalLitres " +
        "FROM DailyConsumption " +
        "WHERE Userid = ? AND Datum = ?"
      )){
        ps.setInt(1,userId);
        ps.setDate(2, date);
        
        ResultSet rs = ps.executeQuery();
        if(rs.next()){
          return "{\"Litres\":" + rs.getInt("TotalLitres") + "}";
        }
        return"{\"Litres\":0}";
         } catch (SQLException e) {
          return "{\"success\":false, \"error\":\""+getError(e)+"\"}";
       }     
      }
      
    public static String getError(SQLException e){
       String message = e.getMessage();
       int ix = message.indexOf('\n');
       if (ix > 0) message = message.substring(0, ix);
       message = message.replace("\"","\\\"");
       return message;
    }
}
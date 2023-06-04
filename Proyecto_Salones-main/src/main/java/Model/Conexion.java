package Model;

import java.sql.Connection;
import java.sql.DriverManager;

public class Conexion {

    private Connection connection;
    private  String user = "root";
    private String password = "";
    private String server = "localhost";
    private String port = "3306";
    private String nameDB = "horarios";
    private String url = "jdbc:mysql://"+server+":"+port+"/"+nameDB+"?serverTimezone=UTC";
    private String driver = "com.mysql.jdbc.Driver";

    public Conexion(){
        try {
            Class.forName(driver);
            connection = DriverManager.getConnection(url,user,password);
            if (connection != null){
                System.out.println("Connected :)");
            }

        } catch (Exception e) {
            System.err.println("Error not Connected :(");
            System.err.println("Message Error:"+ e.getMessage());
            System.err.println("Detail:");
            e.printStackTrace();
        }
    }
    public Connection getConnection(){
        return connection;
    }
}

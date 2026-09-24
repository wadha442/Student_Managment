package app.studentmanagment.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBConnection {

    private static final String URL =
            "jdbc:sqlserver://localhost:1433;"
          + "databaseName=StudentManagment;"
          + "integratedSecurity=true;"
          + "encrypt=false";

    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(URL);
    }
}
package util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConectaBD {

    private final String URL = "jdbc:mysql://us-cdbr-east-06.cleardb.net/heroku_457c9b12fec67e1";
    private final String DRIVER = "com.mysql.jdbc.Driver";
    private final String USER = "b04510eb392657";
    private final String PASS = "4d1df8ce";

    public Connection getConnection() throws SQLException {
        Connection c = null;
        try {
            Class.forName(DRIVER).newInstance();
            c = DriverManager.getConnection(URL, USER, PASS);
        } catch (ClassNotFoundException |
                IllegalAccessException |
                InstantiationException |
                SQLException e) {
            throw new SQLException(e.getMessage());
        }
        return c;
    }
}

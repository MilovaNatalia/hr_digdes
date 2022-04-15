package util;

import com.mchange.v2.c3p0.ComboPooledDataSource;

import java.beans.PropertyVetoException;
import java.sql.Connection;
import java.sql.SQLException;

public class C3p0DataSource {
    private static ComboPooledDataSource cpds = new ComboPooledDataSource();

    private C3p0DataSource() {
    }

    static {
        try {
            cpds.setDriverClass("org.postgresql.Driver");
            cpds.setJdbcUrl("jdbc:postgresql://localhost:5432/test_hr_digdes_db");
            cpds.setUser("hr_digdes_db_admin");
            cpds.setPassword("digdes");
        } catch (PropertyVetoException e) {
            e.printStackTrace();
            //todo: log this
        }

    }

    public static Connection getConnection() throws SQLException {
        return cpds.getConnection();
    }

    public static Connection getTestConnection(){
        return null;
    }
}

package com.ps.repository;

import java.sql.Connection;
import java.sql.SQLException;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;

public class DBConnectionManager {
    public static Connection getDBConnection() throws NamingException, SQLException {
        InitialContext initialContext=new InitialContext();
        DataSource dataSource=(DataSource) initialContext.lookup("java:comp/env/jdbc/");
        return dataSource.getConnection();
    }
}

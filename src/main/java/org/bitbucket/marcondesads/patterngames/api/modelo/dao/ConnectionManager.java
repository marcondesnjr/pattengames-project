package org.bitbucket.marcondesads.patterngames.api.modelo.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * Classe de criação de conexões.
 * @author José Marcondes do Nascimento Junior
 */
public abstract class ConnectionManager {
    private static Connection conn;
    
    public static Connection getConnection() throws SQLException{
        if(conn == null || conn.isClosed()){
            conn = DriverManager.getConnection("jdbc:postgresql://localhost:5432/pattern-games","postgres","123");
            conn.setAutoCommit(false);
        }
        return conn;
    }
}

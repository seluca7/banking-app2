package repository.impl;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class JDBConnectionWrapper {
    private static final String DB_URL = "jdbc:mysql://localhost:3306/";
    private static final String USER = "root";
    private static final String PASS = "";
    private static final int TIMEOUT = 5;

    private Connection connection;

    public JDBConnectionWrapper(String schemaName) {
        try {
            connection = DriverManager.getConnection(DB_URL + schemaName + "?useSSL=false", USER, PASS);
            createTables();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void createTables() throws SQLException {
        Statement statement = connection.createStatement();

        String sql = "CREATE TABLE IF NOT EXISTS user (" +
                "  id BIGINT(100) NOT NULL AUTO_INCREMENT," +
                "  username varchar(255) NOT NULL," +
                "  password varchar(255) NOT NULL," +
                "  admin BOOLEAN not null, " +
                "  PRIMARY KEY (id)," +
                "  UNIQUE KEY id_UNIQUE (id)" +
                ") ENGINE=InnoDB AUTO_INCREMENT=0 DEFAULT CHARSET=utf8;";
        statement.execute(sql);

        String acc = "CREATE TABLE IF NOT EXISTS account (" +
                "  id BIGINT(100) NOT NULL AUTO_INCREMENT," +
                "  balance double NOT NULL," +
                "  userId BIGINT(100)," +
                "  PRIMARY KEY (id)," +
                "  UNIQUE KEY id_UNIQUE (id)," +
                "  FOREIGN KEY (userId) REFERENCES user(id) ON DELETE CASCADE "+
                ") ENGINE=InnoDB AUTO_INCREMENT=0 DEFAULT CHARSET=utf8;";
        statement.execute(acc);

        String log = "CREATE TABLE IF NOT EXISTS log (" +
                "  id BIGINT(100) NOT NULL AUTO_INCREMENT," +
                "  description VARCHAR(150)," +
                "  userId BIGINT(100)," +
                "  PRIMARY KEY (id)," +
                "  UNIQUE KEY id_UNIQUE (id)," +
                "  FOREIGN KEY (userId) REFERENCES user(id) ON DELETE CASCADE "+
                ") ENGINE=InnoDB AUTO_INCREMENT=0 DEFAULT CHARSET=utf8;";
        statement.execute(log);
    }

    public boolean testConnection() throws SQLException {
        return connection.isValid(TIMEOUT);
    }

    public Connection getConnection() {
        return connection;
    }

}

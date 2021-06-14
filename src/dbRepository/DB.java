/**
 *
 * @author Jedaboy/Mateus Oliveira/Guilherme Leme
 */
package dbRepository;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.sql.*;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;

public class DB {

    private static Connection connection = null;
    private static Socket skt = null;
    static private ObjectInputStream oIS = null;
    static private ObjectOutputStream oOS = null;

    public static Connection getConnection() {
        if (connection == null) {
            try {
                Properties props = loadProperties();
                String url = props.getProperty("dburl");
                connection = DriverManager.getConnection(url, props);
            } catch (SQLException e) {
                throw new DbException(e.getMessage());
            }
        }
        return connection;
    }

    public static Socket getTCP() {
        if (skt == null) {
            try {
                Properties props = loadProperties();
                String url = props.getProperty("ngrok_url");
                int port = Integer.parseInt(props.getProperty("ngrok_port"));
                skt = new Socket(url, port);
                System.out.println(skt);

                oOS = new ObjectOutputStream(skt.getOutputStream());
                oIS = new ObjectInputStream(skt.getInputStream());

                return skt;
            } catch (IOException ex) {
                Logger.getLogger(DB.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        System.out.println(skt);
        return skt;
    }

    public static ObjectInputStream getInputStream() {
        return oIS;
    }

      public static ObjectOutputStream getOutputStream() {
        return oOS;
    }
    public static void closeConnection() {
        if (skt != null) {
            try {
                skt.close();
            } catch (IOException ex) {
                Logger.getLogger(DB.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    private static Properties loadProperties() {
        try ( FileInputStream fs = new FileInputStream("db.properties")) {
            Properties props = new Properties();
            props.load(fs);

            return props;
        } catch (IOException e) {
            throw new DbException(e.getMessage());
        }
    }

    public static void closeStatement(Statement st) {
        if (st != null) {
            try {
                st.close();
            } catch (SQLException e) {
                throw new DbException(e.getMessage());
            }
        }
    }

    public static void closeResultSet(ResultSet rs) {
        if (rs != null) {
            try {
                rs.close();
            } catch (SQLException e) {
                throw new DbException(e.getMessage());
            }
        }
    }
}

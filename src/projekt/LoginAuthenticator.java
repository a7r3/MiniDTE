package projekt;

import java.sql.*;

public class LoginAuthenticator {

    public static int id = 404;
    public static boolean isAdmin = false;

    private final String USER;
    private final String PASS;

    public LoginAuthenticator(String USER, String PASS) {
        this.USER = USER;
        this.PASS = PASS;
    }

    public boolean isRegistered() {

        Connection conn = null;
        Statement st;

        try {
            new org.mariadb.jdbc.Driver();
            conn = DriverManager.getConnection(Main.DB_URL, "root", "");
            String TAG = "LoginAuthenticator";
            if (conn != null)
                Main.log(TAG, "Connected to Database");
            else {
                Main.log(TAG, "Couldn't connect to Database");
                return false;
            }

            st = conn.createStatement();
            String sql;
            ResultSet rs;

            sql = "SELECT id from LOGIN WHERE email='" + USER + "' AND pass='" + PASS + "'";
            rs = st.executeQuery(sql);
            while (rs.next())
                id = rs.getInt("id");
            System.out.println("id " + id);

            conn.close();
            if (id == 404)
                return false;
            else if (id == 0) {
                isAdmin = true;
                return true;
            }

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                conn.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return true;
    }
}

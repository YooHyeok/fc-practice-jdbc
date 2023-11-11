package org.example;

import java.sql.*;

public class UserDao {

    private static Connection getConnection() {

        String url = "jdbc:h2:mem://localhost/~/jdbc-practice;MODE=MySQL;DB_CLOSE_DELAY=-1";
        String id = "sa";
        String password = "";
        try {
            Class.forName("org.h2.Driver");
            return DriverManager.getConnection(url, id, password);
        } catch (Exception e) {
            return null;
        }
    }

    public void create(User user) throws SQLException {
        Connection conn = null;
        PreparedStatement pstmt = null;

        try {
            conn = getConnection();
            String sql = "INSERT INTO USERS VALUE (?, ?, ?, ?)";
            pstmt = conn.prepareStatement(sql);

            // 파라미터 바인딩
            pstmt.setString(1, user.getUserId());
            pstmt.setString(2, user.getPassword());
            pstmt.setString(4, user.getEmail());
            pstmt.setString(3, user.getName());

            pstmt.executeUpdate(); //쿼리 수행
        } finally {
            //자원 해제
            if (pstmt != null) {
                pstmt.close();
            }

            if (conn != null) {
                conn.close();
            }
        }
    }

    public static User findByUserId(String userId) {
        return null;
    }
}

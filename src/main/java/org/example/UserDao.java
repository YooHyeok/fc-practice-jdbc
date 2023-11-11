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
//            conn = getConnection();
            conn = ConnectionManager.getConnection();
            String sql = "INSERT INTO USERS VALUES (?, ?, ?, ?)";
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

    public static User findByUserId(String userId) throws SQLException {
    Connection conn = null;
    PreparedStatement pstmt = null;
    ResultSet rs = null;

    try {
//            conn = getConnection();
        conn = ConnectionManager.getConnection();
        String sql = "SELECT userId, password, name, email FROM USERS WHERE userId = ?";
        pstmt = conn.prepareStatement(sql);
        pstmt.setString(1, userId);
        rs = pstmt.executeQuery();
        User user = null;
        if (rs.next()) {
            user = new User(
                    rs.getString("userId"),
                    rs.getString("password"),
                    rs.getString("name"),
                    rs.getString("email")
            );
        }
            return user;
        } finally {
            if (rs != null) {
                rs.close();
            }
            if (pstmt != null) {
                pstmt.close();
            }
            if (conn != null) {
                conn.close();
            }
        }
    }

    public void createRefactorAnonymous(User user) throws SQLException {
        JdbcTemplate jdbcTemplate = new JdbcTemplate();
        String sql = "INSERT INTO USERS VALUES (?, ?, ?, ?)";

        jdbcTemplate.executeUpdate(sql, new PreparedStatementSetter() {
            @Override
            public void setter(PreparedStatement pstmt) throws SQLException {
                pstmt.setString(1, user.getUserId());
                pstmt.setString(2, user.getPassword());
                pstmt.setString(4, user.getEmail());
                pstmt.setString(3, user.getName());
                pstmt.executeUpdate(); //쿼리 수행
            }
        });
    }

    public static User findByUserIdRefactorAnonymous(String userId) throws SQLException {
        JdbcTemplate jdbcTemplate = new JdbcTemplate();
        String sql = "SELECT userId, password, name, email FROM USERS WHERE userId = ?";

        return (User) jdbcTemplate.executeQuery(sql,
                new PreparedStatementSetter() {

                    @Override
                    public void setter(PreparedStatement pstmt) throws SQLException {
                        pstmt.setString(1, userId);

                    }
                },
                new RowMapper() {
                    @Override
                    public Object map(ResultSet rs) throws SQLException {

                        return new User(
                                rs.getString("userId"),
                                rs.getString("password"),
                                rs.getString("name"),
                                rs.getString("email")
                        );
                    }
                });
    }

    public void createRefactorLambda(User user) throws SQLException {
        JdbcTemplate jdbcTemplate = new JdbcTemplate();
        String sql = "INSERT INTO USERS VALUES (?, ?, ?, ?)";

        jdbcTemplate.executeUpdate(sql, pstmt -> {
            pstmt.setString(1, user.getUserId());
            pstmt.setString(2, user.getPassword());
            pstmt.setString(4, user.getEmail());
            pstmt.setString(3, user.getName());
            pstmt.executeUpdate(); //쿼리 수행
        });
    }

    public static User findByUserIdRefactorLambda(String userId) throws SQLException {
        JdbcTemplate jdbcTemplate = new JdbcTemplate();
        String sql = "SELECT userId, password, name, email FROM USERS WHERE userId = ?";

        return (User) jdbcTemplate.executeQuery(sql,
                pstmt -> pstmt.setString(1, userId),
                rs -> new User(
                        rs.getString("userId"),
                        rs.getString("password"),
                        rs.getString("name"),
                        rs.getString("email")
                ));
    }
}

package org.example;


import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class JdbcTemplate {
    public void executeUpdate(String sql, PreparedStatementSetter pss) throws SQLException {
        Connection conn = null;
        PreparedStatement pstmt = null;

        try {
            conn = ConnectionManager.getConnection();
            pstmt = conn.prepareStatement(sql);
            pss.setter(pstmt); // 바인딩 전가
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

    public Object executeQuery(String sql, PreparedStatementSetter pss, RowMapper rowMapper) throws SQLException {
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Object obj = null;
        try {
            conn = ConnectionManager.getConnection();
            pstmt = conn.prepareStatement(sql);
            pss.setter(pstmt); // 바인딩 전가


            rs = pstmt.executeQuery();
            if (rs.next()) {
                obj = rowMapper.map(rs);
            }
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
        return obj;
    }
}

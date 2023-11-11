package org.example;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.core.io.ClassPathResource;
import org.springframework.jdbc.datasource.DataSourceUtils;
import org.springframework.jdbc.datasource.init.DatabasePopulatorUtils;
import org.springframework.jdbc.datasource.init.ResourceDatabasePopulator;

import java.sql.SQLException;

import static org.assertj.core.api.Assertions.assertThat;

public class UserDaoTest {
    @BeforeEach // 테스트 코드 실행하기 앞서 수행
    void setUp() {
        ResourceDatabasePopulator populator = new ResourceDatabasePopulator();
        populator.addScript(new ClassPathResource("db_schema.sql")); //db-schema.sql 스크립트 파일을 class pass에서 읽어온 뒤 스크립트에 추가
        DatabasePopulatorUtils.execute(populator, ConnectionManager.getDataSource()); // populator script 실행
    }

    @Test
    void createTest() throws SQLException {
        UserDao userDao = new UserDao();
        userDao.create(new User("wizard", "password", "name", "email"));
        User user = UserDao.findByUserId("wizard");
        assertThat(user).isEqualTo(new User("wizard", "password", "name", "email"));
    }
}

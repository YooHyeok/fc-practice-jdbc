package org.example;

import org.junit.jupiter.api.BeforeEach;
import org.springframework.core.io.ClassPathResource;
import org.springframework.jdbc.datasource.DataSourceUtils;
import org.springframework.jdbc.datasource.init.DatabasePopulatorUtils;
import org.springframework.jdbc.datasource.init.ResourceDatabasePopulator;

public class UserDaoTest {
    @BeforeEach // 테스트 코드 실행하기 앞서 수행
    void setUp() {
        ResourceDatabasePopulator populator = new ResourceDatabasePopulator();
        populator.addScript(new ClassPathResource("db_schema.sql")); //db-schema.sql 스크립트 파일을 class pass에서 읽어온 뒤 스크립트에 추가
        DatabasePopulatorUtils.execute(populator, ConnectionManager.getDataSource()); // populator script 실행
    }



}

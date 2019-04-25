package hello;


import org.anystub.jdbc.StubDataSource;
import org.h2.jdbcx.JdbcDataSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.sql.DataSource;

@Configuration
public class TestConfiguration {

    @Bean
    public DataSource dataSource() {

        JdbcDataSource ds = new JdbcDataSource();
        ds.setURL("jdbc:h2:./test");
        return new StubDataSource(ds);
    }

}

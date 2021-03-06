package com.wabi.challengeone.component.sqlite;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

@Component
public class SQLiteComponent {

    private final static Logger logger = LoggerFactory.getLogger(SQLiteComponent.class);

    @Autowired
    JdbcTemplate jdbcTemplate;


    public void runSqliteQuery() {
        long count = jdbcTemplate.queryForList("select * from employees").size();
        logger.info("Number of table records: {}", count);

        logger.info("USING LISTS");
        jdbcTemplate
                .queryForList("select * from employees")
                .forEach(map -> logger.info(" " + map.get("FirstName") + " " + map.get("LastName")));

        logger.info("USING STREAMS");
        jdbcTemplate
                .queryForStream("select * from employees", (rs, row) ->
                    new FullName(rs.getString("FirstName"), rs.getString("LastName"))
                )
                .forEach(fullName -> logger.info(" " + fullName.FirstName + " " + fullName.LastName));

    }






    static protected class FullName {

        private final String FirstName;
        private final String LastName;

        public FullName(String firstName, String lastName) {
            this.FirstName = firstName;
            this.LastName = lastName;
        }
    }

}

package com.loy;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.sql.Connection;
import java.sql.Statement;

import javax.annotation.PostConstruct;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
@Service(value = "initTestSqlService")
public class InitTestSql {
	 static final Log logger = LogFactory.getLog(InitTestSql.class);
	    @Autowired
	    JdbcTemplate jdbcTemplate;
	    @PostConstruct
	    public void init() throws Exception {
	        int count = jdbcTemplate.queryForObject("select count(*) from  e_dictionary", Integer.class);
	        Connection con = jdbcTemplate.getDataSource().getConnection();
	        con.setAutoCommit(false);
	        Statement statement = con.createStatement();
	        if (count == 0) {
	            ClassPathResource classPathResource = new ClassPathResource("e_init_test.sql");
	            BufferedReader br = new BufferedReader(
	                    new InputStreamReader(classPathResource.getInputStream(), "UTF-8"));
	            String s = null;
	            while ((s = br.readLine()) != null) {
	                if (StringUtils.isNotBlank(s) && !s.startsWith("--")) {
	                    logger.info(s);
	                    if (s.endsWith(";")) {
	                        s = s.substring(0, s.length() - 1);
	                    }
	                    statement.execute(s);
	                }
	            }
	            con.commit();
	            con.close();
	            br.close();
	        }
	    }
}

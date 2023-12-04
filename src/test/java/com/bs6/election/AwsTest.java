package com.bs6.election;
import com.bs6.election.model.StateDTO;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.cassandra.core.CassandraTemplate;

import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest
public class AwsTest {
    @Autowired
    CassandraTemplate cassandraTemplate;

    private Logger logger = LoggerFactory.getLogger(AwsTest.class);
    @Test
    public void cassandra(){
        String cql = "SELECT * FROM final.state WHERE state='Ohio'";
        List<StateDTO> results = cassandraTemplate.select(cql, StateDTO.class);
        for (StateDTO result : results) {
            System.out.println(result);
        }
    }


}

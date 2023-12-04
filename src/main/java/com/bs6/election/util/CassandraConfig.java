package com.bs6.election.util;
import com.datastax.oss.driver.api.core.config.DriverConfigLoader;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.cassandra.core.CassandraTemplate;
import com.datastax.oss.driver.api.core.CqlSession;
import com.datastax.oss.driver.api.core.CqlSessionBuilder;
import javax.net.ssl.SSLContext;
import java.io.File;
import java.security.NoSuchAlgorithmException;

@Configuration
public class CassandraConfig {

    @Value("${spring.cassandra.keyspace-name}")
    private String keyspace;

    File driverConfig = new File(System.getProperty("user.dir")+"/application.conf");
    @Bean
    public CqlSession session() throws NoSuchAlgorithmException {

        CqlSessionBuilder builder = CqlSession.builder();
        builder.withConfigLoader(DriverConfigLoader.fromFile(driverConfig))
                .withKeyspace(keyspace)
                .withSslContext(SSLContext.getDefault());
        return builder.build();
    }

    @Bean
    public CassandraTemplate cassandraTemplate(CqlSession session) {
        return new CassandraTemplate(session);
    }
}

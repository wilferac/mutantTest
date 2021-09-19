package com.meli.mutant.application.configuration.dataBase;


import io.r2dbc.h2.H2ConnectionConfiguration;
import io.r2dbc.h2.H2ConnectionFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.r2dbc.config.AbstractR2dbcConfiguration;
import org.springframework.data.r2dbc.repository.config.EnableR2dbcRepositories;

@Configuration
@EnableR2dbcRepositories
public class H2Factory extends AbstractR2dbcConfiguration {
    @Value("${spring.r2dbc.url}")
    private String url;

    @Value("${spring.datasource.username}")
    private String user;

    @Value("${spring.datasource.password}")
    private String password;

    @Bean
    public H2ConnectionFactory connectionFactory() {
        var config = H2ConnectionConfiguration.builder()
                .url(url)
                .username(user)
                .password(password)
                .build();

        return new H2ConnectionFactory(config);
    }

}

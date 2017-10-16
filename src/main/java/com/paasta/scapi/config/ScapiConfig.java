package com.paasta.scapi.config;

import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

/**
 * Created by lena on 2017-06-14.
 */
@Configuration
@EnableJpaRepositories("com.paasta.scapi.repository")
@EntityScan("com.paasta.scapi.entity")
@ComponentScan(basePackages = "com.paasta")
public class ScapiConfig {
}


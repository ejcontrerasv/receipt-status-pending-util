package com.sovos.status.pending.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Configuration
@PropertySource("classpath:application.properties")
@ConfigurationProperties(prefix = "query.parameter")
public class ConfigProperties {
	
	private Integer maxRows;
	private String endDate;

}

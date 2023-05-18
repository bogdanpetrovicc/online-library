package com.bogdan.onlinelibrary;

import com.bogdan.onlinelibrary.bean.Exclude;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.context.properties.ConfigurationPropertiesScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.FilterType;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication(scanBasePackages = "com.bogdan.onlinelibrary")
@EntityScan(basePackages = "com.bogdan.onlinelibrary")
@ConfigurationPropertiesScan("com.bogdan.onlinelibrary")
@EnableJpaRepositories(value = "com.bogdan.onlinelibrary",
		excludeFilters = @ComponentScan.Filter(type = FilterType.ANNOTATION, value = Exclude.class))
public class OnlineLibraryApplication {
	public static void main(String[] args) {
		SpringApplication.run(OnlineLibraryApplication.class, args);
	}
}

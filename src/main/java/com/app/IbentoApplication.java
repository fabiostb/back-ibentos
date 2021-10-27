package com.app;

import com.google.common.base.Predicate;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import static com.google.common.base.Predicates.or;
import static springfox.documentation.builders.PathSelectors.regex;

@EnableSwagger2
@SpringBootApplication(scanBasePackages = "com.*")
public class IbentoApplication {

	@Bean
	public Docket api() {
		return new Docket(DocumentationType.SWAGGER_2).groupName("public-api")
				.apiInfo(apiInfo()).select().paths(appPaths()).build();
	}

	private Predicate<String> appPaths() {
		return or(regex("/api.*"));
	}

	private ApiInfo apiInfo() {
		Contact contact = new Contact("Fabio Santiago",
				"https://www.linkedin.com/in/fabiosantiago/",
				"santiago.fabio@gmail.com");
		return new ApiInfoBuilder().title("Ibentos API")
				.description("Juste for FUN :)")
				.contact(contact).version("1.0").build();
	}
	
	public static void main(String[] args) {
		SpringApplication.run(IbentoApplication.class, args);
	}
}

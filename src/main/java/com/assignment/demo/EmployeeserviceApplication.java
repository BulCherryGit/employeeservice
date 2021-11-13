package com.assignment.demo;

import java.util.Collections;
import java.util.function.Predicate;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.web.bind.annotation.RestController;

import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@SpringBootApplication
@EnableSwagger2
public class EmployeeserviceApplication {

	@Bean
	  public Docket swagger() {
	        return new Docket(DocumentationType.SWAGGER_2).apiInfo(apiInfo())
	                .select()
	                .apis(RequestHandlerSelectors.basePackage("com.assignment.demo"))
	                .paths(PathSelectors.regex("/employ.*"))
	                .build();
	        
	        
	       
	  }
	

	public static void main(String[] args) {
		SpringApplication.run(EmployeeserviceApplication.class, args);
	}
	
	public ApiInfo apiInfo()
	{
		Contact c1 = new Contact("Kishore", "Click here ot visit our Site ", "test@test.com");
		return new ApiInfo("Employee Service", "Rest Interfaces to Create Associate and Disassociate Employee with Departments and Projects", "1.0", "myurl.com", c1, "kishorelicense", "www.kishorelicense.com",  Collections.emptyList());
	}

}

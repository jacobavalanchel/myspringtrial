package com.jacob.myspringtrial;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;

@SpringBootApplication(exclude = {DataSourceAutoConfiguration.class })
public class MyspringtrialApplication {

	public static void main(String[] args) {
		SpringApplication.run(MyspringtrialApplication.class, args);
	}

}

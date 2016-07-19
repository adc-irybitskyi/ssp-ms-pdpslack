package pdp.api;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.ComponentScan;

@EnableAutoConfiguration
@ComponentScan(basePackages = {"pdp.api"})
public class ApiApp {
	public static void main(String[] args) throws Exception {
		SpringApplication.run(ApiApp.class, args);
	}
}

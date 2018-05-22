package co.ceibauniversity.parkinglot;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan(basePackages = {
		"co.ceibauniversity.parkinglot"
})
public class ParqueaderoApplication {

	public static void main(String[] args) {
		
		SpringApplication.run(ParqueaderoApplication.class, args);
	}
}

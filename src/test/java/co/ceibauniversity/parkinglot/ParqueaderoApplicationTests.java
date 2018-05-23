package co.ceibauniversity.parkinglot;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
@ComponentScan(basePackages = {
		"co.ceibauniversity.parkinglot"
})
public class ParqueaderoApplicationTests {

	@Test
	public void contextLoads() {
	}

}

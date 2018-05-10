package co.ceibaUniversity.Parqueadero.domain;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import co.ceibaUniversity.Parqueadero.dao.IParkingLotDAO;

@RunWith(SpringRunner.class)
@SpringBootTest
public class ParkingLotDomainTest {

	private static final String CAR_PLATE = "FCL799";
	private static final String CAR = "CAR";
	private static final String BIKE_PLATE = "ELR01D";
	private static final String BIKE = "BIKE";
	private static final String OTHER_PLATE = "ABC123";
	private static final String OTHER = "OTHER";	
	
	@InjectMocks
	private ParkingLotDomain parkingLotDomain;
	
	@Mock
	private IParkingLotDAO parkingLotDAO;
	
    @Before
    public void initMocks(){
        MockitoAnnotations.initMocks(this);
    }
	
	@Test
	public void getTypeCarTest() {
		//arrange
		Mockito.when(parkingLotDAO.getVehicleType(CAR_PLATE)).thenReturn(CAR);
		String type;
		//act
		type = parkingLotDomain.getType(CAR_PLATE);
		//assert
		assertEquals(CAR,type);
	}
	
	@Test
	public void getTypeBikeTest() {
		//arrange
		String type;
		//act
		type = parkingLotDomain.getType(BIKE_PLATE);
		//assert
		assertEquals(BIKE,type);
	}
	
	@Test
	public void getTypeFailTest() {
		String type;
		type = parkingLotDomain.getType(OTHER_PLATE);
		
		assertEquals(OTHER,type);
	}

}
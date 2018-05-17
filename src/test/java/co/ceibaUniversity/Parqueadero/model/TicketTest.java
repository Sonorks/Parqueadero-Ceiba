package co.ceibaUniversity.Parqueadero.model;

import static org.junit.Assert.*;

import java.util.Date;

import org.junit.Test;

public class TicketTest {
	
	Ticket ticket = new Ticket();

	@Test
	public void setTotalHoursTest() {
		ticket.setTotalHours(10);
		assertEquals(ticket.getTotalHours(),10);
	}
	
	@Test
	public void setTotalPriceTest() {
		ticket.setTotalPrice(10000);
		assertTrue(ticket.getTotalPrice()==10000);
	}
	
	@Test
	public void setExitDateTest() {
		ticket.setExitDate(new Date());
		assertEquals(ticket.getExitDate(), new Date());
	}

}

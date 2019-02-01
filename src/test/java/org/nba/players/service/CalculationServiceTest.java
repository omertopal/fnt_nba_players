package org.nba.players.service;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import org.junit.Test;

public class CalculationServiceTest {

	//@Test
	public void testGetGameDateRosters() {
		
	}
	
	@Test
	public void test1()  {
	    //  create mock
		CalculationService calculationService = mock(CalculationService.class);

        // define return value for method getUniqueId()
        when(calculationService.getNextCalculationId()).thenReturn(1);

        // use mock in test....
        assertEquals(calculationService.getNextCalculationId(), 1);
	}
}

package org.nba.players.service;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

public class CalculationServiceTest {

	
	CalculationService calculationService = new CalculationService();
	@Test
	public void testGetGameDateRosters() {
		assertEquals("CD", calculationService.getNextCalculationId());
	}
}

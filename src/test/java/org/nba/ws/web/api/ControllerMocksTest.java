package org.nba.ws.web.api;

import javax.transaction.Transactional;

import org.junit.Before;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.nba.players.controller.PlayerController;
import org.nba.players.service.PlayerService;
import org.nba.ws.AbstractTest;

@Transactional
public class ControllerMocksTest extends AbstractTest {

	@Mock
	private PlayerService playerService;
	
	
	@InjectMocks
	private PlayerController controller;
	
	@Before
	public void setup(){
		MockitoAnnotations.initMocks(this);
		setup(controller);
	}
}

package org.nba.ws.service;


import java.util.List;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.nba.players.entity.Player;
import org.nba.players.service.PlayerService;
import org.nba.ws.AbstractTest;
import org.nba.ws.web.api.BaseController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.context.WebApplicationContext;

@Transactional
public class PlayersServiceTest extends AbstractTest{
	
	@Autowired
	private PlayerService service;
	
	@Autowired
	protected WebApplicationContext webApplicationContext;
	
	protected MockMvc mvc;
	
	@Before
	public void setup(){
		mvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
		System.out.println(mvc.toString());
	}
	
	@Before
	public void setup(BaseController controller){
		mvc = MockMvcBuilders.standaloneSetup(controller).build();
		System.out.println(mvc.toString());	
	}
	
	@After
	public void tearDown(){
		//clean up
	}
	
	@Test
	public void testFindAll(){
		List<Player> allPlayers = service.getAllPlayers();
		Assert.assertNotNull("Failure: expected not null", allPlayers);
		Assert.assertEquals("Failure: expected size", 100, allPlayers.size(), 0);
	}
}

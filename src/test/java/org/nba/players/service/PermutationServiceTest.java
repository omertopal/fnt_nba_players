package org.nba.players.service;

import static org.junit.Assert.assertEquals;

import java.util.List;

import org.nba.players.dao.IPERM_6_1DAO;
import org.nba.players.model.PermModel;
import org.springframework.beans.factory.annotation.Autowired;

//@RunWith(SpringRunner.class)
//@SpringBootTest(classes = MyApplication.class)
public class PermutationServiceTest {
	
	@Autowired
	private IPERM_6_1DAO perm6_1DAO;

	PermutationService permService = null;
	
	//@Before
	public void setup() {
		//permService = new PermutationService();
		//PlayerModel player1 = new PlayerModel();
		//player1.setOrder(1).setId(1).setTeam("OKC").setName("Paul George").setAvgPts(47.4).setIsPG(0).setIsSG(1).setIsSF(1).setIsPF(1).setIsC(0);
		//myPlayersToday1.add(player1);
	}
	
	//@Test
	public void testGetPermutations1() {
		List<PermModel> permutations = perm6_1DAO.getAllPerm();
		assertEquals(6,permutations.size());
		
	}
}

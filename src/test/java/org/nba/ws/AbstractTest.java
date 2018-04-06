package org.nba.ws;


import org.junit.Before;
import org.junit.runner.RunWith;
import org.nba.players.MyApplication;
import org.nba.players.controller.NbaController;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(value = SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = MyApplication.class)
public abstract class AbstractTest {
	protected Logger logger = LoggerFactory.getLogger(this.getClass());
	
	@Before
	public void setup(NbaController controller){
	}
}

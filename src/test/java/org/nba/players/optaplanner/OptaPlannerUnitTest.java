package org.nba.players.optaplanner;

import org.nba.players.opta.NbaOptaSchedule;
import org.nba.players.opta.OptaPlayer;
import org.nba.players.opta.PlayerSlotSelection;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.optaplanner.core.api.solver.Solver;
import org.optaplanner.core.api.solver.SolverFactory;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class OptaPlannerUnitTest {

    static NbaOptaSchedule unsolvedNbaOptaSchedule;

    @Before
    public void setUp() {

        unsolvedNbaOptaSchedule = new NbaOptaSchedule();

        for(int i = 0; i < 10; i++){
            unsolvedNbaOptaSchedule.getSelectionList().add(new PlayerSlotSelection());
        }

        //unsolvedNbaOptaSchedule.getGameDateList().addAll(Arrays.asList(new Integer[] { 1, 2, 3 }));
        unsolvedNbaOptaSchedule.getPositionSlotList().addAll(Arrays.asList(new Integer[] { 1, 2,3,4,5 }));
        
        List<OptaPlayer> myPlayers = new ArrayList<>();
        OptaPlayer lillard = new OptaPlayer();
        lillard.setName("Lillard");	
        lillard.setPlayerId(1);
        Set<Integer> playerSkills = new HashSet<Integer>();
        playerSkills.add(1);
        lillard.setSkills(playerSkills);
        
        myPlayers.add(lillard);
        
        OptaPlayer kyrie = new OptaPlayer();
        kyrie.setName("Kyrie");	
        kyrie.setPlayerId(2);
        Set<Integer> kyrieSkills = new HashSet<Integer>();
        kyrieSkills.add(1);
        kyrieSkills.add(2);
        kyrie.setSkills(kyrieSkills);
        
        myPlayers.add(kyrie);
        
        unsolvedNbaOptaSchedule.setPlayerList(myPlayers);
    }

    //@Test
    public void test_whenCustomJavaSolver() {

        SolverFactory<NbaOptaSchedule> solverFactory = SolverFactory.createFromXmlResource("nbaScheduleSolverConfiguration.xml");
        Solver<NbaOptaSchedule> solver = solverFactory.buildSolver();
        NbaOptaSchedule solvedNbaSchedule = solver.solve(unsolvedNbaOptaSchedule);

        //Assert.assertNotNull(solvedCourseSchedule.getScore());
        //Assert.assertEquals(-4, solvedCourseSchedule.getScore().getHardScore());
        
        solvedNbaSchedule.printNbaSchedule();
    }

    //@Test
    public void test_whenDroolsSolver() {

        SolverFactory<NbaOptaSchedule> solverFactory = SolverFactory.createFromXmlResource("nbaScheduleSolverConfigDrools.xml");
        Solver<NbaOptaSchedule> solver = solverFactory.buildSolver();
        NbaOptaSchedule solvedNbaSchedule = solver.solve(unsolvedNbaOptaSchedule);

        Assert.assertNotNull(solvedNbaSchedule.getScore());
        
        solvedNbaSchedule.printNbaSchedule();
    }
}

package org.nba.players.optaplanner;

import org.nba.players.opta.NbaOptaSchedule;
import org.nba.players.opta.Player;
import org.nba.players.opta.PlayerSlotSelection;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.optaplanner.core.api.solver.Solver;
import org.optaplanner.core.api.solver.SolverFactory;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

public class OptaPlannerUnitTest {

    static NbaOptaSchedule unsolvedNbaOptaSchedule;

    @Before
    public void setUp() {

        unsolvedNbaOptaSchedule = new NbaOptaSchedule();

        for(int i = 0; i < 6; i++){
            unsolvedNbaOptaSchedule.getSelectionList().add(new PlayerSlotSelection());
        }

        unsolvedNbaOptaSchedule.getGameDateList().addAll(Arrays.asList(new Integer[] { 1, 2, 3 }));
        unsolvedNbaOptaSchedule.getPositionSlotList().addAll(Arrays.asList(new Integer[] { 1, 2 }));
        
        Player player = new Player();
        player.setName("Omer");	
        player.setPlayerId(1);
        Set<Integer> playerSkills = new HashSet<Integer>();
        playerSkills.add(3);
        player.setSkills(playerSkills);
        
        unsolvedNbaOptaSchedule.getPlayerList().add(player);
    }

    //@Test
    public void test_whenCustomJavaSolver() {

        SolverFactory<NbaOptaSchedule> solverFactory = SolverFactory.createFromXmlResource("nbaScheduleSolverConfiguration.xml");
        Solver<NbaOptaSchedule> solver = solverFactory.buildSolver();
        NbaOptaSchedule solvedCourseSchedule = solver.solve(unsolvedNbaOptaSchedule);

        Assert.assertNotNull(solvedCourseSchedule.getScore());
        Assert.assertEquals(-4, solvedCourseSchedule.getScore().getHardScore());
        
        solvedCourseSchedule.printNbaSchedule();
    }

    @Test
    public void test_whenDroolsSolver() {

        SolverFactory<NbaOptaSchedule> solverFactory = SolverFactory.createFromXmlResource("nbaScheduleSolverConfigDrools.xml");
        Solver<NbaOptaSchedule> solver = solverFactory.buildSolver();
        NbaOptaSchedule solvedCourseSchedule = solver.solve(unsolvedNbaOptaSchedule);
        NbaOptaSchedule solvedCourseScheduleBest = solver.getBestSolution();

        Assert.assertNotNull(solvedCourseSchedule.getScore());
        Assert.assertEquals(0, solvedCourseSchedule.getScore().getHardScore());
        
        solvedCourseSchedule.printNbaSchedule();
        System.out.println("This is best solution");
        solvedCourseScheduleBest.printNbaSchedule();
    }
}

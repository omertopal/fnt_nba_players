package org.nba.players.opta;

import java.util.ArrayList;
import java.util.List;

import org.optaplanner.core.api.domain.solution.PlanningEntityCollectionProperty;
import org.optaplanner.core.api.domain.solution.PlanningScore;
import org.optaplanner.core.api.domain.solution.PlanningSolution;
import org.optaplanner.core.api.domain.solution.drools.ProblemFactCollectionProperty;
import org.optaplanner.core.api.domain.valuerange.ValueRangeProvider;
import org.optaplanner.core.api.score.buildin.hardsoft.HardSoftScore;

@PlanningSolution
public class NbaOptaSchedule {

    private List<Integer> positionSlotList;
    private List<Integer> gameDateList;
    private List<Player> playerList;
    private List<PlayerSlotSelection> selectionList;
    private HardSoftScore score;

    public NbaOptaSchedule(){
        positionSlotList = new ArrayList<>();
        gameDateList = new ArrayList<>();
        playerList = new ArrayList<>();
        selectionList = new ArrayList<>();
    }

    @ValueRangeProvider(id = "availableSlots")
    @ProblemFactCollectionProperty
    public List<Integer> getPositionSlotList() {
        return positionSlotList;
    }

    @ValueRangeProvider(id = "availableGameDates")
    @ProblemFactCollectionProperty
    public List<Integer> getGameDateList() {
        return gameDateList;
    }
    
    @ValueRangeProvider(id = "availablePlayers")
    @ProblemFactCollectionProperty
    public List<Player> getPlayerList() {
        return playerList;
    }

    @PlanningEntityCollectionProperty
    public List<PlayerSlotSelection> getSelectionList() {
        return selectionList;
    }

    @PlanningScore
    public HardSoftScore getScore() {
        return score;
    }

    public void setScore(HardSoftScore score) {
        this.score = score;
    }

    public void printNbaSchedule() {
    	selectionList.stream()
                .map(c -> "Player in position " + c.getPositionSlot().toString() + " - Game date: " + c.getGameDate().toString()
                		+ ", Player :"+ c.getPlayer().getName())
                .forEach(k -> System.out.println(k));
    }

}

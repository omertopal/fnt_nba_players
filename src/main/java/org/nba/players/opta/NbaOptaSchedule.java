package org.nba.players.opta;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.optaplanner.core.api.domain.solution.PlanningEntityCollectionProperty;
import org.optaplanner.core.api.domain.solution.PlanningScore;
import org.optaplanner.core.api.domain.solution.PlanningSolution;
import org.optaplanner.core.api.domain.solution.drools.ProblemFactCollectionProperty;
import org.optaplanner.core.api.domain.valuerange.ValueRangeProvider;
import org.optaplanner.core.api.score.buildin.hardsoft.HardSoftScore;
import org.springframework.data.util.Pair;

@PlanningSolution
public class NbaOptaSchedule {

    private List<Integer> positionSlotList;
    //private List<Integer> gameDateList;
    private List<OptaPlayer> playerList;
    private List<PlayerSlotSelection> selectionList;
    private HardSoftScore score;

    public NbaOptaSchedule(){
        positionSlotList = new ArrayList<>();
        //gameDateList = new ArrayList<>();
        playerList = new ArrayList<>();
        selectionList = new ArrayList<>();
    }

    @ValueRangeProvider(id = "availableSlots")
    @ProblemFactCollectionProperty
    public List<Integer> getPositionSlotList() {
        return positionSlotList;
    }

    /*
    @ValueRangeProvider(id = "availableGameDates")
    @ProblemFactCollectionProperty
    public List<Integer> getGameDateList() {
        return gameDateList;
    }
    */
    
    @ValueRangeProvider(id = "availablePlayers")
    @ProblemFactCollectionProperty
    public List<OptaPlayer> getPlayerList() {
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
    
    public void setPlayerList(List<OptaPlayer> playerList) {
        this.playerList = playerList;
    }

    public void setScore(HardSoftScore score) {
        this.score = score;
    }

    public void printNbaSchedule() {
    	selectionList.stream()
                .map(c -> "Position :" + c.getPositionSlot().toString() +
                		 ", Player :"+ c.getPlayer().getName())
                .forEach(k -> System.out.println(k));
    	
    	//Map<Object, Long> counting = selectionList.stream().collect(
          //      Collectors.groupingBy(p -> Pair.of(p.getPositionSlot(), p.getPlayer().getPlayerId()) , Collectors.counting()));
    	
    	//System.out.println("counting:" + counting);
    }

}

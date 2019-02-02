package org.nba.players.opta;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;

import org.optaplanner.core.api.score.Score;
import org.optaplanner.core.api.score.buildin.hardsoft.HardSoftScore;
import org.optaplanner.core.impl.score.director.easy.EasyScoreCalculator;

public class ScoreCalculator implements EasyScoreCalculator<NbaOptaSchedule> {

    @Override
    public Score calculateScore(NbaOptaSchedule nbaSchedule) {
        int hardScore = 0;
        int softScore = 0;

        
        HashSet<String> occupiedSelections = new HashSet<>();
        HashSet<String> occupiedPlayers = new HashSet<>();
        HashSet<String> occupiedPositions = new HashSet<>();
        
        Map<Integer,Double> positionMaxPointMap = new HashMap<>();
        for (PlayerSlotSelection selection : nbaSchedule.getSelectionList()) {
            if( selection.getPlayer() != null && selection.getPositionSlot() != null) {
            	
            	
            	//don't repeat exact same
                String selectionInUse = selection.getPositionSlot().toString() +":"
                						+ selection.getPlayer().getPlayerId();
                if (occupiedSelections.contains(selectionInUse)) {
                    hardScore += -1;
                } else {
                	occupiedSelections.add(selectionInUse);
                }
                
                //check if player has skill
            	if(!selection.getPlayer().hasSkillForSpot(selection.getPositionSlot())) {
            		hardScore += -1;
            	}else {
            		/*
            		//if player is best at a skill and has max avg
            		if(positionMaxPointMap.get(selection.getPositionSlot()) != null) {
            			if(selection.getPlayer().getAvgPts() < positionMaxPointMap.get(selection.getPositionSlot())) {
            				softScore += -1;
            			}else {
            				positionMaxPointMap.put(selection.getPositionSlot(), selection.getPlayer().getAvgPts());
            			}
            		}else {
            			positionMaxPointMap.put(selection.getPositionSlot(), selection.getPlayer().getAvgPts());
            		}
            		*/
            	}
            	
                
               //check if same player is assigned twice in same day
                String playerInUse = selection.getPlayer().getPlayerId().toString();
                
				if (occupiedPlayers.contains(playerInUse)) {
				    hardScore += -1;
				} else {
					occupiedPlayers.add(playerInUse);
				}
                
				
				//check if same spot assigned twice in same day
				 String positionInUse = selection.getPositionSlot().toString();
	                
				if (occupiedPositions.contains(positionInUse)) {
				    hardScore += -1;
				} else {
					occupiedPositions.add(positionInUse);
				}
				
				// check if best players are activated in spots
				
				
            	
            	//check if player has game that day
            	/*
            	if(!selection.getPlayer().playerHasGameThatDay(selection.getGameDate())) {
            		hardScore += -1;
            	}
            	*/
                
            } else {
                hardScore += -1;
            }
        }
        
        return HardSoftScore.of(hardScore, softScore);
    }
}

package org.nba.players.opta;

import org.optaplanner.core.api.score.Score;
import org.optaplanner.core.api.score.buildin.hardsoft.HardSoftScore;
import org.optaplanner.core.impl.score.director.easy.EasyScoreCalculator;

import java.util.HashSet;
import java.util.Iterator;

public class ScoreCalculator implements EasyScoreCalculator<NbaOptaSchedule> {

    @Override
    public Score calculateScore(NbaOptaSchedule courseSchedule) {
        int hardScore = 0;
        int softScore = 0;

        HashSet<String> occupiedRooms = new HashSet<>();
        for (PlayerSlotSelection selection : courseSchedule.getSelectionList()) {
            if(selection.getGameDate() != null && selection.getPositionSlot() != null) {
                String roomInUse = selection.getGameDate().toString() + ":" + selection.getPositionSlot().toString();
                if (occupiedRooms.contains(roomInUse)) {
                    hardScore += -1;
                } else {
                    occupiedRooms.add(roomInUse);
                }
            } else {
                hardScore += -1;
            }
        }
        
        return HardSoftScore.valueOf(hardScore, softScore);
    }
}

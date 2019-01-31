package org.nba.players.opta;

import org.optaplanner.core.api.domain.entity.PlanningEntity;
import org.optaplanner.core.api.domain.variable.PlanningVariable;

@PlanningEntity
public class PlayerSlotSelection {

    private Integer positionSlot;
    private Integer gameDate;
    private Player player;

    @PlanningVariable(valueRangeProviderRefs = {"availableGameDates"})
    public Integer getGameDate() {
        return gameDate;
    }

    @PlanningVariable(valueRangeProviderRefs = {"availableSlots"})
    public Integer getPositionSlot() {
        return positionSlot;
    }
    
    @PlanningVariable(valueRangeProviderRefs = {"availablePlayers"})
    public Player getPlayer() {
        return player;
    }

    public void setGameDate(Integer gameDate) {
        this.gameDate = gameDate;
    }

    public void setPositionSlot(Integer positionSlot) {
        this.positionSlot = positionSlot;
    }

	public void setPlayer(Player player) {
		this.player = player;
	}

}

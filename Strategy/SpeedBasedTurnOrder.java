package Strategy;

import java.util.List;
import java.util.ArrayList;
import Combatants.Combatant;

public class SpeedBasedTurnOrder implements TurnOrderStrategy {
    // Attributes
    List<Combatant> combatants;
    
    // Constuctor
    public SpeedBasedTurnOrder(List<Combatant> combatants){
        this.combatants = combatants;
    }

    // Methods
    public List<Combatant> sortCombatantsSpeed(){
        List<Combatant> ordered = new ArrayList<>(combatants);
        ordered.sort((a, b) -> a.getSpeed() - b.getSpeed());
        return ordered;
    }
    @Override
    public List<Combatant> getOrder(List<Combatant> combatants) {
        this.combatants = combatants;
        return sortCombatantsSpeed();
    }
}


package Strategy;

import java.util.List;
import Combatants.Combatant;

public interface TurnOrderStrategy{
    List<Combatant> getOrder (List<Combatant> combatants);
}

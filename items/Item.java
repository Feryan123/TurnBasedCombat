package Items;

import java.util.List;
import Combatants.Combatant;
import Combatants.Player;

public interface Item {
    void use(Player user, List<Combatant> targets);
    String getName();
}
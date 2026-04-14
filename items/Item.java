package Items;

import Combatants.Player;

public interface Item {
    void use(Player user);
    String getName();
}

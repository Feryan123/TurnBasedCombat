package Items;

import Combatants.*;

public interface Item {
    void use(Combatant user);
    String getName();
}

package Actions;

import Combatants.*;

public interface Action {
    void execute(Combatant user, Combatant target);
}
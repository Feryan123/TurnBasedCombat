package Actions;

import Combatants.*;
import java.util.List;

public interface Action {
    void execute(Combatant user, List<Combatant> targets);
}
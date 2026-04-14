package Actions;

import java.util.List;
import Combatants.*;

public abstract class SpecialSkillAction implements Action {
    // Methods
    @Override
    public abstract void execute(Combatant user, List<Combatant> targets);
}

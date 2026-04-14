package Actions;

import Combatants.*;
import java.util.List;

public class BasicAttack implements Action {
    @Override
    public void execute(Combatant user, List<Combatant> targets) {
        if (!user.canAct()) return;

        for (Combatant target : targets) {
            target.takeDamage(user.getAttack());
        }
    }
}
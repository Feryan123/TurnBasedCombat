package Actions;

import java.util.List;
import Combatants.*;
import StatusEffects.ArcaneBuffEffect;

public class ArcaneBlastAction extends SpecialSkillAction {
    public void execute(Combatant user, List<Combatant> targets) {
        if(!user.canAct()) return; // Check if user can act
        int kills = 0;
        for (Combatant target : targets) {
            target.takeDamage(user.getAttack());
            if (!target.isAlive()) {
                kills++;
            }
        }
        user.addStatusEffect(new ArcaneBuffEffect(kills));
    }
}

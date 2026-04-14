package Actions;

import java.util.List;
import Combatants.*;
import StatusEffects.StunEffect;

public class ShieldBashAction extends SpecialSkillAction {
    public void execute(Combatant user, List<Combatant> targets) {
        if(!user.canAct()) return; // Check if user can act
        for (Combatant target : targets) {
            int damage = Math.max(0, user.getAttack() - target.getDefense());
            target.takeDamage(damage);
            target.addStatusEffect(new StunEffect(2));   
        }
    }
}

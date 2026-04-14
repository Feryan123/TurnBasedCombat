package Actions;

import java.util.List;
import Combatants.*;
import StatusEffects.StunEffect;

public class ShieldBashAction extends SpecialSkillAction {
    @Override
    public void execute(Combatant user, List<Combatant> targets) {
        if(!user.canAct()) return; // Check if user can act
        for (Combatant target : targets) {
            target.takeDamage(user.getAttack());
            target.addStatusEffect(new StunEffect(2));   
        }
    }
}

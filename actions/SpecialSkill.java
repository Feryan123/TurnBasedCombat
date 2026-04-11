package Actions;

import java.util.List;
import Combatants.*;

public class SpecialSkill implements Action {
    // Attributes
    private Combatant singleTarget;
    private List<Enemy> multiTargets;
    
    // Constructor
    public SpecialSkill(Combatant singleTarget){
        this.singleTarget = singleTarget;
        this.multiTargets = null;
    }
    public SpecialSkill(List<Enemy> multiTargets){
        this.singleTarget = null;
        this.multiTargets = multiTargets;
    }

    // Methods
    @Override
    public void execute(Combatant user, Combatant target) {
        if (!user.canAct()) return; // Check if user can act
        if (singleTarget == null && (multiTargets == null || multiTargets.isEmpty())) return; 

        if (user instanceof Warrior) {
            if (((Warrior) user).getSkillCooldown() != 0) return; // Check cooldown
            ((Warrior) user).shieldBash(singleTarget); // Execute Warrior's skill
        } else if (user instanceof Wizard) {
            if (((Wizard) user).getSkillCooldown() != 0) return; // Check cooldown
            ((Wizard) user).arcaneBlast(multiTargets); // Execute Wizard's skill
        }
    }
}

package Actions;

import StatusEffects.DefendEffect;
import Combatants.*;
import java.util.List;

public class DefendAction implements Action {
    // Methods
    @Override
    public void execute(Combatant user, List<Combatant> targets){
        if(!user.canAct()) return; // Check if user can act 
        user.addStatusEffect(new DefendEffect());
    }
}

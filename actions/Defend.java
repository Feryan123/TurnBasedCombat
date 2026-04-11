package Actions;

import StatusEffects.DefendBuffEffect;
import Combatants.*;

public class Defend implements Action {
    // Methods
    @Override
    public void execute(Combatant user, Combatant target){
        if(!user.canAct()) return; // Check if user can act 
        user.addEffect(new DefendBuffEffect());
    }
}

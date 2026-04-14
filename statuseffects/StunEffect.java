package StatusEffects;

import Combatants.*;

public class StunEffect extends StatusEffect{
    // Constructor
    public StunEffect(int duration){
        super("Stun", duration);
    }

    // Methods
    @Override
    public void onApply(Combatant target) {
    }

    @Override
    public void onExpire(Combatant target) {
    }
    
    @Override
    public boolean preventsAction(){
        return true;
    }
}

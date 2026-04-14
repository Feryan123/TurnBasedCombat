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
        target.addStatusEffect(this);
    }
    @Override
    public void onExpire(Combatant target) {
        target.removeExpiredEffects();
    }
    @Override
    public boolean preventsAction(){
        return true;
    }
}

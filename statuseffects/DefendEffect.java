package StatusEffects;

import Combatants.*;

public class DefendEffect extends StatusEffect {
    // Constructor
    public DefendEffect(){
        super("Defend Buff", 2);
    }

    // Methods
    @Override
    public void onApply(Combatant target){
        ((Player) target).increaseDefense(10);
    }
    @Override
    public void onExpire(Combatant target){
        ((Player) target).decreaseDefense(10);
    }
}

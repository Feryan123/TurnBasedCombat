package StatusEffects;

import Combatants.*;

public class SmokeBombEffect extends StatusEffect {
    // Constructor
    public SmokeBombEffect(){
        super("Smoke Bomb Effect", 2);
    }

    // Methods
    @Override
    public void onApply(Combatant target){
        target.setIsDamageImmune(true);
        System.out.println(target.getName() + " is now invisible and immune to damage!");
    }
    @Override
    public void onExpire(Combatant target){
        target.setIsDamageImmune(false);
    }
}

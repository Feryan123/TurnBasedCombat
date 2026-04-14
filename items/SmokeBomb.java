package Items;

import Combatants.*;
import StatusEffects.*;

public class SmokeBomb implements Item {
    // Methods
    @Override
    public void use(Player user){
        user.addStatusEffect(new SmokeBombEffect());
        
    }
    @Override
    public String getName() {
        return "Smoke Bomb";
    }
}

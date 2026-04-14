package Items;

import Combatants.*;

public class Potion implements Item {
    private int healAmount = 100;
    
    // Methods
    @Override
    public void use(Player user){
        user.heal(healAmount);
    }
    @Override
    public String getName() {
        return "Potion";
    }
}

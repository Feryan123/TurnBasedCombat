package Items;

import Combatants.*;

public class Potion implements Item {
    private int healAmount = 100;
    
    // Methods
    @Override
    public void use(Player user){
        user.heal(healAmount);
        System.out.println(user.getName() + " uses a Potion and recovers " + healAmount + " HP!");
    }
    @Override
    public String getName() {
        return "Potion";
    }
}

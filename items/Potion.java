package Items;

import java.util.List;
import Combatants.Combatant;
import Combatants.Player;

public class Potion implements Item {
    private int healAmount = 100;

    @Override
    public void use(Player user, List<Combatant> targets) {
        user.heal(healAmount);
        System.out.println(user.getName() + " recovers " + healAmount + " HP!");
    }

    @Override
    public String getName() {
        return "Potion";
    }
}

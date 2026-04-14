package Items;

import java.util.List;
import Combatants.Combatant;
import Combatants.Player;
import StatusEffects.SmokeBombEffect;

public class SmokeBomb implements Item {
    @Override
    public void use(Player user, List<Combatant> targets) {
        user.addStatusEffect(new SmokeBombEffect());
    }

    @Override
    public String getName() {
        return "Smoke Bomb";
    }
}
package Items;

import java.util.List;
import Combatants.Combatant;
import Combatants.Player;
import Actions.Action;

public class PowerStone implements Item {
    @Override
    public void use(Player user, List<Combatant> targets) {
        System.out.println(user.getName() + " has used Power Stone!");
        Action specialSkill = user.getSpecialSkill();
        specialSkill.execute(user, targets);
    }

    @Override
    public String getName() {
        return "Power Stone";
    }
}
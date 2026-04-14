package Items;

import Combatants.Player;

public class PowerStone implements Item {
    @Override
    public void use(Player user) {
        user.resetSkillCooldown(0);
    }

    @Override
    public String getName() {
        return "Power Stone";
    }
}

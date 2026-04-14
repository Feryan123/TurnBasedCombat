package Items;

import Combatants.*;

public class PowerStone implements Item {
    // Methods
    @Override
    public void use(Player user){
        int cooldown = user.getSkillCooldown();
        user.resetSkillCooldown(0);
        user.useSpecialSkill();
        user.resetSkillCooldown(cooldown);
    }
    @Override
    public String getName() {
        return "Power Stone";
    }
}

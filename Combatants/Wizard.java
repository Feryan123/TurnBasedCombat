package Combatants;

import java.util.List;
import Actions.*;
import StatusEffects.*;

public class Wizard extends Player {
	
	public Wizard(String combatantName) {
		super("Wizard", 200, 50, 10, 20);
	}
	
	public void arcaneBlast(List<Enemy> targets){
        int kills = 0;
		for (Combatant target: targets) {
			new BasicAttack().execute(this, target);
			if (!target.isAlive()) { kills++; }
		}
		this.addEffect(new ArcaneBuffEffect(kills));
        resetSkillCooldown(3);
    }
}

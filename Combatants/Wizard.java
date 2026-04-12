package Combatants;

import java.util.List;

import actions.BasicAttack;

public class Wizard extends Player {

	public Wizard(String combatantName) {
		int HP = 200;
		int Atk = 50;
		int Def = 10;
		int Speed = 20;
		super(combatantName, HP, Atk, Def, Speed);
	}
	
	@Override
	public void useSpecialSkill(List<Enemy> targets) {
		for (Combatant target: targets) {
			new BasicAttack().execute(this, target);
			if (!target.isAlive()) { increaseAttack(10); }
		}
	}
}

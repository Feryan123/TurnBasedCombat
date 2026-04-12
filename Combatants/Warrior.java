package Combatants;

import Actions.BasicAttack;
import StatusEffects.StunEffect;

public class Warrior extends Player{
	public Warrior(String combatantName) {
		int HP = 260;
		int Atk = 40;
		int Def = 20;
		int Speed = 30;
		super(combatantName, HP, Atk, Def, Speed);
	}
	
	public void useSpecialSkill(Combatant target) {
		new BasicAttack().execute(this, target);
		target.addStatusEffect(new StunEffect(2));
	}
}

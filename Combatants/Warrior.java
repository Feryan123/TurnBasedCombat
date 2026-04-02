package Combatants;

public class Warrior extends Player{
	public Warrior(String combatantName) {
		int HP = 260;
		int Atk = 40;
		int Def = 20;
		int Speed = 30;
		super(combatantName, HP, Atk, Def, Speed);
	}
	
	public void useSpecialSkill(List<Combatant> targets) {
		for (Combatant target: targets) {
			new BasicAttack().execute(this, target, engine);
			target.addStatusEffect(new StunEffect());
		}
	}
}

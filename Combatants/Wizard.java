package Combatants;

public class Wizard extends Player {
	private int arcaneBlastBonus = 0;
	

	public Wizard(String combatantName) {
		int HP = 200;
		int Atk = 50;
		int Def = 10;
		int Speed = 20;
		super(combatantName, HP, Atk, Def, Speed);
	}
	
	public void useSpecialSkill(List<Combatant> targets, BattleEngine engine) {
		int kills = 0;
		for (Combatant target: targets) {
			new BasicAttack().execute(this, target, engine);
			if (!target.isAlive()) { kills++; }
		}
		increaseAttack(kills * 10);
	}
	
	public void increaseAttack(int amount) { arcaneBlastBonus += amount; }
}

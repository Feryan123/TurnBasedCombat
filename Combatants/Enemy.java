package Combatants;

public abstract class Enemy extends Combatant{
	private EnemyActionStrategy actionStrategy;
	
	public Enemy(String combatantName, int HP, int Atk, int Def, int Speed) {
		super(combatantName, HP, Atk, Def, Speed);
	}

	public void takeTurn(BattleEngine engine) {
		engine.spawnBackUpIfNeeded();
		engine.applyTurnStartEffect();
		if (!canAct()) return;
		Action action = decideAction(engine);
		List<Combatants> targets = engine.getAliveCombatants();
		for (Combatant target : targets) {
			action.execute(this, target, engine);
		}
		
	}
	public Action decideAction(BattleEngine engine) {
		return new BasicAttack();
	}

}

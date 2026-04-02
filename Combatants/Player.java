package Combatants;

import java.util.Scanner;

public abstract class Player extends Combatant {
	private Inventory inventory;
	
	public Player(String combatantName, int HP, int Atk, int Def, int Speed) {
		super(combatantName, HP, Atk, Def, Speed);
	}

	public void takeTurn(BattleEngine engine) {
		if (!canAct()) return;

	    engine.applyTurnStartEffects(this);

	    Action action = chooseAction();
	    action.execute(this, engine);

	    engine.processTurn();
	}
	
	public Action chooseAction() {
		Scanner sc = new Scanner(System.in);
		int action = sc.nextInt();
		
		switch (action) {
			case 1 -> return new UseItemAction();
			case 2 -> return new DefendAction();
			case 3 -> return new SpecialSkillAction();
			default -> return new BasicAttack();
		}
	}
	
	public void useSpecialSkill(List<Combatant> targets, BattleEngine engine) {
		for (Combatant target: targets) {
			new BasicAttack().execute(this, target, engine);
		}
	}
	
	public Inventory getInventory() { return inventory; }
	
}


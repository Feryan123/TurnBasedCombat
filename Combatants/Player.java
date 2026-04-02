package Combatants;

import java.util.Scanner;
import java.util.ArrayList;
import java.util.List;

public abstract class Player extends Combatant {
	private Inventory inventory;
	
	public Player(String combatantName, int HP, int Atk, int Def, int Speed) {
		super(combatantName, HP, Atk, Def, Speed);
	}

	public void takeTurn(BattleEngine engine) {
		super(engine);
		
	    Action action = chooseAction();
	    List<Combatants> targets = engine.getAliveCombatants();
		for (Combatant target : targets) {
			action.execute(this, target, engine);
		}

	    engine.processTurn();
	}
	
	public Action chooseAction(int choice) {
		Scanner sc = new Scanner(System.in);
		int action = sc.nextInt();
		
		switch (action) {
			case 1 : return new UseItemAction(); break;
			case 2 : return new DefendAction(); break;
			case 3 : return new SpecialSkillAction(); break;
			default : return new BasicAttack(); break;
		}
	}
	
	public void useSpecialSkill(List<Combatant> targets, BattleEngine engine) {
		for (Combatant target: targets) {
			new BasicAttack().execute(this, target, engine);
		}
	}
	
	public Inventory getInventory() { return inventory; }
	
}


package Combatants;

import Items.*;
import Actions.*;
import StatusEffects.*;
import Combatants.*;
import Control.BattleEngine;

public abstract class Player extends Combatant {
	private int skillcd;
	private Inventory inventory;
	
	public Player(String combatantName, int HP, int Atk, int Def, int Speed) {
		super(combatantName, HP, Atk, Def, Speed);
		this.skillcd = 0;
		this.inventory = new Inventory();
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
	
	public Action chooseAction() {
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
	
	public void decrementCooldown() { 
		if (skillcd > 0) { skillcd--; } 
		for (StatusEffect effect : effects) {
	        if (effect.isExpired()) {
	        	effect.onExpire();
	        	removeExpiredEffect(effect);
	        }
	    }
	}
	public void addItem(Item item) { inventory.addItem(item); }
	public void removeItem(Item item) { inventory.removeItem(item); }
	public void resetSkillCooldown(int cooldown) { this.skillcd = cooldown;}
	public int getSkillCooldown() {return this.skillcd;}
	public Inventory getInventory() { return inventory; }
	
}


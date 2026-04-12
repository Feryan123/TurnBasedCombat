package Combatants;

import java.util.ArrayList;
import java.util.List;

import actions.Action;
import actions.BasicAttack;
import Control.BattleEngine;

public abstract class Enemy extends Combatant{
	public Enemy(String combatantName, int HP, int Atk, int Def, int Speed) {
		super(combatantName, HP, Atk, Def, Speed);
	}

	@Override
	public void takeTurn(BattleEngine engine) {
		engine.processRound();
		if (!canAct()) return; 
		engine.processTurn(this);
		Action action = decideAction(engine);
		List<Combatant> targets = engine.getAliveCombatants();
		for (Combatant target : targets) {
			action.execute(this, target);
		}
		
	}
	public Action decideAction(BattleEngine engine) { return new BasicAttack(); }
}

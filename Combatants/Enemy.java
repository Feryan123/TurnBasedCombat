package Combatants;

import java.util.List;
import Actions.Action;
import Actions.BasicAttack;
import Control.BattleEngine;

public abstract class Enemy extends Combatant {
    // Constructor
    public Enemy(String combatantName, int HP, int Atk, int Def, int Speed) {
        super(combatantName, HP, Atk, Def, Speed);
    }

    // Methods
    public Action decideAction(BattleEngine engine) {
        return new BasicAttack();
    }
    public void takeTurn(BattleEngine engine) {
        if (!canAct()) return;

        Action action = decideAction(engine);
        List<Combatant> targets = engine.selectPlayerTargets(); // To be fixed: should be a List of Combatant, not a single Combatant. If only one target, return a list with that target as the only element.   

        if(!targets.isEmpty()) {
            action.execute(this, targets);
        }
    }
}
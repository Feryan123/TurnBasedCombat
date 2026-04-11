package Combatants;

import java.util.List;

import Actions.Action;
import Actions.BasicAttack;
import Control.BattleEngine;

public abstract class Enemy extends Combatant {

    public Enemy(String combatantName, int HP, int Atk, int Def, int Speed) {
        super(combatantName, HP, Atk, Def, Speed);
    }

    public void takeTurn(BattleEngine engine) {
        if (!canAct()) return;

        Action action = decideAction(engine);
        Combatant target = engine.selectPlayerTarget();

        if (target != null) {
            action.execute(this, target);
        }
    }

    public Action decideAction(BattleEngine engine) {
        return new BasicAttack();
    }
}
package Combatants;

import java.util.List;
import java.util.ArrayList;
import Actions.Action;
import Actions.BasicAttack;
import Control.BattleEngine;

public abstract class Enemy extends Combatant {
    public Enemy(String combatantName, int HP, int Atk, int Def, int Speed) {
        super(combatantName, HP, Atk, Def, Speed);
    }

    public Action decideAction(BattleEngine engine) {
        return new BasicAttack();
    }

    @Override
    public void takeTurn(BattleEngine engine) {
        if (!canAct()) {
            engine.getUI().showMessage(getName() + " is stunned and cannot act!");
            return;
        }

        Action action = decideAction(engine);
        List<Combatant> targets = engine.selectPlayerTargets();

        if (!targets.isEmpty()) {
            Combatant chosenTarget = targets.get(0);
            List<Combatant> selectedTarget = new ArrayList<>();
            selectedTarget.add(chosenTarget);

            action.execute(this, selectedTarget);
            engine.getUI().showMessage(getName() + " attacked " + chosenTarget.getName() + "!");
        }
    }
}
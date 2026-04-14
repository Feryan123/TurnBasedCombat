package StatusEffects;

import Combatants.Combatant;

public class DefendEffect extends StatusEffect {
    private int defenseBoost;

    public DefendEffect() {
        super("Defend", 2);
        this.defenseBoost = 10;
    }

    @Override
    public void onApply(Combatant target) {
        target.increaseDefense(defenseBoost);
    }

    @Override
    public void onExpire(Combatant target) {
        target.decreaseDefense(defenseBoost);
    }

    @Override
    public boolean preventsAction() {
        return false;
    }
}
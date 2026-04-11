package StatusEffects;

import Combatants.*;

public class ArcaneBuffEffect extends StatusEffect {
    private int atkIncrease = 10;
    private int kills;

    // Constructor
    public ArcaneBuffEffect(int kills){
        super("Arcane Buff", Integer.MAX_VALUE);
        this.kills = kills;
    }

    // Methods
    @Override
    public void onApply(Combatant target){
        target.increaseAttack(atkIncrease*kills);
    }
    @Override
    public void onExpire(Combatant target){
        // No expiration effect, this is a Permanent Buff
    }
}

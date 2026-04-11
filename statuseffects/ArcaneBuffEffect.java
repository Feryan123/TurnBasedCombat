package StatusEffects;
public class ArcaneBuffEffect extends StatusEffect {
    // Constructor
    public ArcaneBuffEffect(){
        super("Arcane Buff", Integer.MAX_VALUE);
    }

    // Methods
    @Override
    public void onApply(Combatant target){
        target.increaseAttack(10);
    }
    @Override
    public void onExpire(Combatant target){
        // No expiration effect, this is a Permanent Buff
    }
}

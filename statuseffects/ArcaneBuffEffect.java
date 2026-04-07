public class ArcaneBuffEffect extends StatusEffect {
    // Constructor
    public ArcaneBuffEffect(){
        super("Arcane Buff", Integer.MAX_VALUE);
    }

    // Methods
    @Override
    public void apply(Combatant target){
        target.increaseAttack(10);
    }
}

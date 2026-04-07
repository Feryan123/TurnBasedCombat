public class DefendBuffEffect extends StatusEffect {
    // Constructor
    public DefendBuffEffect(){
        super("Defend Buff", 2);
    }

    // Methods
    @Override
    public void apply(Combatant target){
        target.increaseDefense(10);
    }
    @Override
    public void onExpire(Combatant target){
        target.decreaseDefense(10);
    }
}

package StatusEffects;
public class StunEffect extends StatusEffect{
    // Constructor
    public StunEffect(int duration){
        super("Stun", duration);
    }

    // Methods
    @Override
    public void onApply(Combatant target){
        target.setCanAct(false);
    }
    @Override
    public void onExpire(Combatant target){
        target.setCanAct(true);
    }
    @Override
    public boolean preventsAction(){
        return true;
    }
}

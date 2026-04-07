public class SmokeBombEffect extends StatusEffect {
    // Constructor
    public SmokeBombEffect(){
        super("Smoke Bomb Effect", 2);
    }

    // Methods
    @Override
    public void apply(Combatant target){
        target.setIsDamageImmune(true); // Set if user is damage immune --> TBA in Combatant class
    }
    @Override
    public void onExpire(Combatant target){
        target.setIsDamageImmune(false);
    }
}

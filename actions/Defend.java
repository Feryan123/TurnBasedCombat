public class Defend implements Action {
    private int defenseIncrease = 10;
    // Methods
    @Override
    public void execute(Combatant user, Combatant target){
        if(!user.getCanAct()) return; // Check if user can act --> TBA in Combatant class
        user.increaseDefense(defenseIncrease);
        user.addEffect(new DefendBuff());
    }
}

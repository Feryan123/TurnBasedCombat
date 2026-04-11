package Actions;
public class BasicAttack implements Action {
    // Methods
    @Override
    public void execute(Combatant user, Combatant target){
        if(!user.canAct()) return; // Check if user can act
        int damage = Math.max(0, user.getAttack() - target.getDefense());
        target.takeDamage(damage);
    }
}
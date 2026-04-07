public class BasicAttack implements Action {
    // Methods
    @Override
    public void execute(Combatant user, Combatant target){
        if(!user.getCanAct()) return; // Check if user can act --> TBA in Combatant class
        int damage = Math.max(0, user.getAttack() - target.getDefense());
        target.takeDamage(damage);
    }
}
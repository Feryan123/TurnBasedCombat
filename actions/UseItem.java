public class UseItem implements Action {
    // Attributes
    private Item item;

    // Constructor
    public UseItem(Item item){
        this.item = item;
    }
    
    // Methods
    @Override
    public void execute(Combatant user, Combatant target){
        if(!user.getCanAct()) return; // Check if user can act --> TBA in Combatant class
        if(user instanceof Player){
            this.item.use(user);
            ((Player) user).removeItem(item);
        }
    }
}

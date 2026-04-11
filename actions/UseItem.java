package Actions;

import Items.*;
import Combatants.*;

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
        if(!user.canAct()) return; // Check if user can act
        if(user instanceof Player){
            this.item.use(user);
            ((Player) user).removeItem(item);
        }
    }
}

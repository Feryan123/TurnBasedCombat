package Actions;

import Items.*;
import Combatants.*;
import java.util.List;

public class UseItemAction implements Action {
    // Attributes
    private Item item;

    // Constructor
    public UseItemAction(Item item){
        this.item = item;
    }
    
    // Methods
    @Override
    public void execute(Combatant user, List<Combatant> targets){
        if(!user.canAct()) return; // Check if user can act
        if(user instanceof Player){
            this.item.use((Player) user);
            ((Player) user).removeItem(item);
        }
    }
}

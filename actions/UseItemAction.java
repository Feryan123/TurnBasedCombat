package Actions;

import java.util.List;
import Combatants.Combatant;
import Combatants.Player;
import Items.Item;

public class UseItemAction implements Action {
    private Item item;

    public UseItemAction(Item item) {
        this.item = item;
    }

    @Override
    public void execute(Combatant user, List<Combatant> targets) {
        if (user instanceof Player) {
            Player player = (Player) user;
            item.use(player, targets);
            player.removeItem(item);
        }
    }
}
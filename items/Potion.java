package Items;
public class Potion implements Item {
    private int healAmount = 100;
    
    // Methods
    @Override
    public void use(Combatant user){
        user.heal(healAmount);
    }
    @Override
    public String getName() {
        return "Potion";
    }
}

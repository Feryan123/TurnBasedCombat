public class SmokeBomb implements Item {
    // Methods
    @Override
    public void use(Combatant user){
        user.addEffect(new SmokeBombEffect());
    }
    @Override
    public String getName() {
        return "Smoke Bomb";
    }
}

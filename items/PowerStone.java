public class PowerStone implements Item {
    // Attribute
    private Combatant singleTarget;
    private List<Enemy> multiTargets;
    
    // Constructors
    public PowerStone(Combatant singleTarget){
        this.singleTarget = singleTarget;
        this.multiTargets = null;
    }
    public PowerStone(List<Enemy> multiTargets){
        this.singleTarget = null;
        this.multiTargets = multiTargets;
    }

    // Methods
    @Override
    public void use(Combatant user){
        if (user instanceof Warrior){
            int cooldown = ((Warrior) user).getSkillCooldown();
            ((Warrior) user).shieldBash(singleTarget);
            ((Warrior) user).resetSkillCooldown(cooldown);
        } else if (user instanceof Wizard) {
            int cooldown = ((Wizard) user).getSkillCooldown();
            ((Wizard) user).arcaneBlast(multiTargets);
            ((Wizard) user).resetSkillCooldown(cooldown);
        }
    }
    @Override
    public String getName() {
        return "Power Stone";
    }
}

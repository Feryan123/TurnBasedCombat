import java.util.List;

public class SpecialSkill implements Action {
    // Attributes
    private Combatant singleTarget;
    private List<Enemy> multiTargets;
    
    // Constructor
    public SpecialSkill(Combatant singleTarget){
        this.singleTarget = singleTarget;
        this.multiTargets = null;
    }
    public SpecialSkill(List<Enemy> multiTargets){
        this.singleTarget = null;
        this.multiTargets = multiTargets;
    }

    // Methods
    @Override
    public void execute(Combatant user, Combatant target){
        if(!user.getCanAct()) return;
        if (user instanceof Warrior){
            if(((Warrior) user).getSkillCooldown() != 0) return;
            ((Warrior) user).shieldBash(singleTarget);
        } else if (user instanceof Wizard) {
            if(((Wizard) user).getSkillCooldown() != 0) return;
            ((Wizard) user).arcaneBlast(multiTargets);
        }
    }
}

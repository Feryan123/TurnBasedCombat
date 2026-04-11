package Combatants;

import StatusEffects.StunEffect;

public class Warrior extends Player{
	public Warrior() {
		super("Warrior", 260, 40, 20, 30);
	}
	
    public void shieldBash(Combatant target){
        int damage = Math.max(0, getAttack() - target.getDefense());
        target.takeDamage(damage);
        target.addEffect(new StunEffect(2));
        resetSkillCooldown(3);
    }
}

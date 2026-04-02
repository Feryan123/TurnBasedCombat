package Combatants;

import java.util.ArrayList;
import java.util.List;

public abstract class Combatant implements StatusEffect, Action{
	private String name;
	private int maxHP;
	private int curHP;
	private int atk;
	private int def;
	private int speed;
	private int skillcd;
	private List<StatusEffect> effects = new ArrayList<>();
	
	public Combatant(String combatantName, int HP, int Atk, int Def, int Speed) {
		this.name = combatantName;
		this.maxHP = HP;
		this.curHP = maxHP;
		this.atk = Atk;
		this.def = Def;
		this.speed = Speed;
		this.skillcd = 0;
	}
	public void takeDamage(int amount) {
		curHP -= Math.max(0, amount - def);
		if (curHP <= 0) { curHP = 0; }
	}
	public void takeTurn(BattleEngine engine) {
		engine.processRound();
		if (!canAct()) return; 
		engine.processTurn(this);
	}
	public void heal(int amount) {
		curHP += amount;
		if (curHP > maxHP) {
			curHP = maxHP;
		}
	}
	public boolean isAlive() { return curHP > 0; }
	public boolean canAct() { 
		if (isAlive()) {
			for (StatusEffect effect : effects) {
		        if (effect.preventsAction()) { return false; }
		    }
			return true;
		}
		return false;
	}
	public void addStatusEffect(StatusEffect effect) {
		effects.add(effect);
	}
	public void removeExpiredEffect() {
		effects.removeIf(effect -> effect.isExpired());
	}
	public void applyStatusEffect() {
		for (StatusEffect effect : effects) {
	        effect.onApply(this);
	    }
	}
	public void decrementCooldown() { 
		if (skillcd > 0) { skillcd--; } 
		for (StatusEffect effect : effects) {
	        if (effect.isExpired()) {
	        	effect.onExpire();
	        	removeExpiredEffect(effect);
	        }
	    }
	}
}

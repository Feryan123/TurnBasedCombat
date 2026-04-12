package Combatants;

import java.util.ArrayList;
import java.util.List;

import actions.*;
import Control.*;
import statuseffects.*;

public abstract class Combatant implements Action{
	private String name;
	private int maxHP;
	private int curHP;
	private int atk;
	private int def;
	private int speed;
	private boolean immunity;
	private List<StatusEffect> effects = new ArrayList<>();
	
	public int getAttack() { return atk; }
	public int getDefense() { return def; }
	public String getName() { return name; }
	public int getCurrentHP() { return curHP; }
	public int getMaxHP() { return maxHP; }
	public int getSpeed() { return speed; }
	public List<StatusEffect> getEffects() { return effects; }

	public Combatant(String combatantName, int HP, int Atk, int Def, int Speed) {
		this.name = combatantName;
		this.maxHP = HP;
		this.curHP = maxHP;
		this.atk = Atk;
		this.def = Def;
		this.speed = Speed;
		this.immunity = false;
	}
	public void takeDamage(int amount) {
		curHP -= Math.max(0, amount);
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
			for (StatusEffect effect : getEffects()) {
		        if (effect.preventsAction()) { return false; }
		    }
			return true;
		}
		return false;
	}
	public void addStatusEffect(StatusEffect effect) {
		getEffects().add(effect);
	}

	public void removeExpiredEffect() {
		getEffects().removeIf(effect -> effect.isExpired());
	}
	public void applyStatusEffect() {
		for (StatusEffect effect : getEffects()) {
	        effect.onApply(this);
	    }
	}
	
	public void decrementCooldown() { 
		for (StatusEffect effect : getEffects()) {
	        if (effect.isExpired()) {
	        	effect.onExpire(this);
	        	removeExpiredEffect();
	        }
	    }
	}
	
	public void setIsDamageImmune(boolean op) { immunity = op; }
	public void increaseDefense(int value) { def += value; }
	public void decreaseDefense(int value) { def -= value; }
	public void increaseAttack(int amount) { atk += amount; }
	
}

package Combatants;

import java.util.ArrayList;
import java.util.List;
import StatusEffects.StatusEffect;
import Items.Inventory;
import Control.BattleEngine;

public abstract class Combatant{
	private String name;
	private int maxHP;
	private int curHP;
	private int atk;
	private int def;
	private int speed;
	private boolean damageImmune = false;
	private List<StatusEffect> effects = new ArrayList<>();
	
	public Combatant(String combatantName, int HP, int Atk, int Def, int Speed) {
		this.name = combatantName;
		this.maxHP = HP;
		this.curHP = maxHP;
		this.atk = Atk;
		this.def = Def;
		this.speed = Speed;
	}

	public String getName() {
		return this.name;
	}

	public int getCurrentHP() {
		return this.curHP;
	}

	public int getMaxHP() {
		return this.maxHP;
	}

	public void takeDamage(int amount) {
		if (damageImmune) { return; }
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
	public void setIsDamageImmune(boolean isImmune) {
		damageImmune = isImmune;
	}
	public void increaseDefense(int amount) {
		this.def += amount;
	}
	public void decreaseDefense(int amount) {
		this.def -= amount;
	}
	public void increaseAttack(int amount) {
		this.atk += amount;
	}
	public int getSpeed(){return this.speed;}
	public int getAttack(){return this.atk;}
	public int getDefense(){return this.def;}
	public void addEffect(StatusEffect effect) {
		effects.add(effect);
	}
	public void removeEffect(StatusEffect effect) {
		effects.remove(effect);
	}
}

package Combatants;

import java.util.ArrayList;
import java.util.List;
import StatusEffects.StatusEffect;
import Control.BattleEngine;

public abstract class Combatant{
	// Attributes
	private String name;
	private int maxHP;
	private int currentHP;
	private int attack;
	private int defense;
	private int speed;
	private boolean damageImmune = false;
	private List<StatusEffect> effects = new ArrayList<>();
	
	// Constructor
	public Combatant(String combatantName, int HP, int Atk, int Def, int Speed) {
		this.name = combatantName;
		this.maxHP = HP;
		this.currentHP = maxHP;
		this.attack = Atk;
		this.defense = Def;
		this.speed = Speed;
	}

	// Getters
	public String getName() {return this.name;}
	public int getCurrentHP() {return this.currentHP;}
	public int getMaxHP() {return this.maxHP;}
	public int getSpeed(){return this.speed;}
	public int getAttack(){return this.attack;}
	public int getDefense(){return this.defense;}

	// Setters
	public void setIsDamageImmune(boolean isImmune) {damageImmune = isImmune;}
	public void setCurrentHP(int HP) {
		this.currentHP = Math.min(HP, this.maxHP);
		if (this.currentHP < 0) {
			this.currentHP = 0;
		}
	}
	public void setAttack(int attack) {this.attack = attack;}
	public void setDefense(int defense) {this.defense = defense;}


	// Methods - Effects
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
	public void endTurnStatusEffect() {
		for (StatusEffect effect : effects) {
	        effect.onEndTurn();
	    }
	}
	public void removeEffect(StatusEffect effect) {
		effects.remove(effect);
	}

	// Method - Combat Actions
	public void takeDamage(int amount) {
		if (damageImmune) { return; }
		currentHP -= Math.max(0, amount - defense);
		if (currentHP <= 0) { currentHP = 0; }
	}
	public void takeTurn(BattleEngine engine) {
		engine.processRound();
		if (!canAct()) return; 
		engine.processTurn(this);
	}
	public boolean isAlive() { return currentHP > 0; }
	public boolean canAct() { 
		if (isAlive()) {
			for (StatusEffect effect : effects) {
		        if (effect.preventsAction()) { return false; }
		    }
			return true;
		}
		return false;
	}
}

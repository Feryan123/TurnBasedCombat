package Combatants;

import java.util.Scanner;
import java.util.ArrayList;
import java.util.List;
import actions.*;
import Control.*;
import items.*;
import statuseffects.*;

public abstract class Player extends Combatant {
	private Inventory inventory;
	private List<Item> items = new ArrayList<>();
	private int skillCD = 0;
	
	public Player(String combatantName, int HP, int Atk, int Def, int Speed) {
		super(combatantName, HP, Atk, Def, Speed);
	}
	
	public Action chooseAction(Action action) {
		return action;
	}
	
	public void useSpecialSkill(List<Enemy> targets) {
		for (Combatant target: targets) {
			new BasicAttack().execute(this, target);
		}
	}
	
	@Override
	public void decrementCooldown() { 
		if (skillCD > 0) { skillCD--; } 
		for (StatusEffect effect : getEffects()) {
	        if (effect.isExpired()) {
	        	effect.onExpire(this);
	        	removeExpiredEffect();
	        }
	    }
	}
	
	public void resetSkillCooldown(int cooldown) { skillCD = 0; }
	
	public Inventory getInventory() { return inventory; }
	public int getSkillCooldown() { return skillCD; }

	public void removeItem(Item item) { items.remove(item); }

	public void setItems(List<Item> Items) { items = Items; }

}

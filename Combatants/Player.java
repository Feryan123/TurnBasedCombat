package Combatants;

import Items.Inventory;
import Items.Item;
import Actions.Action;
import Actions.BasicAttack;
import java.util.List;
import Control.BattleEngine;

public abstract class Player extends Combatant {
    // Attributes
    private int specialSkillCooldown;
    private Action specialSkill;
    private Inventory inventory;

    // Constructor
    public Player(String combatantName, int HP, int Atk, int Def, int Speed, Action specialSkill) {
		super(combatantName, HP, Atk, Def, Speed);
		this.specialSkillCooldown = 0;
		this.specialSkill = specialSkill;
		this.inventory = new Inventory();
	}

    // Getter
    public int getSkillCooldown() {return this.specialSkillCooldown;}
    public Inventory getInventory() {return inventory;}
    public Action getSpecialSkill() {return this.specialSkill;}

    // Increase/Decrease Stats
	public void increaseDefense(int amount) {
		this.setDefense(this.getDefense() + amount);}
	public void decreaseDefense(int amount) {
		this.setDefense(this.getDefense() - amount);
	}

	public void increaseAttack(int amount) {
		this.setAttack(this.getAttack() + amount);
	}

	public void heal(int amount) {
		this.setCurrentHP(this.getCurrentHP() + amount);
	}

    public void decrementCooldown() {
        if (specialSkillCooldown > 0) {
            specialSkillCooldown--;
        }
    }

    public void resetSkillCooldown(int cooldown) {
        this.specialSkillCooldown = cooldown;
    }

    // Methods - Items
    public void addItem(Item item) {
        inventory.addItem(item);
    }
    public void removeItem(Item item) {
        inventory.removeItem(item);
    }    

    // Methods - Special Skill
    public void useSpecialSkill(List<Combatant> targets, BattleEngine engine) {
		for (Combatant target : targets) {
			new BasicAttack().execute(this, java.util.Arrays.asList(target));
		}
	}

}
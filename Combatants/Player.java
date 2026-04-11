package Combatants;

import Items.Inventory;
import Items.Item;

public abstract class Player extends Combatant {
    private int skillcd;
    private Inventory inventory;

    public Player(String combatantName, int HP, int Atk, int Def, int Speed) {
        super(combatantName, HP, Atk, Def, Speed);
        this.skillcd = 0;
        this.inventory = new Inventory();
    }

    public void decrementCooldown() {
        if (skillcd > 0) {
            skillcd--;
        }
    }

    public void addItem(Item item) {
        inventory.addItem(item);
    }

    public void removeItem(Item item) {
        inventory.removeItem(item);
    }

    public void resetSkillCooldown(int cooldown) {
        this.skillcd = cooldown;
    }

    public int getSkillCooldown() {
        return this.skillcd;
    }

    public Inventory getInventory() {
        return inventory;
    }
}
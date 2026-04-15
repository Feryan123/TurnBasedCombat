package Combatants;

import Items.Inventory;
import Items.Item;
import Actions.Action;
import Actions.BasicAttack;
import Actions.DefendAction;
import Actions.UseItemAction;
import Actions.ShieldBashAction;
import Actions.ArcaneBlastAction;
import java.util.List;
import java.util.ArrayList;
import Control.BattleEngine;

public abstract class Player extends Combatant {
    private int specialSkillCooldown;
    private Action specialSkill;
    private Inventory inventory;

    private Action queuedAction;
    private List<Combatant> queuedTargets;
	private boolean hasActedThisRound;

    public Player(String combatantName, int HP, int Atk, int Def, int Speed, Action specialSkill) {
        super(combatantName, HP, Atk, Def, Speed);
        this.specialSkillCooldown = 0;
        this.specialSkill = specialSkill;
        this.inventory = new Inventory();

        this.queuedAction = null;
        this.queuedTargets = new ArrayList<>();
		this.hasActedThisRound = false;
    }

    public int getSkillCooldown() { return this.specialSkillCooldown; }
    public Inventory getInventory() { return inventory; }
    public Action getSpecialSkill() { return this.specialSkill; }

	public boolean hasActedThisRound() {
		return hasActedThisRound;
	}

    public void increaseDefense(int amount) {
        this.setDefense(this.getDefense() + amount);
    }

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

    public void addItem(Item item) {
        inventory.addItem(item);
    }

    public void removeItem(Item item) {
        inventory.removeItem(item);
    }

    public void useSpecialSkill(List<Combatant> targets, BattleEngine engine) {
        for (Combatant target : targets) {
            new BasicAttack().execute(this, java.util.Arrays.asList(target));
        }
    }

    public void setQueuedTurn(Action action, List<Combatant> targets) {
        this.queuedAction = action;
        this.queuedTargets = new ArrayList<>(targets);
    }

    public void clearQueuedTurn() {
		this.queuedAction = null;
		this.queuedTargets.clear();
	}

	public void setHasActedThisRound(boolean hasActedThisRound) {
		this.hasActedThisRound = hasActedThisRound;
	}

    @Override
	public void takeTurn(BattleEngine engine) {
		if (!canAct()) {
			engine.getUI().showMessage(getName() + " is stunned and cannot act!");
			clearQueuedTurn();
			hasActedThisRound = true;
			return;
		}

		if (queuedAction == null) {
			return;
		}

		queuedAction.execute(this, queuedTargets);
		
		if (queuedAction instanceof BasicAttack && !queuedTargets.isEmpty()) {
			engine.getUI().showMessage(getName() + " attacked " + queuedTargets.get(0).getName() + "!");
		} 
		else if (queuedAction instanceof ShieldBashAction && !queuedTargets.isEmpty()) {
			engine.getUI().showMessage(getName() + " used Shield Bash on " + queuedTargets.get(0).getName() + "!");
			resetSkillCooldown(3);
		} 
		else if (queuedAction instanceof ArcaneBlastAction) {
			engine.getUI().showMessage(getName() + " used Arcane Blast!");
			resetSkillCooldown(3);
		}
		else if (queuedAction instanceof DefendAction) {
			engine.getUI().showMessage(getName() + " is defending! (+10 DEF)");
		}
		else if (queuedAction instanceof UseItemAction) {
		}

		clearQueuedTurn();
		hasActedThisRound = true;
	}
}
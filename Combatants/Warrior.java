package Combatants;

import Actions.ShieldBashAction;

public class Warrior extends Player{
    public Warrior() {
		super("Warrior", 260, 40, 20, 30, new ShieldBashAction());
	}
}

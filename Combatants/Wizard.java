package Combatants;

import Actions.ArcaneBlastAction;

public class Wizard extends Player {
	// Constructor
	public Wizard() {
		super("Wizard", 200, 50, 10, 20, new ArcaneBlastAction());
	}
}

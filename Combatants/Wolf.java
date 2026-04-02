package Combatants;

public class Wolf extends Enemy{
	public Wolf(String combatantName) {
		int HP = 40;
		int Atk = 45;
		int Def = 5;
		int Speed = 35;
		super(combatantName, HP, Atk, Def, Speed);
	}
}

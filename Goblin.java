package Combatants;

public class Goblin extends Enemy{
	public Goblin(String combatantName) {
		int HP = 55;
		int Atk = 35;
		int Def = 15;
		int Speed = 25;
		super(combatantName, HP, Atk, Def, Speed);
	}
}

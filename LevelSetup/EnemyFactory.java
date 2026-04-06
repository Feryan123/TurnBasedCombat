package LevelSetup;

import java.util.ArrayList;
import java.util.List;

public class EnemyFactory implements IEnemyFactory{

    public Goblin createGoblin() {
        // TODO: Ping Jin needs to create constructors, that I'll call w the below stats
        // Required Stats: HP: 55, Attack: 35, Defense: 15, Speed: 25
        return null; 
    }

    public Wolf createWolf() {
        // TODO: Ping Jin needs to create constructors, that I'll call w the below stats
        // Required Stats: HP: 40, Attack: 45, Defense: 5, Speed: 35
        return null; 
    }

    
    /*
     * LSP: Returns a generic List<Enemy>. 
     * Goblin and Wolf objects perfectly substitute their parent 
     * Enemy class without the Level needing to know the difference.
     */
    public List<Enemy> createEnemiesForLevel(Difficulty difficulty) {
    	/* Single Responsibility Principle (SRP). For createGoblin and createWolf calls within this function
    	* If we decide to change a Goblin's HP from 55 to 60:
    	* only have to change it in one place (the Factory); The rest of the game doesn't have to worry about it.
        */
    	List<Enemy> enemies = new ArrayList<>();
        
        switch (difficulty) {
            case EASY:
                // Initial Spawn: 3 Goblins
                enemies.add(createGoblin());
                enemies.add(createGoblin());
                enemies.add(createGoblin());
                break;
            case MEDIUM:
                // Initial Spawn: 1 Goblin, 1 Wolf
                enemies.add(createGoblin());
                enemies.add(createWolf());
                break;
            case HARD:
                // Initial spawn: 2 Goblins
                enemies.add(createGoblin());
                enemies.add(createGoblin());
                break;
        }
        
        return enemies;
    }
}
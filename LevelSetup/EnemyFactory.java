package LevelSetup;

import java.util.ArrayList;
import java.util.List;
import Combatants.*;

/**
 * EnemyFactory handles the concrete instantiation of enemies.
 * Adheres to the Factory Method pattern to decouple creation from game logic.
 */
public class EnemyFactory implements IEnemyFactory{

    @Override
    public Goblin createGoblin() {
        return new Goblin("Goblin"); // Instantiate Goblin with default stats
    }

    @Override
    public Wolf createWolf() {
        return new Wolf("Wolf"); // Instantiate Wolf with default stats
    }

    
    /*
     * LSP: Returns a generic List<Enemy>. 
     * Goblin and Wolf objects perfectly substitute their parent 
     * Enemy class without the Level needing to know the difference.
     */
    @Override
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
                break;
            case MEDIUM:
                // Initial Spawn: 1 Goblin, 1 Wolf
                enemies.add(createGoblin());
                enemies.add(createWolf());
                break;
            case HARD:
                // Initial spawn: 1 Goblin, 2 Wolves
                enemies.add(createGoblin());
                enemies.add(createWolf());
                enemies.add(createWolf());
                break;
        }
        
        return enemies;
    }
}

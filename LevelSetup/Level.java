package LevelSetup;

import java.util.ArrayList;
import java.util.List;
import Combatants.Enemy;

public class Level {
    private Difficulty difficulty;
    private boolean backupSpawned;
    private List<Enemy> initialEnemies;
    private List<Enemy> backupEnemies;
    private EnemyFactory factory;

    public Level(Difficulty difficulty) {
        this.difficulty = difficulty;
        this.backupSpawned = false;
        this.factory = new EnemyFactory();
        this.initialEnemies = new ArrayList<>();
        this.backupEnemies = new ArrayList<>();
    }

    public List<Enemy> spawnInitialEnemies() {
        this.initialEnemies = factory.createEnemiesForLevel(this.difficulty);
        return this.initialEnemies;
    }

    public List<Enemy> spawnBackupEnemies() {
    	// Easy mode has no backups, only generate for Medium and Hard
        if (this.difficulty == Difficulty.MEDIUM) {
            // Backup Spawn: 2 Wolf 
            this.backupEnemies.add(factory.createWolf());
            this.backupEnemies.add(factory.createWolf());
        } else if (this.difficulty == Difficulty.HARD) {
            // Backup Spawn: 1 Goblin, 2 Wolf 
            this.backupEnemies.add(factory.createGoblin());
            this.backupEnemies.add(factory.createWolf());
            this.backupEnemies.add(factory.createWolf());
        }
        
        this.backupSpawned = true;
        return this.backupEnemies;
    }

    public boolean shouldSpawnBackup() {
    	
        // 2. If we already spawned them, don't spawn them again
        if (this.backupSpawned) {
            return false;
        }
        
        // 3. Check if all initial enemies are dead. 
        // Based on  an isAlive() method!
        for (Enemy enemy : initialEnemies) {
            if (enemy.isAlive()) {
                return false; // Found a living enemy, so don't spawn backups yet
            }
        }
        
        // If  we loop without returning false, all enemies are dead => should spawn backup
        return true; 
    }
    
}

package LevelSetup;

import java.util.ArrayList;
import java.util.List;
import Combatants.*; // Imports Enemy, Goblin, and Wolf

public class Level {
    private Difficulty difficulty;
    private boolean backupSpawned;
    private List<Enemy> initialEnemies;
    private List<Enemy> backupEnemies;
    
    // 1. DIP: We now depend on the Interface, not the concrete class
    private IEnemyFactory factory; 

    // 2. CONSTRUCTOR INJECTION: Pass the factory in as a parameter
    public Level(Difficulty difficulty, IEnemyFactory factory) {
        this.difficulty = difficulty;
        this.factory = factory; 
        this.backupSpawned = false;
        this.initialEnemies = new ArrayList<>();
        this.backupEnemies = new ArrayList<>();
    }

    public List<Enemy> spawnInitialEnemies() {
    	// We use the factory method to get the first wave
        this.initialEnemies = factory.createEnemiesForLevel(this.difficulty);
        return this.initialEnemies;
    }

    public List<Enemy> spawnBackupEnemies() {
        // Easy mode has no backups, only generate for Medium and Hard
        
        if (this.difficulty == Difficulty.MEDIUM) {
            this.backupEnemies.add(factory.createWolf());
            this.backupEnemies.add(factory.createWolf());
            // Backup Spawn: 2 Wolf 
            
        } 
        else if (this.difficulty == Difficulty.HARD) {
            this.backupEnemies.add(factory.createGoblin());
            this.backupEnemies.add(factory.createWolf());
            this.backupEnemies.add(factory.createWolf());
            // Backup Spawn: 1 Goblin, 2 Wolf 
        }
        
        this.backupSpawned = true;
        return this.backupEnemies;
    }

    public boolean shouldSpawnBackup() {
        // 1. Easy mode never has backups 
        if (this.difficulty == Difficulty.EASY) {
            return false;
        }
        
        // 2. If we already spawned them, don't spawn them again
        if (this.backupSpawned) {
            return false;
        }
        // 3. Check if all initial enemies are dead. Based on  an isAlive() method
        for (Enemy enemy : initialEnemies) {
            if (enemy.isAlive()) {
                return false; // Found a living enemy, so don't spawn backups yet
            }
        }
        // If  we loop without returning false, all enemies are dead => should spawn backup
        return true; 
    }
}

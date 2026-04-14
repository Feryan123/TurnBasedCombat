package LevelSetup;

import java.util.List;
import Combatants.*;

public interface IEnemyFactory {
    Goblin createGoblin();
    Wolf createWolf();
    List<Enemy> createEnemiesForLevel(Difficulty difficulty);
}

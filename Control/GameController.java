package Control;

import Boundary.GameUI;
import Combatants.Player;
import LevelSetup.Difficulty;
import LevelSetup.Level;
import Items.Item;
import Strategy.TurnOrderStrategy;
import Strategy.SpeedBasedTurnOrder;
import LevelSetup.EnemyFactory;
import java.util.List;

public class GameController {

    private GameUI ui;
    private BattleEngine battleEngine;
    public GameController() {
        ui = new GameUI();
        battleEngine = new BattleEngine(new java.util.ArrayList<>(), new SpeedBasedTurnOrder(new java.util.ArrayList<>()));
    }

    public void startGame() {
        ui.displayWelcomeScreen();
        setupGame();
        startBattle();
    }

    public void setupGame() {
        Player player = ui.displayCharacterOptions();
        ui.showMessage("You have selected: " + player.getName());
        ui.showMessage("");

        List<Item> items = ui.displayItemOptions();
        for (Item item : items) {
            player.addItem(item);
        }
        Difficulty difficulty = ui.displayDifficultyOptions();
        Level level = new Level(difficulty, new EnemyFactory());

        battleEngine.initialise(player, level);
    }   

    public void startBattle() {
        battleEngine.startBattle();
    }

    public void restartGame() {
        setupGame();
        startBattle();
    }
}

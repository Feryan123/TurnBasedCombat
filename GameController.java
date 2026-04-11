package control;

import boundary.GameUI;
import entity.combatant.Player;
import entity.level.Difficulty;
import entity.level.Level;
import entity.item.Item;
import strategy.TurnOrderStrategy;
import factory.LevelFactory;
import java.util.List;

public class GameController {

    private GameUI ui;
    private BattleEngine battleEngine;

    public GameControllerVersion(){
        ui = new GameUI();
        battleEngine = new BattleEngine(new SpeedBasedTurnOrder());
    }

    public void startGame() {
        ui.displayWelcomeScreen();
        setupGame();
        startBattle();
    }

    public void setupGame() {
        Player player = ui.displayCharacterOptions();
        List<Item> items = ui.displayItemOptions();
        player.setItems(items);
        Difficulty difficulty = ui.displayDifficultyOptions();
        Level level = LevelFactory.createLevel(difficulty);
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

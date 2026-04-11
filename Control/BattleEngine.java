package Control;

import Combatants.*;
import Actions.*;
import Strategy.*;
import LevelSetup.*;
import java.util.List;
import java.util.stream.Collectors;

public class BattleEngine {
    private List<Combatant> combatants;
    private TurnOrderStrategy turnOrderStrategy;
    private Level level;
    private int currentRound;

    public BattleEngine(List<Combatant> combatants, TurnOrderStrategy turnOrderStrategy) {
        this.combatants = combatants;
        this.turnOrderStrategy = new SpeedBasedTurnOrder(); 
        this.level = new Level(Difficulty.MEDIUM); // Initialize Level with a Difficulty -- Placeholder, can be set via GameController
        this.currentRound = 0;
    }

    public void startBattle() {
        while (!checkLoseCondition() && !checkWinCondition()) {
            processRound();
        }
        if (checkLoseCondition()) {
            ui.showDefeatScreen();
        } else {
            ui.showVictoryScreen();
        }
    }

    public void processRound() {
        currentRound += 1;
        List<Combatant> turnOrder = turnOrderStrategy.getTurnOrder(getAliveCombatants());
        for (Combatant actor : turnOrder) {
            if (actor.isAlive()) {
                processTurn(actor);
            }
        }
    }

    public void processTurn(Combatant actor) {
        if (actor instanceof Player) {
            ui.displayActionMenu();
            int choice = ui.getPlayerChoice();

            Action action = switch (choice) {
                case 1 -> new BasicAttack();
                case 2 -> new Defend();
                case 3 -> new UseItem();
                case 4 -> new SpecialSkill();
                default -> new BasicAttack();
            };

            Combatant target = selectTarget(); 
            action.execute(actor, target);
        } else if (actor instanceof Enemy) {
            Combatant target = selectPlayerTarget(); 
            new BasicAttack().execute(actor, target);
        }
    }

    private Combatant selectTarget() {
        // Placeholder for target selection logic
        return combatants.stream().filter(Combatant::isAlive).findFirst().orElse(null);
    }

    private Combatant selectPlayerTarget() {
        // Placeholder for selecting a player target
        return combatants.stream().filter(c -> c instanceof Player && c.isAlive()).findFirst().orElse(null);
    }

    public boolean checkLoseCondition() {
        return getAliveCombatants().stream()
                .noneMatch(c -> c instanceof Player);
    }

    public boolean checkWinCondition() {
        return getAliveCombatants().stream()
                .noneMatch(c -> c instanceof Enemy);
    }

    public List<Combatant> getAliveCombatants() {
        return combatants.stream()
                .filter(c -> c.isAlive())
                .collect(Collectors.toList());
    }
}

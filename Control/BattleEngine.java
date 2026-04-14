package Control;

import Combatants.*;
import Actions.*;
import Strategy.*;
import LevelSetup.*;
import java.util.List;
import java.util.stream.Collectors;
import Boundary.GameUI;

public class BattleEngine {
    private List<Combatant> combatants;
    private TurnOrderStrategy turnOrderStrategy;
    private Level level;
    private int currentRound;
    private GameUI ui;

    public BattleEngine(List<Combatant> combatants, TurnOrderStrategy turnOrderStrategy) {
        this.combatants = combatants;
        this.turnOrderStrategy = turnOrderStrategy;
        this.level = null;
        this.currentRound = 0;
        this.ui = new GameUI();
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
        List<Combatant> turnOrder = turnOrderStrategy.getOrder(getAliveCombatants());
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

            Combatant target = selectTarget();
            if (target != null) {
                Action action = new BasicAttack();
                action.execute(actor, target);
            }
        } else if (actor instanceof Enemy) {
            Combatant target = selectPlayerTarget();
            if (target != null) {
                Action action = new BasicAttack();
                action.execute(actor, target);
            }
        }
    }

    public Combatant selectEnemyTargets() { // To be fixed: Need to return a List of Combatant, not a single Combatant. If only one target, return a list with that target as the only element.
        return combatants.stream()
                .filter(c -> c instanceof Enemy && c.isAlive())
                .findFirst()
                .orElse(null);
    }

    private Combatant selectPlayerTargets() { // To be fixed: Need to return a List of Combatant, not a single Combatant. If only one target, return a list with that target as the only element.
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

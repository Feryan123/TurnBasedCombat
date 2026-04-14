package Control;

import Combatants.*;
import Actions.*;
import Strategy.*;
import LevelSetup.*;
import java.util.List;
import java.util.stream.Collectors;
import Boundary.GameUI;
import java.util.ArrayList;

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

    public void initialise(Player player, Level level) {
        this.level = level;
        this.combatants.clear();
        this.combatants.add(player);
        this.combatants.addAll(level.spawnInitialEnemies());
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
            ui.displayBattleStatus(getAliveCombatants());
            ui.displayActionMenu();
            int choice = ui.getPlayerChoice();

            List<Combatant> possibleTargets = selectEnemyTargets();
            if (!possibleTargets.isEmpty()) {
                ui.displayTargetOptions(possibleTargets);
                Combatant chosenTarget = ui.getTargetChoice(possibleTargets);

                List<Combatant> selectedTarget = new ArrayList<>();
                selectedTarget.add(chosenTarget);

                Action action = new BasicAttack();
                action.execute(actor, selectedTarget);
            }
        } else if (actor instanceof Enemy) {
            List<Combatant> possibleTargets = selectPlayerTargets();
            if (!possibleTargets.isEmpty()) {
                List<Combatant> selectedTarget = new ArrayList<>();
                selectedTarget.add(possibleTargets.get(0));

                Action action = new BasicAttack();
                action.execute(actor, selectedTarget);
            }
        }
    }

    public List<Combatant> selectEnemyTargets() {
        return combatants.stream()
                .filter(c -> c instanceof Enemy && c.isAlive())
                .collect(Collectors.toList());
    }

    public List<Combatant> selectPlayerTargets() {
        return combatants.stream()
                .filter(c -> c instanceof Player && c.isAlive())
                .collect(Collectors.toList());
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

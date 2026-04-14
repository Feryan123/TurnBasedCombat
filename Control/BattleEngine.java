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
    private Action queuedPlayerAction;
    private List<Combatant> queuedPlayerTargets;

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
        currentRound++;
        ui.displayBattleStatus(getAliveCombatants());

        // Queue player action first
        queuedPlayerAction = null;
        queuedPlayerTargets = new ArrayList<>();

        for (Combatant combatant : combatants) {
            if (combatant instanceof Player && combatant.isAlive()) {
                boolean validChoice = false;

                while (!validChoice) {
                    ui.displayActionMenu();
                    int choice = ui.getPlayerChoice();

                    if (choice == 1) {
                        List<Combatant> possibleTargets = selectEnemyTargets();
                        if (!possibleTargets.isEmpty()) {
                            ui.displayTargetOptions(possibleTargets);
                            Combatant chosenTarget = ui.getTargetChoice(possibleTargets);
                            queuedPlayerTargets.add(chosenTarget);
                            queuedPlayerAction = new BasicAttack();
                            validChoice = true;
                        }
                    } else if (choice == 2) {
                        // Apply defend immediately
                        Action action = new DefendAction();
                        action.execute(combatant, new ArrayList<>());

                        ui.showMessage(combatant.getName() + " is defending! (+10 DEF)");

                        // Do NOT queue anything
                        queuedPlayerAction = null;

                        validChoice = true;
                    } else {
                        ui.showMessage("Option not implemented yet. Choose again.");
                    }
                }

                break;
            }
        }

        List<Combatant> turnOrder = turnOrderStrategy.getOrder(getAliveCombatants());

        for (Combatant actor : turnOrder) {
            if (actor.isAlive()) {
                processTurn(actor);
            }
        }
    }

    public void processTurn(Combatant actor) {

        if (!actor.isAlive() || !actor.canAct()) {
            return;
        }

        if (actor instanceof Player) {
            if (queuedPlayerAction != null) {
                queuedPlayerAction.execute(actor, queuedPlayerTargets);

                if (queuedPlayerAction instanceof BasicAttack && !queuedPlayerTargets.isEmpty()) {
                    ui.showMessage(actor.getName() + " attacked " + queuedPlayerTargets.get(0).getName() + "!");
                }
            }
        } else if (actor instanceof Enemy) {
            List<Combatant> targets = selectPlayerTargets();
            if (!targets.isEmpty()) {
                Combatant chosenTarget = targets.get(0);

                List<Combatant> selectedTarget = new ArrayList<>();
                selectedTarget.add(chosenTarget);

                Action action = new BasicAttack();
                action.execute(actor, selectedTarget);

                ui.showMessage(actor.getName() + " attacked " + chosenTarget.getName() + "!");
            }
        }
        actor.endTurnStatusEffect();
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

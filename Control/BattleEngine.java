package Control;

import Combatants.*;
import Actions.*;
import Strategy.*;
import LevelSetup.*;
import java.util.List;
import java.util.stream.Collectors;
import Boundary.GameUI;
import Items.PowerStone;
import Items.Item;  
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
                Player player = (Player) combatant;
                if (player.getSkillCooldown() > 0) {
                    player.decrementCooldown();
                }
                boolean validChoice = false;

                while (!validChoice) {
                    ui.displayActionMenu(player);
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
                        Action action = new DefendAction();
                        action.execute(player, new ArrayList<>());
                        ui.showMessage(player.getName() + " is defending! (+10 DEF)");
                        queuedPlayerAction = null;
                        validChoice = true;

                    } else if (choice == 3) {
                        List<Item> items = player.getInventory().getItems();

                        if (items.isEmpty()) {
                            ui.showMessage("You have no items. Choose another action.");
                            System.out.println();
                            continue;
                        }

                        boolean itemChosen = false;
                        while (!itemChosen) {
                            ui.displayInventoryOptions(items);
                            int itemChoice = ui.getInventoryChoice(items.size());

                            if (itemChoice == items.size() + 1) {
                                System.out.println();
                                break; // Back
                            }

                            Item selectedItem = items.get(itemChoice - 1);
                            List<Combatant> itemTargets = new ArrayList<>();

                            if (selectedItem instanceof PowerStone) {
                                Action specialSkill = player.getSpecialSkill();

                                if (specialSkill instanceof ArcaneBlastAction) {
                                    itemTargets = selectEnemyTargets();
                                } else {
                                    List<Combatant> possibleTargets = selectEnemyTargets();
                                    if (!possibleTargets.isEmpty()) {
                                        ui.displayTargetOptions(possibleTargets);
                                        Combatant chosenTarget = ui.getTargetChoice(possibleTargets);
                                        itemTargets.add(chosenTarget);
                                    }
                                }
                            }
                            
                            ui.showMessage(player.getName() + " used " + selectedItem.getName() + "!");
                            Action action = new UseItemAction(selectedItem);
                            action.execute(player, itemTargets);
                            
                            queuedPlayerAction = null;
                            queuedPlayerTargets.clear();

                            itemChosen = true;
                            validChoice = true;
                        }
                    } else if (choice == 4) {
                        if (player.getSkillCooldown() > 0) {
                            ui.showMessage("Special Skill is on cooldown. Choose another action.");
                            System.out.println(); 
                            continue;
                        }

                        Action specialSkill = player.getSpecialSkill();

                        if (specialSkill instanceof ArcaneBlastAction) {
                            queuedPlayerTargets = selectEnemyTargets();
                            queuedPlayerAction = specialSkill;
                            validChoice = true;
                        } else {
                            List<Combatant> possibleTargets = selectEnemyTargets();
                            if (!possibleTargets.isEmpty()) {
                                ui.displayTargetOptions(possibleTargets);
                                Combatant chosenTarget = ui.getTargetChoice(possibleTargets);
                                queuedPlayerTargets.clear();
                                queuedPlayerTargets.add(chosenTarget);
                                queuedPlayerAction = specialSkill;
                                validChoice = true;
                            }
                        }
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
            Player player = (Player) actor;

            if (queuedPlayerAction != null) {
                queuedPlayerAction.execute(actor, queuedPlayerTargets);

                if (queuedPlayerAction instanceof BasicAttack && !queuedPlayerTargets.isEmpty()) {
                    ui.showMessage(actor.getName() + " attacked " + queuedPlayerTargets.get(0).getName() + "!");
                } else if (queuedPlayerAction instanceof ShieldBashAction && !queuedPlayerTargets.isEmpty()) {
                    ui.showMessage(actor.getName() + " used Shield Bash on " + queuedPlayerTargets.get(0).getName() + "!");
                    player.resetSkillCooldown(3);
                } else if (queuedPlayerAction instanceof ArcaneBlastAction) {
                    ui.showMessage(actor.getName() + " used Arcane Blast!");
                    player.resetSkillCooldown(3);
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
        spawnBackupIfNeeded();
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

    public void spawnBackupIfNeeded() {
        if (level != null && level.shouldSpawnBackup()) {
            List<Enemy> backupEnemies = level.spawnBackupEnemies();
            combatants.addAll(backupEnemies);
            ui.showMessage("Backup enemies have arrived!");
        }
    }

}

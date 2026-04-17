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

    public BattleEngine(List<Combatant> combatants, TurnOrderStrategy turnOrderStrategy) {
        this.combatants = combatants;
        this.turnOrderStrategy = turnOrderStrategy;
        this.level = null;
        this.currentRound = 0;
        this.ui = new GameUI();
    }

    public GameUI getUI() {
        return ui;
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

        Player player = null;

        for (Combatant combatant : combatants) {
            if (combatant instanceof Player && combatant.isAlive()) {
                player = (Player) combatant;
                break;
            }
        }

        if (player != null) {
            player.clearQueuedTurn();
            player.setHasActedThisRound(false);

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

                        List<Combatant> targets = new ArrayList<>();
                        targets.add(chosenTarget);

                        player.setQueuedTurn(new BasicAttack(), targets);
                        validChoice = true;
                    }

                } else if (choice == 2) {
                    player.setQueuedTurn(new DefendAction(), new ArrayList<>());
                    player.takeTurn(this);   // immediate execution
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
                            break;
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

                        player.setQueuedTurn(new UseItemAction(selectedItem), itemTargets);
                        player.takeTurn(this);   // immediate execution
                        player.endTurnStatusEffect();
                        spawnBackupIfNeeded();
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
                        player.setQueuedTurn(specialSkill, selectEnemyTargets());
                        validChoice = true;
                    } else {
                        List<Combatant> possibleTargets = selectEnemyTargets();

                        if (!possibleTargets.isEmpty()) {
                            ui.displayTargetOptions(possibleTargets);
                            Combatant chosenTarget = ui.getTargetChoice(possibleTargets);

                            List<Combatant> targets = new ArrayList<>();
                            targets.add(chosenTarget);

                            player.setQueuedTurn(specialSkill, targets);
                            validChoice = true;
                        }
                    }
                }
            }
        }

        List<Combatant> turnOrder = turnOrderStrategy.getOrder(getAliveCombatants());

        for (Combatant actor : turnOrder) {
            if (!actor.isAlive()) {
                continue;
            }

            if (actor instanceof Player) {
                Player currentPlayer = (Player) actor;

                if (currentPlayer.hasActedThisRound()) {
                    continue;
                }
            }

            processTurn(actor);
        }
    }

    public void processTurn(Combatant actor) {

        actor.takeTurn(this);
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

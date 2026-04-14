package Boundary;

import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;

import Combatants.Combatant;
import Combatants.Enemy;
import Combatants.Player;
import Combatants.Warrior;
import Combatants.Wizard;
import Items.Item;
import Items.Potion;
import Items.PowerStone;
import Items.SmokeBomb;
import LevelSetup.Difficulty;

public class GameUI {
    private final Scanner scanner;

    public GameUI() {
        this.scanner = new Scanner(System.in);
    }

    public void displayWelcomeScreen() {
        System.out.println("======================================");
        System.out.println("       TURN-BASED COMBAT GAME         ");
        System.out.println("======================================");
        System.out.println("Welcome to the arena.");
        System.out.println();
    }

    public void showVictoryScreen() {
        System.out.println();
        System.out.println("======================================");
        System.out.println("               VICTORY                ");
        System.out.println("======================================");
        System.out.println("All enemies have been defeated.");
        System.out.println();
    }

    public void showDefeatScreen() {
        System.out.println();
        System.out.println("======================================");
        System.out.println("                DEFEAT                ");
        System.out.println("======================================");
        System.out.println("Your player has fallen.");
        System.out.println();
    }

    private int getValidatedChoice(int min, int max) {
        while (true) {
            try {
                System.out.print("Enter choice: ");
                int choice = scanner.nextInt();
                scanner.nextLine();

                if (choice >= min && choice <= max) {
                    return choice;
                }

                System.out.println("Please enter a number between " + min + " and " + max + ".");
            } catch (InputMismatchException e) {
                System.out.println("Invalid input. Please enter a number.");
                scanner.nextLine();
            }
        }
    }

    public Difficulty displayDifficultyOptions() {
        System.out.println();
        System.out.println("Choose difficulty:");
        System.out.println("1. EASY");
        System.out.println("2. MEDIUM");
        System.out.println("3. HARD");
        System.out.println();

        int choice = getValidatedChoice(1, 3);

        switch (choice) {
            case 1:
                return Difficulty.EASY;
            case 2:
                return Difficulty.MEDIUM;
            case 3:
                return Difficulty.HARD;
            default:
                throw new IllegalStateException("Unexpected difficulty choice.");
        }
    }

    public Player displayCharacterOptions() {
        System.out.println("Choose your character:");
        System.out.println("1. Warrior");
        System.out.println("2. Wizard");
        System.out.println();

        int choice = getValidatedChoice(1, 2);

        switch (choice) {
            case 1:
                return new Warrior();
            case 2:
                return new Wizard();
            default:
                throw new IllegalStateException("Unexpected character choice.");
        }
    }

    public List<Item> displayItemOptions() {
        List<Item> selectedItems = new ArrayList<>();

        System.out.println("Choose 2 starting items.");
        System.out.println("Duplicates are allowed.");
        System.out.println();

        while (selectedItems.size() < 2) {
            System.out.println("Available items:");
            System.out.println("1. Potion");
            System.out.println("2. Smoke Bomb");
            System.out.println("3. Power Stone");
            System.out.println();

            System.out.println("Pick item " + (selectedItems.size() + 1) + " of 2:");
            int choice = getValidatedChoice(1, 3);

            Item chosenItem = null;

            switch (choice) {
                case 1:
                    chosenItem = new Potion();
                    break;
                case 2:
                    chosenItem = new SmokeBomb();
                    break;
                case 3:
                    chosenItem = new PowerStone();
                    break;
                default:
                    throw new IllegalStateException("Unexpected item choice.");
            }

            selectedItems.add(chosenItem);

            System.out.println();
            System.out.println("You selected: " + chosenItem.getName());
            System.out.println("Current selected items:");
            showItems(selectedItems);
            System.out.println();
        }

        System.out.println("Item selection complete.");
        System.out.println("Your starting items are:");
        System.out.println();
        showItems(selectedItems);
        System.out.println();

        return selectedItems;
    }

    public void displayBattleStatus() {
        System.out.println("======================================");
        System.out.println("             BATTLE STATUS            ");
        System.out.println("======================================");
        System.out.println("Battle status display not wired yet.");
        System.out.println();
    }

    public void displayBattleStatus(List<Combatant> combatants) {
        System.out.println("======================================");
        System.out.println("             BATTLE STATUS            ");
        System.out.println("======================================");

        if (combatants == null || combatants.isEmpty()) {
            System.out.println("No combatants to display.");
            System.out.println();
            return;
        }

        for (Combatant combatant : combatants) {
            String role;
            if (combatant instanceof Player) {
                role = "Player";
            } else if (combatant instanceof Enemy) {
                role = "Enemy";
            } else {
                role = "Combatant";
            }

            System.out.println(role + ": " + combatant.getName());
            System.out.println("HP: " + combatant.getCurrentHP() + "/" + combatant.getMaxHP()
                    + " | ATK: " + combatant.getAttack()
                    + " | DEF: " + combatant.getDefense()
                    + " | SPD: " + combatant.getSpeed());
            System.out.println("--------------------------------------");
        }

        System.out.println();
    }

    public void showMessage(String message) {
        System.out.println(message);
    }

    public void showItems(List<Item> items) {
        if (items == null || items.isEmpty()) {
            System.out.println("No items available.");
            return;
        }

        for (int i = 0; i < items.size(); i++) {
            System.out.println((i + 1) + ". " + items.get(i).getName());
        }
    }

    public void displayActionMenu() {
        System.out.println("Choose an action:");
        System.out.println("1. Basic Attack");
        System.out.println("2. Defend");
        System.out.println("3. Use Item");
        System.out.println("4. Special Skill");
        System.out.println();
    }

    public int getPlayerChoice() {
        return getValidatedChoice(1, 4);
    }

}
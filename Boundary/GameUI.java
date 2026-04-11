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
        System.out.println("Choose difficulty:");
        System.out.println("1. EASY");
        System.out.println("2. MEDIUM");
        System.out.println("3. HARD");

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

        int choice = getValidatedChoice(1, 2);

        switch (choice) {
            case 1:
                return new Warrior();
            case 2:
                return new Wizard("Wizard");
            default:
                throw new IllegalStateException("Unexpected character choice.");
        }
    }

    public List<Item> displayItemOptions() {
        List<Item> selectedItems = new ArrayList<>();

        System.out.println("Choose your starting items.");
        System.out.println("You may pick multiple items.");
        System.out.println("1. Potion");
        System.out.println("2. Smoke Bomb");
        System.out.println("3. Power Stone");
        System.out.println("4. Finish selection");

        while (true) {
            int choice = getValidatedChoice(1, 4);

            switch (choice) {
                case 1:
                    selectedItems.add(new Potion());
                    System.out.println("Added: Potion");
                    break;
                case 2:
                    selectedItems.add(new SmokeBomb());
                    System.out.println("Added: Smoke Bomb");
                    break;
                case 3:
                    selectedItems.add(new PowerStone((Combatant) null));
                    System.out.println("Added: Power Stone");
                    break;
                case 4:
                    if (selectedItems.isEmpty()) {
                        System.out.println("No items selected.");
                    } else {
                        System.out.println("Item selection complete.");
                    }
                    System.out.println();
                    return selectedItems;
                default:
                    throw new IllegalStateException("Unexpected item choice.");
            }
        }
    }

    

}
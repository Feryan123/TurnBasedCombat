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
}
# TurnBasedCombat

🧙‍♂️ Turn-Based Combat Arena

A console-based turn-based combat game built in Java, applying Object-Oriented Design (OODP) principles and SOLID design patterns.

This project was developed as part of SC2002 – Object-Oriented Design & Programming. This README.md is generated with the help of GenerativeAI (Not to be part of the project's grading, but for reference when entering this GitHub page.


🎮 Overview

This system simulates a turn-based battle between a player and enemies, where each round is processed through a central game engine.

Players can:

Perform basic attacks
Defend
Use items
Activate special skills

Enemies automatically decide their actions and interact with the player through the same combat system.

The design emphasises modularity, extensibility, and clean separation of concerns using an ECB (Entity-Control-Boundary) architecture.


🏗️ System Architecture

The project is structured into the following components:

🔹 Control Layer
BattleEngine → Core game loop (handles rounds, turns, win/lose logic)
GameController → Sets up and starts the game

🔹 Boundary Layer
GameUI → Handles all user input/output

🔹 Entity Layer
Combatant → Base class for all characters
Player, Enemy → Abstract subclasses
Wizard, Warrior, Goblin, Wolf → Concrete implementations

🔹 Gameplay Components
Action → Defines actions like attack, defend, skills
Item & Inventory → Item system
StatusEffect → Buffs/debuffs (e.g. stun, defense boost)

🔹 Strategy Layer
TurnOrderStrategy → Determines turn order (e.g. speed-based)

🔹 Level Setup
Level + IEnemyFactory → Handles enemy spawning logic


🧠 Design Principles Used

✅ OOP Concepts
Encapsulation → Data hidden inside classes

Inheritance → Shared behaviour via Combatant

Polymorphism → Actions, items, and enemies interchangeable

Abstraction → Interfaces and abstract classes

✅ SOLID Principles
SRP → Each class has a single responsibility

OCP → Extend via new classes (Actions, Effects, Items)

LSP → Subclasses usable as parent types

ISP → Small focused interfaces (Action, Item)

DIP → Depends on abstractions (TurnOrderStrategy, IEnemyFactory)


🚀 How to Run

Clone the repo

git clone https://github.com/your-repo-link

Open in IntelliJ / VSCode

Run: GameController.java


👥 Team

Gan Ching Heng

Bu Ping Jin

Gajulapalli Anish Reddy

Feryan Krishany Jonandri

Dhawan Khushi

This project is based on the SC2002 group assignment specification.

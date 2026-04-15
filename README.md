# TurnBasedCombat

🧙‍♂️ Turn-Based Combat Arena

A console-based turn-based combat game built in Java, applying Object-Oriented Design (OODP) principles and SOLID design patterns.

This project was developed as part of SC2002 – Object-Oriented Design & Programming.

🎮 Overview

This system simulates a turn-based battle between a player and enemies, where each round is processed through a central game engine.

Players can:

Perform basic attacks
Defend
Use items
Activate special skills

Enemies automatically decide their actions and interact with the player through the same combat system.

The design emphasises modularity, extensibility, and clean separation of concerns using an ECB (Entity-Control-Boundary) architecture .

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
⚙️ Key Features
1. Turn-Based Engine
Centralised in BattleEngine.processRound()
Uses a strategy pattern to determine turn order
Supports queued and immediate player actions
2. Flexible Combat System
All characters inherit from Combatant
Shared mechanics:
HP, attack, defense, speed
Status effects
Turn execution via takeTurn()
3. Action System (Polymorphic)
All actions implement a common Action interface
Examples:
BasicAttack
DefendAction
UseItemAction
Special skills

New actions can be added without modifying existing code.

4. Status Effect System
StatusEffect is an abstract class
Applied dynamically to combatants
Effects:
Modify stats (buff/debuff)
Prevent actions (e.g. stun)
Expire over time

Handled via:

endTurnStatusEffect()
5. Inventory & Items
Players own an Inventory
Items are independent objects (aggregation relationship)
Examples:
Potion (heal)
SmokeBomb (immunity)
PowerStone (trigger skills)
6. Strategy Pattern (Turn Order)
BattleEngine depends on TurnOrderStrategy
Example:
SpeedBasedTurnOrder

Allows easy extension:

Random order
Priority-based turns
Boss mechanics
7. Factory Pattern (Enemy Creation)
IEnemyFactory decouples enemy creation from Level
Supports multiple game modes:
Standard mode
Boss mode
Endless mode
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
Run:
GameController.java
📌 Example Gameplay Flow
Game starts → Player selects character & items
BattleEngine initialises level
Each round:
Player chooses action
Turn order is calculated
Each combatant executes takeTurn()
Status effects are updated
Game ends when:
Player dies ❌
All enemies defeated ✅
🛠️ Possible Improvements
More enemy AI (smarter decideAction())
Additional turn order strategies
More status effects (e.g. poison, lifesteal)
GUI instead of console UI
Multiplayer support
👥 Team
Gan Ching Heng
Bu Ping Jin
Gajulapalli Anish Reddy
Feryan Krishany Jonandri
Dhawan Khushi
📄 Reference

This project is based on the SC2002 group assignment specification

# Roleplaying-Gear-Game

## 📘 Overview

This is a lab project for the *Program Design Paradigms (CS 5010)* course at Northeastern University. The goal of this assignment is to model an RPG-style character equipment system in Java, supporting dynamic gear combinations and an automated turn-based battle system between two characters.

## 🧩 Problem Description

Characters in many RPGs can wear gear that boosts either their attack or defense stats. The gear is categorized into:

- **Head Gear**: Provides defense only (attack = 0)
- **Hand Gear**: Provides attack only (defense = 0)
- **Footwear**: Can provide both attack and defense

Each item has:
- A name consisting of an adjective and a noun (e.g., *Happy Helmet*)
- Specific attack and defense values

## 🔗 Gear Combination Rules

Characters can wear:
- 1 Head Gear
- 2 Hand Gear items
- 2 Footwear items

If a character picks up a gear item but the relevant slots are full:
- The item is **combined** with an existing item of the same type
- The new item takes:
  - The **adjective from the weaker item**
  - The **full name from the stronger item**
  - Sum of attack and defense values
  - "Stronger" is determined first by attack, then defense, then randomly if tied

### 🧪 Example Combination

Combining:

- `Scurrying Sandals` (Attack: 0, Defense: 1)  
- `Happy HoverBoard` (Attack: 1, Defense: 3)  
→ Result: `Scurrying, Happy HoverBoard` (Attack: 1, Defense: 4)

## ⚔️ Battle Simulation

A separate `Battle` class simulates a turn-based battle:

- Accepts 2 characters and a list of 10 gear items
- Characters take turns choosing one item per round until all are picked

### 🧠 Item Selection Rules

1. Prefer items that fit in available slots
2. Prefer higher attack
3. If tied, prefer higher defense
4. If still tied, pick randomly

### 🏁 Battle Outcome

After all items are picked:
- Compute final attack/defense stats
- Determine winner based on damage taken:
  - `damage = opponent’s attack - your defense`
  - Lower damage wins; if tied → tie declared

## 📦 Features

- Abstract gear and character modeling
- Polymorphic behavior for gear types
- Automatic gear combination logic
- Rule-based turn-taking system
- Deterministic winner evaluation



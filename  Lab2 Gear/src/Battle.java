import java.util.List;
import java.util.ArrayList;

public class Battle {

    private Player player1;
    private Player player2;

    private List<Gear> gears;

    public Battle(Player player1, Player player2, List<Gear> gears) {
        // check arguments
        if (player1 == null || player2 == null || gears == null) {
            throw new IllegalArgumentException("Invalid Arguments - Battle");
        }
        if (player1 == player2) {
            throw new IllegalArgumentException("Players cannot be the same");
        }
        this.player1 = player1;
        this.player2 = player2;
        this.gears = gears;
    }

    //Constructor without gears
    public Battle(Player player1, Player player2) {
        this(player1, player2, new ArrayList<>());
    }

    public Player getPlayer1(){
        return player1;
    }

    public Player getPlayer2(){
        return player2;
    }

    public List<Gear> getBattleGears(){
        return gears;
    }

    public void addGear(Gear gear) {
        // check argument
        if (gear == null) {
            throw new IllegalArgumentException("Argument cannot be null");
        }
        // check if gear already exists
        if (gears.contains(gear)) {
            throw new IllegalArgumentException("Gear already exists");
        }
        gears.add(gear);
    }

    public void addGear(List<Gear> newGears) {
        if (newGears == null) {
            throw new IllegalArgumentException("Argument cannot be null");
        }
        for (Gear gear : newGears) {
            if (this.gears.contains(gear)) {
                throw new IllegalArgumentException("Gear already exists: " + gear.getFullName());
            }
        }
        this.gears.addAll(newGears);
    }


    public void dressPlayers() {
        if (gears.isEmpty()) {
            throw new IllegalStateException("No gears available to distribute.");
        }

        // Alternating turns between players
        boolean isPlayer1Turn = true;

        while (!gears.isEmpty()) {
            Player currentPlayer = isPlayer1Turn ? player1 : player2;

            // Find the best gear for the current player based on rules
            Gear bestGear = findBestGearForPlayer(currentPlayer);

            if (bestGear != null) {
                currentPlayer.assignGearToPlayer(bestGear); // Equip the chosen gear
                gears.remove(bestGear); // Remove the gear from the pool
            }

            isPlayer1Turn = !isPlayer1Turn; // Switch turns
        }
    }

//    private Gear findBestGearForPlayer(Player player) {
//        // Filter gears by available slots (Rule 1)
//        List<Gear> filteredGears = new ArrayList<>();
//        for (Gear gear : gears) {
//            switch (gear.getType()) {
//                case HEAD:
//                    if (player.getHeadGear() == null) {
//                        filteredGears.add(gear);
//                    }
//                    break;
//                case HAND:
//                    if (player.getHandGears().size() < Player.MAX_HAND_GEAR) {
//                        filteredGears.add(gear);
//                    }
//                    break;
//                case FOOT:
//                    if (player.getFootWears().size() < Player.MAX_FOOT_WEAR) {
//                        filteredGears.add(gear);
//                    }
//                    break;
//            }
//        }
//
//        if (filteredGears.isEmpty()) {
//            return null; // No suitable gear for this player
//        }
//
//        // Sort gears by attack strength (Rule 2) and defense strength (Rule 3)
//        filteredGears.sort((g1, g2) -> {
//            if (g1.getAttackStrength() != g2.getAttackStrength()) {
//                return Integer.compare(g2.getAttackStrength(), g1.getAttackStrength());
//            }
//            return Integer.compare(g2.getDefenseStrength(), g1.getDefenseStrength());
//        });
//
//        // Return the best gear, breaking ties randomly (Rule 4)
//        int highestAttack = filteredGears.get(0).getAttackStrength();
//        int highestDefense = filteredGears.get(0).getDefenseStrength();
//        List<Gear> tiedGears = new ArrayList<>();
//        for (Gear gear : filteredGears) {
//            if (gear.getAttackStrength() == highestAttack && gear.getDefenseStrength() == highestDefense) {
//                tiedGears.add(gear);
//            }
//        }
//
//        return tiedGears.get((int) (Math.random() * tiedGears.size())); // Pick randomly if there's a tie
//    }
    private Gear findBestGearForPlayer(Player player) {
        // Prioritize head gear if slot is empty
        if (player.getHeadGear() == null) {
            List<Gear> headGears = new ArrayList<>();
            for (Gear gear : gears) {
                if (gear.getType() == GearType.HEAD) {
                    headGears.add(gear);
                }
            }
            if (!headGears.isEmpty()) {
                // Pick the head gear with the highest defense
                headGears.sort((g1, g2) -> Integer.compare(g2.getDefenseStrength(), g1.getDefenseStrength()));
                return headGears.get(0);
            }
        }
        // If hand gear slot is not full, choose from hand gears
        if (player.getHandGears().size() < Player.MAX_HAND_GEAR) {
            List<Gear> handGears = new ArrayList<>();
            for (Gear gear : gears) {
                if (gear.getType() == GearType.HAND) {
                    handGears.add(gear);
                }
            }
            if (!handGears.isEmpty()) {
                handGears.sort((g1, g2) -> Integer.compare(g2.getAttackStrength(), g1.getAttackStrength()));
                return handGears.get(0);
            }
        }
        // If foot wear slot is not full, choose from foot wears
        if (player.getFootWears().size() < Player.MAX_FOOT_WEAR) {
            List<Gear> footWears = new ArrayList<>();
            for (Gear gear : gears) {
                if (gear.getType() == GearType.FOOT) {
                    footWears.add(gear);
                }
            }
            if (!footWears.isEmpty()) {
                footWears.sort((g1, g2) -> {
                    int cmp = Integer.compare((g2.getAttackStrength() + g2.getDefenseStrength()),
                            (g1.getAttackStrength() + g1.getDefenseStrength()));
                    return cmp;
                });
                return footWears.get(0);
            }
        }
        return null;
    }

    //This will print out what the character is wearing after each turn.
    public void printCharacterState(Player player) {
        System.out.println("Player: " + player.getPlayerName());

        // Print equipment
        System.out.println("HeadGear: " + (player.getHeadGear() != null ? player.getHeadGear().getFullName() : "None"));
        System.out.println("HandGear: " + (player.getHandGears().isEmpty() ? "None" : player.getHandGears().get(0).getFullName()));
        System.out.println("FootWear: " + (player.getFootWears().isEmpty() ? "None" : player.getFootWears().get(0).getFullName()));

        // Print total attack and defense strength
        System.out.println("Attack Strength: " + player.getTotalAttack());
        System.out.println("Defense Strength: " + player.getTotalDefense());
        System.out.println("--------------------------------------------");
    }

    public String battleOutcome() {
            if (player1 == null || player2 == null) {
                throw new IllegalStateException("Players cannot be null before starting");
            }

            // Get attack and defense values for Player 1
            int attackOfP1 = player1.getTotalAttack();
            int defenseOfP1 = player1.getTotalDefense();

            // Get attack and defense values for Player 2
            int attackOfP2 = player2.getTotalAttack();
            int defenseOfP2 = player2.getTotalDefense();

            // Calculate damage for Player 1 (damage is opponent's attack - player's defense)
            int damageToP1 = attackOfP2 - defenseOfP1;

            // Calculate damage for Player 2
            int damageToP2 = attackOfP1 - defenseOfP2;

            StringBuilder result = new StringBuilder();

            // Compare the damage and determine the winner
            if (damageToP1 < damageToP2) {
                result.append("========    Player 1 WON    ========\n");
            } else if (damageToP1 > damageToP2) {
                result.append("========    Player 2 WON    ========\n");
            } else {
                result.append("========         TIE        ========\n");
            }

            // Print the damage
            result.append("Player 1 Damage: " + damageToP1 + "\n");
            result.append("Player 2 Damage: " + damageToP2 + "\n");

            // Print the final result
            result.append("\n====================================\n");
            return result.toString();

    }

    @Override
    public String toString() {
        return "Battle between " + player1.getPlayerName() + " and " + player2.getPlayerName() + " with gear: " + gears;
    }



}

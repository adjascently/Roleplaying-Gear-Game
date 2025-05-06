import java.util.ArrayList;
import java.util.List;

public class Player {

    private final String playerName;
    private final int baseAttackPower;
    private final int baseDefenseStrength;

    public static final int MAX_FOOT_WEAR = 2;
    public static final int MAX_HAND_GEAR = 2;
    public static final int MAX_HEAD_GEAR = 1;

    // The gears of the players
    private HeadGear headGears;
    private final List<Gear> handGears;
    private final List<Gear> footWears;

    public Player(String playerName, int baseAttackPower, int baseDefenseStrength) {
        if (playerName == null || playerName.isEmpty()) {
            throw new IllegalArgumentException("Player Name cannot be null or empty");
        }
        this.playerName = playerName;
        this.baseAttackPower = baseAttackPower;
        this.baseDefenseStrength = baseDefenseStrength;
        handGears = new ArrayList<>();
        footWears = new ArrayList<>();
        headGears = null;
    }

    public Player(String playerName) {
        this(playerName, 0, 0);
    }

    //Getter for name
    public String getPlayerName(){
        return playerName;
    }

    public HeadGear getHeadGear() {
        return headGears;
    }

    public List<Gear> getHandGears() {
        return handGears;
    }

    public List<Gear> getFootWears() {
        return footWears;
    }


    // Defense strength for hand gears and foot wears only
    public int getTotalAttack() {
        int totalAttack = baseAttackPower;
        for (Gear curr : handGears) {
            totalAttack += curr.getAttackStrength();
        }
        for (Gear curr : footWears) {
            totalAttack += curr.getAttackStrength();
        }
        return totalAttack;
    }

    // Defense strength for head gears and foot wears only
//    public int getTotalDefense() {
//        int totalDefense = baseDefenseStrength;
//        totalDefense += headGears.getDefenseStrength();
//        for (Gear curr : footWears) {
//            totalDefense += curr.getDefenseStrength();
//        }
//        return totalDefense;
//    }
    public int getTotalDefense() {
        int totalDefense = baseDefenseStrength;
        if (headGears != null) {
            totalDefense += headGears.getDefenseStrength();
        }
        for (Gear curr : footWears) {
            totalDefense += curr.getDefenseStrength();
        }
        return totalDefense;
    }


    public void assignGearToPlayer(Gear gear) {
            if (gear == null) {
                throw new IllegalArgumentException("Gear cannot be null");
            }

            switch (gear.getType()) {
                case HEAD:
                    if (headGears != null) {
                        // If a headgear is already equipped, throw an exception or message
                        throw new IllegalStateException("HeadGear is already equipped. Choose other equipment.");
                    } else {
                        // Equip the headgear if none is equipped
                        headGears = (HeadGear) gear;
                    }
                    break;

                case HAND:
                    // Equip to hand gear slot (List)
                    equipToSlot(handGears, gear, MAX_HAND_GEAR);
                    break;

                case FOOT:
                    // Equip to foot wear slot (List)
                    equipToSlot(footWears, gear, MAX_FOOT_WEAR);
                    break;

                default:
                    throw new IllegalArgumentException("Unknown gear type: " + gear.getType());
            }
        }

        private void equipToSlot(List<Gear> slot, Gear gear, int maxSlotSize) {
            if (gear == null) {
                throw new IllegalArgumentException("Gear cannot be null");
            }

            // If there is space in the slot, simply add the gear
            if (slot.size() < maxSlotSize) {
                slot.add(gear);
            } else {
                // Otherwise, combine with the first gear in the slot
                Gear combinedGear = slot.get(0).combine(gear);  // Assuming combine() works for all gear types
                // Remove the first gear and add the combined gear
                slot.remove(0);  // Using remove(0) for ArrayList, if using LinkedList, use removeFirst()
                slot.add(combinedGear);
            }

    }

    @Override
    public String toString() {
        return "Player {" +
                "name='" + playerName + '\'' +
                ", attack=" + getTotalAttack() +
                ", defense=" + getTotalDefense() +
                ", headGear=" + headGears +
                ", handGears=" + handGears +
                ", footWears=" + footWears +
                '}';
    }

}

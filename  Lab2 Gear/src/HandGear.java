public class HandGear extends BaseGear{

    private static final int NO_DEFENSE_POWER = 0;

    public HandGear(String adjective, String noun,  int attackStrength, int defenseStrength) {
        super(adjective, noun, attackStrength, NO_DEFENSE_POWER, GearType.HAND);
    }

    public Gear newGear(String newAdjective, String newNoun,int newAttack, int newDefense) {
        return new HandGear(newAdjective, newNoun, newAttack, newDefense);
    }

    @Override
    public String toString() {
        return "HandGear (" +
                "noun='" + getNoun() + '\'' +
                ", adjective='" + getAdjective() + '\'' +
                ", attack=" + getAttackStrength() +
                ", defense=" + getDefenseStrength() +
                ')';
    }
}

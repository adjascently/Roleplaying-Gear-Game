public class HeadGear extends BaseGear{

    private static final int NO_ATTACK_POWER = 0;

    public HeadGear(String adjective, String noun,  int attackStrength, int defenseStrength) {
        super(adjective, noun,  NO_ATTACK_POWER, defenseStrength, GearType.HEAD);
    }

    @Override
    public Gear newGear(String newAdjective, String newNoun,int newAttack, int newDefense) {
        return new HeadGear(newAdjective, newNoun, newAttack, newDefense);
    }

    @Override
    public String toString() {
        return "HeadGear (" +
                "noun='" + getNoun() + '\'' +
                ", adjective='" + getAdjective() + '\'' +
                ", attack=" + getAttackStrength() +
                ", defense=" + getDefenseStrength() +
                ')';
    }

}

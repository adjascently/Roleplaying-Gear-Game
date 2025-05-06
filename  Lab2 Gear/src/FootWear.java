public class FootWear extends BaseGear {

    public FootWear(String adjective, String noun,  int attackStrength, int defenseStrength) {
        super( adjective, noun, attackStrength, defenseStrength, GearType.FOOT);

    }

    public Gear newGear(String newAdjective, String newNoun,int newAttack, int newDefense) {
        return new FootWear(newAdjective, newNoun, newAttack, newDefense);
    }

    @Override
    public String toString() {
        return "FootWear (" +
                "noun='" + getNoun() + '\'' +
                ", adjective='" + getAdjective() + '\'' +
                ", attack=" + getAttackStrength() +
                ", defense=" + getDefenseStrength() +
                ')';
    }

}

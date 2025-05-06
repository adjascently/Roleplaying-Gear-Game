public interface Gear {

    String getNoun();

    String getAdjective();

    int getAttackStrength();

    int getDefenseStrength();

    GearType getType();

    String getFullName();

    Gear combine(Gear other);

    String toString();
}

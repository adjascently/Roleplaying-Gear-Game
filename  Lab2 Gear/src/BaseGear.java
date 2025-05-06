 public abstract class BaseGear implements Gear {

    private String noun;
    private String adjective;
    private int attackStrength;
    private int defenseStrength;
    private final GearType type;


    public BaseGear(String adjective, String noun, int attackStrength, int defenseStrength, GearType type) {
        if (noun == null || noun.isEmpty() || adjective == null || adjective.isEmpty()) {
            throw new IllegalArgumentException("Gear Name cannot be empty or null");
        }

        if (type == null ){
            throw new IllegalArgumentException("Gear Type cannot be empty or null");
        }

        this.adjective = adjective;
        this.noun = noun;
        this.attackStrength = attackStrength;
        this.defenseStrength = defenseStrength;
        this.type = type;
    }

    @Override
    public String getNoun() {
        return noun;
    }

    @Override
    public String getAdjective() {
        return adjective;
    }

     @Override
     public GearType getType() {
         return type;
     }

    @Override
    public int getAttackStrength() {
        return attackStrength;
    }

    @Override
    public int getDefenseStrength() {
        return defenseStrength;
    }

     @Override
     public String getFullName() {
         return getAdjective() + " " + getNoun();
     }

    abstract public Gear newGear(String newFullName, String newAdjective, int newAttack, int newDefense);

    @Override
    public Gear combine(Gear other) {
        if (other == null) {
            throw new IllegalArgumentException("Gear cannot be null");
        }
        // check types
        if (other.getType() != this.getType()) {
            throw new IllegalArgumentException("Gears must be of the same type to combine");
        }


        String newAdjective;
        String newFullName;

        // Determining which item keeps the full name - based on the algorithm rules
        if (this.getAttackStrength() > other.getAttackStrength() ||
                (this.getAttackStrength() == other.getAttackStrength() &&
                        this.getDefenseStrength() > other.getDefenseStrength()) ||
                (this.getAttackStrength() == other.getAttackStrength() &&
                        this.getDefenseStrength() == other.getDefenseStrength() &&
                        Math.random() > 0.5)) {

            newAdjective = other.getAdjective(); // Keep this item's full name; use other's adjective
            newFullName = this.getFullName();
        } else {
            newAdjective = this.getAdjective(); // Keep other's full name; use this item's adjective
            newFullName = other.getFullName();
        }

        // Combine attack and defense strengths
        int newAttack = this.getAttackStrength() + other.getAttackStrength();
        int newDefense = this.getDefenseStrength() + other.getDefenseStrength();

        return newGear(newFullName, newAdjective, newAttack, newDefense);
    }

     @Override
     public String toString() {
         return "Gear (" +
                 ", type=" + type +
                 ", adjective='" + adjective + '\'' +
                 ", noun='" + noun + '\'' +
                 ", attack=" + attackStrength +
                 ", defense=" + defenseStrength +
                 ')';
     }
}

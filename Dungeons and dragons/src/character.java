import java.awt.*;

abstract class Character {
    protected String name;
    protected int strength;
    protected int intelligence;
    protected int wisdom;
    protected int dexterity;
    protected int constitution;
    protected int charisma;
    protected String alignment;

    public Character(String name, int strength, int intelligence, int wisdom, int dexterity, int constitution, int charisma, String alignment) {
        this.name = name;
        this.strength = strength;
        this.intelligence = intelligence;
        this.wisdom = wisdom;
        this.dexterity = dexterity;
        this.constitution = constitution;
        this.charisma = charisma;
        this.alignment = alignment;
    }

    public abstract void displayInfo();

    // Métodos comunes para todos los personajes
}

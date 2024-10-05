public class Elf extends Character{
    public Elf(String name, int strength, int intelligence, int wisdom, int dexterity, int constitution, int charisma, String alignment) {
        super(name, strength, intelligence, wisdom, dexterity, constitution, charisma,alignment);
    }

    @Override
    public void displayInfo() {
        System.out.println("Elfo: " + name);
        System.out.println("Fuerza: " + strength);
        System.out.println("Inteligencia: " + intelligence);
        System.out.println("Sabiduría: " + wisdom);
        System.out.println("Destreza: " + dexterity);
        System.out.println("Constitución: " + constitution);
        System.out.println("Carisma: " + charisma);
        System.out.println("Alineamiento: " + alignment);
    }
}

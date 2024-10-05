import java.util.HashMap;
import java.util.Map;
import java.util.ArrayList;
import java.util.List;

class CharacterFactory {
    private Map<String, int[]> minimumAttributes;
    private Map<String, int[]> maximumAttributes;
    private Map<String, List<String>> mainAttributes;
    public CharacterFactory() {
        minimumAttributes = new HashMap<>();
        maximumAttributes = new HashMap<>();
        mainAttributes = new HashMap<>();
        // Definición de características mínimas y máximas para cada clase
        minimumAttributes.put("cleric", new int[]{0, 0, 0, 0, 0, 0}); //FUE, INT, SAB, DES, CON, CAR
        maximumAttributes.put("cleric", new int[]{18, 18, 18, 18, 18, 18});
        mainAttributes.put("cleric", List.of("sabiduría"));

        minimumAttributes.put("warrior", new int[]{0, 0, 0, 0, 0, 0});
        maximumAttributes.put("warrior", new int[]{18, 18, 18, 18, 18, 18});
        mainAttributes.put("warrior", List.of("fuerza"));

        minimumAttributes.put("thief", new int[]{0, 0, 0, 0, 0, 0});
        maximumAttributes.put("thief", new int[]{18, 18, 18, 18, 18, 18});
        mainAttributes.put("thief", List.of("destreza"));

        minimumAttributes.put("halfling", new int[]{0, 0, 0, 9, 9, 0});
        maximumAttributes.put("hlafling", new int[]{18, 18, 18, 18, 18, 18});
        mainAttributes.put("halfling", List.of("destreza", "fuerza"));

        minimumAttributes.put("wizard", new int[]{0, 0, 0, 0, 0, 0});
        maximumAttributes.put("wizard", new int[]{18, 18, 18, 18, 18, 18});
        mainAttributes.put("wizard", List.of("inteligencia"));

        minimumAttributes.put("dwarf", new int[]{0, 0, 0, 0, 9, 0});
        maximumAttributes.put("dwarf", new int[]{18, 18, 18, 18, 18, 18});
        mainAttributes.put("dwarf", List.of("fuerza"));

        minimumAttributes.put("elf", new int[]{0, 9, 0, 0, 0, 0});
        maximumAttributes.put("elf", new int[]{18, 18, 18, 18, 18, 18});
        mainAttributes.put("elf", List.of("inteligencia", "fuerza"));
    }

    public List<String> checkClasses(int strength, int intelligence, int wisdom, int dexterity, int constitution, int charisma) {
        List<String> validClasses = new ArrayList<>();

        for (Map.Entry<String, int[]> entry : minimumAttributes.entrySet()) {
            String characterClass = entry.getKey();
            int[] minAttributes = entry.getValue();

            // Verificar si el personaje cumple con los requisitos mínimos
            if (strength >= minAttributes[0] &&
                    intelligence >= minAttributes[1] &&
                    wisdom >= minAttributes[2] &&
                    dexterity >= minAttributes[3] &&
                    constitution >= minAttributes[4] &&
                    charisma >= minAttributes[5]) {
                validClasses.add(characterClass); // Añadir clase válida a la lista
            }
        }

        return validClasses; // Retornar las clases válidas
    }


    public List<String> getMainAttributes(String characterClass) {
        return mainAttributes.getOrDefault(characterClass.toLowerCase(), new ArrayList<>()); // Devuelve una lista vacía si no se encuentra la clase
    }

    // Cambiar el acceso a public
    public Character createCharacterFromType(String type, String name, int strength, int intelligence, int wisdom, int dexterity, int constitution, int charisma, String alignment) {
        switch (type.toLowerCase()) {
            case "cleric":
                return new Cleric(name, strength, intelligence, wisdom, dexterity, constitution, charisma, alignment);
            case "warrior":
                return new Warrior(name, strength, intelligence, wisdom, dexterity, constitution, charisma, alignment);
            case "thief":
                return new Thief(name, strength, intelligence, wisdom, dexterity, constitution, charisma, alignment);
            case "halfling":
                return new Halfling(name, strength, intelligence, wisdom, dexterity, constitution, charisma, alignment);
            case "wizard":
                return new Wizard(name, strength, intelligence, wisdom, dexterity, constitution, charisma, alignment);
            case "dwarf":
                return new Dwarf(name, strength, intelligence, wisdom, dexterity, constitution, charisma, alignment);
            case "elf":
                return new Elf(name, strength, intelligence, wisdom, dexterity, constitution, charisma, alignment);
            default:
                return null;
        }
    }

}

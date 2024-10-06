import java.util.Scanner;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        ConsoleInterface console = new ConsoleInterface();
        console.start();
    }
}


class ConsoleInterface {
    private Scanner scanner = new Scanner(System.in);
    private CharacterFactory factory = new CharacterFactory();

    public void start() {
        System.out.println("Bienvenido al sistema de creación de personajes.");
        System.out.print("Introduce el nombre del personaje: ");
        String name = scanner.nextLine();

        // Lanzar dados para los atributos
        int strength = Dice.roll3d6();
        int intelligence = Dice.roll3d6();
        int wisdom = Dice.roll3d6();
        int dexterity = Dice.roll3d6();
        int constitution = Dice.roll3d6();
        int charisma = Dice.roll3d6();

        // Mostrar los atributos generados
        System.out.println("Atributos generados:");
        System.out.println("Fuerza: " + strength);
        System.out.println("Inteligencia: " + intelligence);
        System.out.println("Sabiduría: " + wisdom);
        System.out.println("Destreza: " + dexterity);
        System.out.println("Constitución: " + constitution);
        System.out.println("Carisma: " + charisma);

        // Comprobar las clases que cumplen con los requisitos
        List<String> validClasses = factory.checkClasses(strength, intelligence, wisdom, dexterity, constitution, charisma);

        List<String> validAlignments = List.of("legal", "neutral", "caótico");

        // Solicitar alineamiento al usuario con validación
        String alignment;
        while (true) {
            System.out.print("Decide tu alineamiento (Legal, Neutral, Caótico): ");
            alignment = scanner.nextLine().toLowerCase();
            if (validAlignments.contains(alignment)) {
                break; // Salir del bucle si el alineamiento es válido
            } else {
                System.out.println("Alineamiento no válido. Por favor, elige uno de los alineamientos disponibles.");
            }
        }

        // Mostrar las clases válidas
        if (!validClasses.isEmpty()) {
            System.out.println("Las siguientes clases cumplen con los requisitos:");
            for (String validClass : validClasses) {
                System.out.println(validClass);
            }
        } else {
            System.out.println("No se cumplen los requisitos para ninguna clase.");
            return; // Terminar el proceso si no hay clases válidas
        }
        String type;
        while (true) {
            System.out.print("Elige una clase de las disponibles: ");
            type = scanner.nextLine().toLowerCase();
            if (validClasses.contains(type)) {
                break;
            } else {
                System.out.println("Clase no válida. Por favor, elige una de las clases disponibles.");
            }
        }

        // Permitir ajustes de atributos
        adjustAttributes(type, name, strength, intelligence, wisdom, dexterity, constitution, charisma, alignment);
    }

    private void adjustAttributes(String type, String name, int strength, int intelligence, int wisdom, int dexterity, int constitution, int charisma, String alignment) {
        boolean adjusting = true;
        while (adjusting) {
            System.out.print("¿Deseas ajustar tus atributos? (sí/no): ");
            String response = scanner.nextLine().toLowerCase();

            if (response.equals("sí")) {
                // Mostrar atributos actuales
                System.out.println("Atributos actuales:");
                System.out.println("Fuerza: " + strength);
                System.out.println("Inteligencia: " + intelligence);
                System.out.println("Sabiduría: " + wisdom);
                System.out.println("Destreza: " + dexterity);
                System.out.println("Constitución: " + constitution);
                System.out.println("Carisma: " + charisma);

                // Mostrar atributos principales y secundarios
                List<String> mainAttributes = factory.getMainAttributes(type);
                System.out.println("Atributos principales que puedes aumentar: " + mainAttributes);
                System.out.println("Atributos secundarios que puedes reducir son el resto que no son los atributos principales.");

                String mainAttr;
                while (true) {
                    System.out.print("¿Qué atributo principal deseas aumentar? ");
                    mainAttr = scanner.nextLine().toLowerCase();

                    if (mainAttributes.contains(mainAttr)) {
                        // Validación de puntos a restar
                        int pointsToRemove;
                        while (true) {
                            System.out.print("¿Cuántos puntos deseas restar de otros atributos? (2 puntos por cada 1 punto a aumentar): ");
                            // Comprobar si el input es un entero
                            if (scanner.hasNextInt()) {
                                pointsToRemove = scanner.nextInt();
                                scanner.nextLine(); // Limpiar el buffer

                                // Comprobar que sea múltiplo de 2
                                if (pointsToRemove % 2 == 0 && pointsToRemove > 0) {
                                    break; // Salir del bucle si es válido
                                } else {
                                    System.out.println("Por favor, ingresa un número positivo y múltiplo de 2.");
                                }
                            } else {
                                System.out.println("Entrada no válida. Por favor, ingresa un número entero.");
                                scanner.nextLine(); // Limpiar el buffer de entrada
                            }
                        }

                        // Lógica de ajuste
                        int adjustment = pointsToRemove / 2; // Cada 2 puntos restados aumenta 1 punto en el atributo principal

                        // Verificar y ajustar atributos según la clase
                        boolean validAdjustment = true;

                        // Ajustar el atributo principal seleccionado
                        if (mainAttr.equals("fuerza")) {
                            if (strength + adjustment <= 18) {
                                strength += adjustment; // Aumentar el atributo principal
                            } else {
                                validAdjustment = false;
                                System.out.println("Ajuste no válido para Fuerza.");
                            }
                        } else if (mainAttr.equals("inteligencia")) {
                            if (intelligence + adjustment <= 18) {
                                intelligence += adjustment; // Aumentar el atributo principal
                            } else {
                                validAdjustment = false;
                                System.out.println("Ajuste no válido para Inteligencia.");
                            }
                        } else if (mainAttr.equals("sabiduría")) {
                            if (wisdom + adjustment <= 18) {
                                wisdom += adjustment; // Aumentar el atributo principal
                            } else {
                                validAdjustment = false;
                                System.out.println("Ajuste no válido para Sabiduría.");
                            }
                        }

                        // Si el ajuste fue válido, restar puntos de un atributo secundario
                        if (validAdjustment) {
                            boolean attributeReduced = false;
                            List<String> secondaryAttributes = new ArrayList<>(Arrays.asList("inteligencia", "sabiduría", "destreza", "constitución", "carisma"));
                            secondaryAttributes.remove(mainAttr); // Asegurarse de que el atributo principal no esté en la lista

                            while (pointsToRemove > 0 && !attributeReduced) {
                                System.out.print("¿De qué atributo secundario deseas restar? " + secondaryAttributes + ": ");
                                String attributeToReduce = scanner.nextLine().toLowerCase();

                                if (secondaryAttributes.contains(attributeToReduce)) {
                                    switch (attributeToReduce) {
                                        case "inteligencia":
                                            if (intelligence - pointsToRemove >= 9) {
                                                intelligence -= pointsToRemove; // Restar puntos de Inteligencia
                                                pointsToRemove = 0; // Se han usado todos los puntos a restar
                                                attributeReduced = true; // Atributo reducido correctamente
                                            } else {
                                                System.out.println("No puedes reducir Inteligencia por debajo de 9.");
                                            }
                                            break;
                                        case "sabiduría":
                                            if (wisdom - pointsToRemove >= 9) {
                                                wisdom -= pointsToRemove; // Restar puntos de Sabiduría
                                                pointsToRemove = 0; // Se han usado todos los puntos a restar
                                                attributeReduced = true; // Atributo reducido correctamente
                                            } else {
                                                System.out.println("No puedes reducir Sabiduría por debajo de 9.");
                                            }
                                            break;
                                        case "destreza":
                                            if (dexterity - pointsToRemove >= 9) {
                                                dexterity -= pointsToRemove; // Restar puntos de Destreza
                                                pointsToRemove = 0; // Se han usado todos los puntos a restar
                                                attributeReduced = true; // Atributo reducido correctamente
                                            } else {
                                                System.out.println("No puedes reducir Destreza por debajo de 9.");
                                            }
                                            break;
                                        case "constitución":
                                            if (constitution - pointsToRemove >= 9) {
                                                constitution -= pointsToRemove; // Restar puntos de Constitución
                                                pointsToRemove = 0; // Se han usado todos los puntos a restar
                                                attributeReduced = true; // Atributo reducido correctamente
                                            } else {
                                                System.out.println("No puedes reducir Constitución por debajo de 9.");
                                            }
                                            break;
                                        case "carisma":
                                            if (charisma - pointsToRemove >= 9) {
                                                charisma -= pointsToRemove; // Restar puntos de Carisma
                                                pointsToRemove = 0; // Se han usado todos los puntos a restar
                                                attributeReduced = true; // Atributo reducido correctamente
                                            } else {
                                                System.out.println("No puedes reducir Carisma por debajo de 9.");
                                            }
                                            break;
                                    }
                                } else {
                                    System.out.println("Atributo secundario no válido. Por favor, elige uno de los atributos secundarios: " + secondaryAttributes);
                                }
                            }
                        }

                        // Mostrar atributos ajustados
                        System.out.println("Atributos ajustados:");
                        System.out.println("Fuerza: " + strength);
                        System.out.println("Inteligencia: " + intelligence);
                        System.out.println("Sabiduría: " + wisdom);
                        System.out.println("Destreza: " + dexterity);
                        System.out.println("Constitución: " + constitution);
                        System.out.println("Carisma: " + charisma);

                        break; // Salir del bucle de ajuste
                    } else {
                        // Si el atributo no es válido, mostrar un mensaje y volver a preguntar
                        System.out.println("Atributo principal no válido. Por favor, elige uno de los atributos principales: " + mainAttributes);
                    }
                }
            } else if (response.equals("no")) {
                adjusting = false; // Salir del bucle
            } else {
                System.out.println("Respuesta no válida. Por favor, responde 'sí' o 'no'.");
            }
        }

        // Crear el personaje con los atributos finales
        Character character = factory.createCharacterFromType(type, name, strength, intelligence, wisdom, dexterity, constitution, charisma, alignment);
        if (character != null) {
            character.displayInfo();
        } else {
            System.out.println("No se pudo crear el personaje o la clase elegida no es válida.");
        }
    }

}


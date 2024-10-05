import java.util.Random;

public class Dice {
    private static Random random = new Random();

    public static int roll(int sides) {
        return random.nextInt(sides) + 1;
    }

    public static int roll3d6() {
        return roll(6) + roll(6) + roll(6);
    }
}

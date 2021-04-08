package library.consoleUI.text_formatter;

public final class Beautify {
    public static void beautifyPrint(String message) {
        if (message.length() < 2) {
            System.out.println("-".repeat(62));
        } else {

            int size = message.length();

            double minusesFromSides = (double) (60 - size) / 2;

            int minusesFromTheLeft = (int) Math.floor(minusesFromSides);
            int minusesFromTheRight = (int) Math.ceil(minusesFromSides);
            String beautifiedText = "-".repeat(minusesFromTheLeft) + " " + message.toUpperCase() + " "
                    + "-".repeat(minusesFromTheRight);
            System.out.println(beautifiedText);
        }
    }
}

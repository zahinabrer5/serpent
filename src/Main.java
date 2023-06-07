import javax.swing.*;
import java.io.*;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) throws Exception {
        // enable OpenGL for Linux & Windows
        System.setProperty("sun.java2d.opengl", "true");

        UIManager.setLookAndFeel("javax.swing.plaf.nimbus.NimbusLookAndFeel");

        printWelcomeMessage();

        Scanner sc = new Scanner(System.in);
        System.out.print("\nPlay multiplayer mode (Y or y for yes, anything else for no): ");
        boolean multiplayer = sc.nextLine().equalsIgnoreCase("y");

        System.out.print("\nEnter snake 1 name: ");
        String snake1Name = sc.nextLine();

        String snake2Name = "";
        if (multiplayer) {
            System.out.print("\nEnter snake 2 name: ");
            snake2Name = sc.nextLine();
        }

        // data array to pass through Window and into Game class
        // I thought it'd look cleaner than having 3 different parameters
        // for both the Window and Game constructors
        Object[] data = { multiplayer, snake1Name, snake2Name };

        int waitTime = 3;
        System.out.println("\nA GUI should appear in about "+waitTime+" seconds... good luck snake(s)!");
        System.out.println("Make sure there's an 'Output' window open if using repl.it\n");
        Thread.sleep(waitTime*1000);
        new Window(data);
    }

    // https://stackoverflow.com/questions/5868369/how-can-i-read-a-large-text-file-line-by-line-using-java
    private static void printWelcomeMessage() {
        try (BufferedReader br = new BufferedReader(new InputStreamReader(
                Main.class.getResourceAsStream("welcome.txt")))) {
            for (String line; (line = br.readLine()) != null; ) {
                System.out.println(line);
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}

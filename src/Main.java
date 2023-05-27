/*
notes:
- do something with Strings
    - perhaps load a text file representing a map
        - load the file as an array of strings
    - store welcome message in a text file and print it out before opening GUI
*/

import javax.swing.*;

public class Main {
    public static void main(String[] args) throws Exception {
        // enable OpenGL for Linux & Windows
        System.setProperty("sun.java2d.opengl", "true");

        UIManager.setLookAndFeel("javax.swing.plaf.nimbus.NimbusLookAndFeel");

        new Window();
    }
}

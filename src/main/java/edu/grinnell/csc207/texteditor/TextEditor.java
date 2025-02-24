package edu.grinnell.csc207.texteditor;

import com.googlecode.lanterna.TextCharacter;
import com.googlecode.lanterna.input.KeyStroke;
import com.googlecode.lanterna.input.KeyType;
import com.googlecode.lanterna.screen.Screen;
import com.googlecode.lanterna.terminal.DefaultTerminalFactory;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

/**
 * The driver for the TextEditor Application.
 */
public class TextEditor {

    /**
     * The main entry point for the TextEditor application.
     * @param args command-line arguments.
     */
    public static void main(String[] args) {
        if (args.length != 1) {
            System.err.println("Usage: java TextEditor <filename>");
            System.exit(1);
        }

        // TODO: fill me in with a text editor TUI!
        String path = args[0];
        System.out.format("Loading %s...\n", path);
        
       // Read the initial file contents, if it exists.
        String initialContent = "";
        try {
            if (Files.exists(Paths.get(path))) {
                initialContent = Files.readString(Paths.get(path));
            }
        } catch (IOException e) {
            System.err.println("Error reading file: " + e.getMessage());
        }

        // Use a StringBuilder as our simple text buffer and track the cursor.
        StringBuilder textBuffer = new StringBuilder(initialContent);
        // Use an integer array to allow mutation within inner scopes.
        int[] cursorPosition = new int[] {textBuffer.length()};

        // Set up Lanterna.
        DefaultTerminalFactory terminalFactory = new DefaultTerminalFactory();
        Screen screen = null;
        try {
            screen = terminalFactory.createScreen();
            screen.startScreen();
            // Hide the hardware cursor
            screen.setCursorPosition(null);
        } catch (IOException e) {
            e.printStackTrace();
            System.exit(1);
        }

        boolean running = true;
        while (running) {
            try {
                // Clear the screen's back-buffer.
                screen.clear();
                // Draw the text buffer on row 0.
                String text = textBuffer.toString();
                for (int col = 0; col < text.length(); col++) {
                    // setCharacter expects this format (column, row, TextCharacter)
                    screen.setCharacter(col, 0, new TextCharacter(text.charAt(col)));
                }
                int cursor = cursorPosition[0];
                screen.setCharacter(cursor, 0, new TextCharacter('_'));
                // Refresh the screen to update the display.
                screen.refresh();

                // Read in a keystroke.
                KeyStroke keyStroke = screen.readInput();
                if (keyStroke == null) {
                    continue;
                }
                KeyType keyType = keyStroke.getKeyType();

                // Process the keystroke.
                switch (keyType) {
                    case Character:
                        char ch = keyStroke.getCharacter();
                        textBuffer.insert(cursorPosition[0], ch);
                        cursorPosition[0]++;
                        break;
                    case Backspace:
                        if (cursorPosition[0] > 0) {
                            textBuffer.deleteCharAt(cursorPosition[0] - 1);
                            cursorPosition[0]--;
                        }
                        break;
                    case ArrowLeft:
                        if (cursorPosition[0] > 0) {
                            cursorPosition[0]--;
                        }
                        break;
                    case ArrowRight:
                        if (cursorPosition[0] < textBuffer.length()) {
                            cursorPosition[0]++;
                        }
                        break;
                    case Escape:
                        running = false;
                        break;
                    default:
                        break;
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        // Upon exit, write the buffer back into the file.
        try {
            Files.writeString(Paths.get(path), textBuffer.toString());
        } catch (IOException e) {
            System.err.println("Error writing file: " + e.getMessage());
        }

        // Clean up the screen.
        try {
            screen.stopScreen();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

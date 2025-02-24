package edu.grinnell.csc207.texteditor;

import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 * Unit tests for GapBuffer
 */
public class GapBufferTests {

    private GapBuffer buffer;

    
@BeforeEach
    public void setUp() {
        buffer = new GapBuffer();
    }

    @Test
    public void testInitialBufferIsEmpty() {
        assertEquals(0, buffer.getSize(), "Buffer should be empty initially");
        assertEquals(0, buffer.getCursorPosition(), "Cursor should be at position 0 in an empty buffer");
        assertEquals("", buffer.toString(), "toString() should return an empty string initially");
    }

    @Test
    public void testInsert() {
        buffer.insert('a');
        assertEquals("a", buffer.toString(), "Buffer should contain 'a' after insertion");
        assertEquals(1, buffer.getCursorPosition(), "Cursor should move to 1 after insertion");

        buffer.insert('b');
        assertEquals("ab", buffer.toString(), "Buffer should contain 'ab' after second insertion");
        assertEquals(2, buffer.getCursorPosition(), "Cursor should move to 2 after second insertion");
    }

    @Test
    public void testDelete() {
        // Deleting on an empty buffer should do nothing.
        buffer.delete();
        assertEquals("", buffer.toString(), "Deleting on an empty buffer should leave it unchanged");

        // Insert some characters: "xyz"
        buffer.insert('x');
        buffer.insert('y');
        buffer.insert('z');  // Buffer now "xyz" with cursor at 3.

        // Move cursor left once (cursor becomes 2) and then delete.
        buffer.moveLeft();   // Cursor now at 2.
        buffer.delete();     // Should delete the character immediately left of the cursor.
                             // Finally, for "xyz", this removes the 'y', resulting in "xz".
        assertEquals("xz", buffer.toString(), "Buffer should be 'xz' after deletion");
        assertEquals(1, buffer.getCursorPosition(),
                     "Cursor should move one position left after deletion");
    }

    @Test
    public void testMoveLeftAndRight() {
        // Build the buffer "abc" with cursor at end.
        buffer.insert('a');
        buffer.insert('b');
        buffer.insert('c'); // Buffer "abc", cursor at 3.

        // Move left repeatedly.
        buffer.moveLeft();
        assertEquals(2, buffer.getCursorPosition(), "Cursor should be at 2 after one moveLeft()");
        buffer.moveLeft();
        assertEquals(1, buffer.getCursorPosition(), "Cursor should be at 1 after second moveLeft()");
        buffer.moveLeft();
        assertEquals(0, buffer.getCursorPosition(), "Cursor should not move left past 0");
        // Extra moveLeft() should leave cursor at 0.
        buffer.moveLeft();
        assertEquals(0, buffer.getCursorPosition(), "Cursor remains at 0 after extra moveLeft()");

        // Move right till the end.
        buffer.moveRight();
        assertEquals(1, buffer.getCursorPosition(), "Cursor should be at 1 after moveRight()");
        buffer.moveRight();
        buffer.moveRight();
        assertEquals(3, buffer.getCursorPosition(), "Cursor should be at end of buffer");
        // Extra moveRight() should leave the cursor at the end.
        buffer.moveRight();
        assertEquals(3, buffer.getCursorPosition(), "Cursor remains at end after extra moveRight()");
    }
}

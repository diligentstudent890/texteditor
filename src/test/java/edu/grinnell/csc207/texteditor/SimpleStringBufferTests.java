package edu.grinnell.csc207.texteditor;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import net.jqwik.api.ForAll;
import net.jqwik.api.Property;

/**
 * Unit tests for {@code SimpleStringBuffer}.
 */
public class SimpleStringBufferTests {

    private SimpleStringBuffer buffer;

    @BeforeEach
    public void setUp() {
        buffer = new SimpleStringBuffer();
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
        
        // Move the cursor left once (cursor becomes 2) and then delete.
        buffer.moveLeft();   // Cursor at 2.
        buffer.delete();     // Should delete the character before the cursor (i.e. 'y').
        
        assertEquals("xz", buffer.toString(), "Buffer should be 'xz' after deletion");
        assertEquals(1, buffer.getCursorPosition(), "Cursor should move one position left after deletion");
    }

    @Test
    public void testMoveLeftAndRight() {
        // Build the buffer "abc" with cursor at end.
        buffer.insert('a');
        buffer.insert('b');
        buffer.insert('c'); // Buffer "abc", cursor at 3.
        
        buffer.moveLeft();
        assertEquals(2, buffer.getCursorPosition(), "Cursor should be at 2 after one moveLeft()");
        
        buffer.moveLeft();
        assertEquals(1, buffer.getCursorPosition(), "Cursor should be at 1 after second moveLeft()");
        
        buffer.moveLeft();
        assertEquals(0, buffer.getCursorPosition(), "Cursor should not move left past 0");
        
        // Extra moveLeft() should leave cursor at 0.
        buffer.moveLeft();
        assertEquals(0, buffer.getCursorPosition(), "Cursor remains at 0 after extra moveLeft()");
        
        // Move right until the end.
        buffer.moveRight();
        assertEquals(1, buffer.getCursorPosition(), "Cursor should be at 1 after moveRight()");
        buffer.moveRight();
        buffer.moveRight();
        assertEquals(3, buffer.getCursorPosition(), "Cursor should be at end of buffer");
        
        // Extra moveRight() should leave the cursor at the end.
        buffer.moveRight();
        assertEquals(3, buffer.getCursorPosition(), "Cursor remains at end after extra moveRight()");
    }

    @Test
    public void testGetCharAndGetSize() {
        buffer.insert('h');
        buffer.insert('e');
        buffer.insert('l');
        buffer.insert('l');
        buffer.insert('o');  // Buffer now "hello"
        
        assertEquals(5, buffer.getSize(), "Buffer size should be 5");
        assertEquals('h', buffer.getChar(0));
        assertEquals('e', buffer.getChar(1));
        assertEquals('l', buffer.getChar(2));
        assertEquals('l', buffer.getChar(3));
        assertEquals('o', buffer.getChar(4));
        
        // Test that getChar throws for invalid indices.
        assertThrows(IndexOutOfBoundsException.class, () -> buffer.getChar(5),
                     "getChar() should throw IndexOutOfBoundsException for index 5");
        assertThrows(IndexOutOfBoundsException.class, () -> buffer.getChar(-1),
                     "getChar() should throw IndexOutOfBoundsException for a negative index");
    }

    /**
     * A property-based test using Jqwik.
     * This property asserts that if we start with an empty buffer and insert every character
     * from an arbitrary string, then the buffer's content, size, and cursor position should match
     * that string.
     *
     * @param randomString an arbitrary string provided by Jqwik
     */
    @Property
    public void propertyInsertCreatesCorrectBuffer(@ForAll String randomString) {
        SimpleStringBuffer buf = new SimpleStringBuffer();
        for (char ch : randomString.toCharArray()) {
            buf.insert(ch);
        }
        assertEquals(randomString, buf.toString(), "Buffer content should match the inserted string");
        assertEquals(randomString.length(), buf.getSize(), "Buffer size should equal the inserted string's length");
        assertEquals(randomString.length(), buf.getCursorPosition(), "Cursor should be at the end of the buffer");
    }
}
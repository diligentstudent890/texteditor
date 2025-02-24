package edu.grinnell.csc207.texteditor;

/**
 * A gap buffer-based implementation of a text buffer.
 */
public class GapBuffer {
    private char[] buffer;
    private int gapStart;
    private int gapEnd;
    private static final int INITIAL_CAPACITY = 10;
/**
 * Constructor
 */    
    public GapBuffer() {
        buffer = new char[INITIAL_CAPACITY];
        gapStart = 0;
        gapEnd = INITIAL_CAPACITY;
    }

    /**
     * Inserts the specified character at the current cursor position.
     * If the gap is full, the underlying array is expanded before the insertion.
     * After insertion, the cursor (i.e. gapStart) advances one position.
     *
     * @param ch the character to insert
     */
    public void insert(char ch) {
        if (gapStart == gapEnd) {
            expandGap();
        }
        buffer[gapStart] = ch;
        gapStart++;
    }

   /**
     * Deletes the character immediately to the left of the cursor (simulating a backspace).
     * If the cursor is at the beginning of the buffer, no deletion occurs.
     * After the deletion, the cursor moves one position to the left.
     */
    public void delete() {
        if (gapStart > 0) {
            gapStart--;
        }
    }

   /**
     * Returns the current cursor position (i.e. the number of characters in the left text).
     *
     * @return the current cursor position
     */
    public int getCursorPosition() {
        return gapStart;
    }

    /**
     * Moves the cursor one position to the left.
     * If the cursor is not at the beginning, the character immediately left of the gap is moved
     * to the right end of the gap.
     * @return void
     */
    public void moveLeft() {
        if (gapStart > 0) {
            // Move the character at gapStart - 1 to the last position of the gap.
            buffer[gapEnd - 1] = buffer[gapStart - 1];
            gapStart--;
            gapEnd--;
        }
    }


    /**
     * Moves the cursor one position to the right.
     * If the cursor is not at the end, the character at the beginning of the right section is moved
     * to the left end of the gap.
     */
    public void moveRight() {
        if (gapEnd < buffer.length) {
            buffer[gapStart] = buffer[gapEnd];
            gapStart++;
            gapEnd++;
        }
    }

    /**
     * Returns the number of characters stored in the buffer (i.e. excluding the gap).
     *
     * @return the size of the text buffer
     */
    public int getSize() {
        return gapStart + (buffer.length - gapEnd);
    }

   /**
     * Returns the character at the specified index of the logical text.
     * The index is zero-based, where indices less than gapStart refer to the left text and
     * indices greater than or equal to gapStart refer to the right text.
     *
     * @param i the index of the character to return
     * @return the character at index {@code i}
     * @throws IndexOutOfBoundsException if {@code i} is not a valid index in the text
     */
    public char getChar(int i) {
        if (i < 0 || i >= getSize()) {
            throw new IndexOutOfBoundsException("Index " + i + " is out of bounds. Size: " + getSize());
        }
        if (i < gapStart) {
            return buffer[i];
        } else {
            // Adjust index to account for the gap.
            return buffer[i + (gapEnd - gapStart)];
        }
    }

    /**
     * Returns the contents of the text buffer as a {@code String}.
     *
     * @return the text contained in the buffer
     */
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder(getSize());
        for (int i = 0; i < gapStart; i++) {
            sb.append(buffer[i]);
        }
        for (int i = gapEnd; i < buffer.length; i++) {
            sb.append(buffer[i]);
        }
        return sb.toString();
    }
    /**
     * Expands the gap when it is empty by allocating a new array.
     * The new gap occupies the middle of the array.
     * @return void
     */
    private void expandGap() {
        int oldCapacity = buffer.length;
        int newCapacity = oldCapacity * 2;
        char[] newBuffer = new char[newCapacity];

        // Copy the left text.
        for (int i = 0; i < gapStart; i++) {
            newBuffer[i] = buffer[i];
        }
        // Compute the size of the right text.
        int rightSize = oldCapacity - gapEnd;
        // New gap will be placed.
        int newGapEnd = newCapacity - rightSize;
        // Copy the right text.
        for (int i = gapEnd; i < oldCapacity; i++) {
            newBuffer[newGapEnd + (i - gapEnd)] = buffer[i];
        }

        buffer = newBuffer;
        gapEnd = newGapEnd;
    }
}

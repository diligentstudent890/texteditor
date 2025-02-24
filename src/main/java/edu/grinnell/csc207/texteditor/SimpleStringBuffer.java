package edu.grinnell.csc207.texteditor;

/**
 * A very naive implementation of a text buffer using a {@code String} as the backing store.
 * The cursor is represented as an index into the String, ranging from 0 to the length
 * of the String. Insertion occurs at the cursor position and deletion removes the character
 * immediately before the cursor (simulating a backspace), then moves the cursor one position left.
 */
public class SimpleStringBuffer {
    /** The backing text. */
    private String text;
    
    /**
     * The cursor position (an index into {@code text}). It ranges from 0 to {@code text.length()}.
     * When the cursor equals {@code text.length()}, insertion appends to the end.
     */
    private int cursor;
    
    /**
     * It constructs a new, empty {@code SimpleStringBuffer}.
     */
    public SimpleStringBuffer() {
        this.text = "";
        this.cursor = 0;
    }
    
    /**
     * Inserts the specified character into the buffer at the current cursor position.
     * After insertion, the cursor advances one position forward.
     *
     * @param ch the character to insert
     */
    public void insert(char ch) {
        text = text.substring(0, cursor) + ch + text.substring(cursor);
        cursor++;
    }
    
    /**
     * Deletes the character immediately before the current cursor position.
     * If the cursor is at the beginning of the buffer (i.e. 0), nothing is deleted.
     * After a successful deletion, the cursor moves one position backwards.
     */
    public void delete() {
        if (cursor > 0 && text.length() > 0) {
            text = text.substring(0, cursor - 1) + text.substring(cursor);
            cursor--;
        }
    }
    
    /**
     * Returns the current position of the cursor.
     *
     * @return the cursor position
     */
    public int getCursorPosition() {
        return cursor;
    }
    
    /**
     * Moves the cursor one position to the left. If the cursor is already at the beginning,
     * it remains unchanged.
     */
    public void moveLeft() {
        if (cursor > 0) {
            cursor--;
        }
    }
    
    /**
     * Moves the cursor one position to the right. 
     * If the cursor is already at the end of the buffer,
     * it remains unchanged.
     */
    public void moveRight() {
        if (cursor < text.length()) {
            cursor++;
        }
    }
    
    /**
     * Returns the number of characters in the buffer.
     *
     * @return the size of the buffer
     */
    public int getSize() {
        return text.length();
    }
    
    /**
     * Returns the character at the specified index (zero-indexed).
     *
     * @param i the index of the character to return
     * @return the character at index {@code i}
     * @throws IndexOutOfBoundsException if {@code i} is negative or not less than {@code getSize()}
     */
    public char getChar(int i) {
        if (i < 0 || i >= text.length()) {
            throw new IndexOutOfBoundsException("Index " + i + " is "
                    + "out of bounds for buffer of size " + text.length());
        }
        return text.charAt(i);
    }
    
    /**
     * Returns the contents of the buffer as a {@code String}.
     *
     * @return the string representation of the buffer's contents
     */
    @Override
    public String toString() {
        return text;
    }
}
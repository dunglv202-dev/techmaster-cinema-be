package dev.dunglv202.techmaster.util;

import dev.dunglv202.techmaster.model.SeatPosition;

public class SeatConverter {
    public static SeatPosition parse(String seat) {
        // Find where the row ends and the column begins
        int i = 0;
        while (i < seat.length() && Character.isLetter(seat.charAt(i))) {
            i++;
        }

        // Split into row (letters) and column (numbers)
        String rowPart = seat.substring(0, i); // Get the row part (e.g., "AA", "B")
        String columnPart = seat.substring(i); // Get the column part (e.g., "1", "11")

        // Convert row to zero-based index
        int rowIndex = rowToIndex(rowPart);

        // Convert column part to a zero-based index
        int columnIndex = Integer.parseInt(columnPart) - 1;

        return new SeatPosition(rowIndex, columnIndex);
    }

    public static String format(int row, int col) {
        String rowPart = indexToRow(row);
        int columnPart = col + 1;
        return rowPart + columnPart;
    }

    private static int rowToIndex(String row) {
        int rowIndex = 0;
        for (int i = 0; i < row.length(); i++) {
            rowIndex *= 26; // Shift for each letter (26 letters in the alphabet)
            rowIndex += row.charAt(i) - 'A' + 1; // Convert 'A' to 1, 'B' to 2, ..., 'Z' to 26
        }
        return rowIndex - 1; // Return zero-based index
    }

    private static String indexToRow(int rowIndex) {
        StringBuilder row = new StringBuilder();
        while (rowIndex >= 0) {
            row.insert(0, (char) ('A' + (rowIndex % 26))); // Get the character for current digit
            rowIndex = (rowIndex / 26) - 1; // Shift index, adjust by 1 because 'A' is treated as 1, not 0
        }
        return row.toString();
    }

}

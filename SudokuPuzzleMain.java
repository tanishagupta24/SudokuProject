package com.sudoku;
import java.util.*;

public class SudokuPuzzleMain {
    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);
        boolean[] allowedValues = new boolean[9];
        SudokuPuzzle puzzle = new SudokuPuzzle();

        while (!puzzle.checkPuzzle() || !puzzle.isFull()) {

            System.out.println("Here is the puzzle so far: ");
            System.out.println(puzzle.toString());
            System.out.println("Enter a open cell you would like to fill in marked as 0[O]");
            System.out.println("Enter the Row Number: ");
            int row = in.nextInt();
            System.out.println("Enter the Column Number: ");
            int col = in.nextInt();

            if (puzzle.start[row - 1][col - 1] == true) {
                System.out.println("Cell (" + row + ", " + col + ") is Fixed and can't be changed");
                continue;
            } else {
                System.out.println("Enter the Value for Cell (" + row + ", " + col + "): ");
                int value = in.nextInt();
                if(value <= 9 && value >= 1) {
                    allowedValues = puzzle.getAllowedValues(row, col);
                    //Turn on the below print to get a hint at allowed values
                    //System.out.println(Arrays.toString(allowedValues));
                    if (allowedValues[value-1] == true ) {
                        System.out.print("That is not a valid entry. It violates the laws of Sudoku. Please choose another number for the cell.");
                    } else {
                        puzzle.addGuess(row, col, value);
                    }
                    System.out.println("\n");
                } else {
                    System.out.println("Seriously... put in proper values!");
                }
            }
        }

        System.out.println("Congratulations! You have succeeded in solving the puzzle!");

        System.out.println("Would you like to play again? [y/n]");
        String answer = in.next();
        if(answer.equalsIgnoreCase("y")) {
            puzzle.reset();
        }
    }
}

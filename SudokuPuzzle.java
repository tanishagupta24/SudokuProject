package com.sudoku;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class SudokuPuzzle {

    //start board
    int[][] board = new int[9][9];
    //board that has the boolean values
    boolean[][] start = new boolean[9][9];

    //constructor in name
    public SudokuPuzzle() {
        initializeAndSetUp();
    }

    //real constructor
    //adds all of the values from the puzzle file into the board array, also fills the start array as well
    private void initializeAndSetUp(){
        File file = new File("./src/com/sudoku/sudoku1.txt");
        int tempInt = 0;

        try {
            Scanner scanner = new Scanner(file);
            //the for loops loop through the entire board
            for(int i = 0; i < board.length; i++) {
                for(int j = 0; j < board[i].length; j++) {
                    tempInt = scanner.nextInt();
                    if (tempInt != 0) {
                        addInitial(i, j, tempInt);
                    }
                }
            }
        }
        catch (FileNotFoundException e){
            e.printStackTrace();
        }
    }

    //used in set up and initialize function to set up the board
    private void addInitial(int row, int col, int value) {
        start[row][col] = true;
        board[row][col] = value;
    }

    //Called from Main
    public void addGuess(int row, int col, int value) {
        //used on user input side of things
        if(row >= 1 && row <= 9 && col >= 1 && col <= 9) {
            if(start[row-1][col-1] == false) {
                if(value >= 1 && value <= 9) {
                    board[row-1][col-1] = value;
                } else {
                    System.out.println("Seriously... input proper values, between 1 through 9!");
                }
            }
        }
    }

    //Returns a array of Boolean values, Index value corresponding to False are the allowed values
    public boolean[] getAllowedValues(int row, int col) {
        boolean[] allowedValues = new boolean[9];

        //Checking for allowed values in the row
        for(int i = 0; i < board.length; i++) {
            if (board[row-1][i] != 0)  {
                allowedValues[board[row-1][i]-1] = true;
            }
        }

        //Checking for allowed values in the column
        for(int i = 0; i < board.length; i++) {
            if (board[i][col-1] != 0)  {
                allowedValues[board[i][col-1]-1] = true;
            }
        }

        //Checking for allowed values in the box
        for(int i = ((row-1)/3)*3; i < (((row-1)/3)*3)+3; i++) {
            for (int j = ((col-1)/3)*3; j < (((col-1)/3)*3)+3; j++) {
                if (board[i][j] != 0) {
                    allowedValues[board[i][j]-1] = true;
                }
            }
        }
        return allowedValues;
    }

    public int getValueIn(int row, int col) {
        return board[row][col];
    }

    // checks if the board is full
    public boolean isFull(){
        for(int i = 0; i < board.length; i++) {
            for(int j = 0; j < board[i].length; j++) {
                if(board[i][j] == 0) {
                    return false;
                }
            }
        }
        return true;
    }

    //checks if a number is contained in an array, is used in checkPuzzle function
    private boolean containsNumber (int number, int[] values) {
        for(int i = 0; i < values.length; i++) {
            if(values[i] == number) {
                return true;
            }
        }
        return false;
    }

    //once the puzzle is complete, checks if puzzle is within the laws
    public boolean checkPuzzle() {
        int[] values = new int[9];

        //Go over each cell in the board & evaluate the uniqueness for corresponding
        for (int a = 0; a < board.length; a++) {
            for (int b = 0; b < board.length; b++) {

                values = new int[9];
                //Check for all unique values in the row
                for (int i = 0; i < board.length; i++) {

                    //Only if the number is not already present in the row will we add it to the values list,
                    //if it is present then they have violated the laws of Sukodu
                    if (!containsNumber(board[a][i], values)) {
                        values[i] = board[a][i];
                    } else {
                        return false;
                    }
                }

                values = new int[9];
                //Check for all unique values in the column
                for (int i = 0; i < board.length; i++) {
                    //Only if the number is not already present in the column will we add it to the values list,
                    //if it is present then they have violated the laws of Sukodu
                    if (!containsNumber(board[i][b], values)) {
                        values[i] = board[i][b];
                    } else {
                        return false;
                    }
                }

                values = new int[9];
                //Check for all unique values in the box
                int k = 0;
                for (int i = ((a) / 3) * 3; i < (((a) / 3) * 3) + 3; i++) {
                    for (int j = ((b) / 3) * 3; j < (((b) / 3) * 3) + 3; j++) {
                        //Only if the number is not already present in the box will we add it to the values list,
                        //if it is present then they have violated the laws of Sukodu
                        if (!containsNumber(board[i][j], values)) {
                            values[k++] = board[i][j];
                        } else {
                            return false;
                        }
                    }
                }
            }
        }
        return true;
    }

    //resets the board for the player if they want to play again
    public void reset() {
        initializeAndSetUp();
    }

    //used to print out the puzzle
    public String toString() {
        String tempString = "";
        for(int i = 0; i < board.length; i++) {
            for(int j = 0; j < board[i].length; j++) {
                tempString += board[i][j];
                if (start[i][j] == true) {
                    //for open spaces
                    tempString += "[F]";
                } else {
                    //for fixed spaces
                    tempString += "[O]";
                }
                tempString += " ";
                if(j != 0 && (j+1) % 3 == 0) {
                    tempString += "  ";
                }
            }
            tempString += "\n";
            if (i != 0 && (i+1) % 3 == 0) {
                tempString += "\n";
            }
        }
        return tempString;
    }
}

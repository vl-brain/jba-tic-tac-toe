package com.company;

import com.company.tictactoe.TicTacToe;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        TicTacToe ticTacToe = new TicTacToe();
        do {
            ticTacToe.input(scanner.nextLine());
        } while (!ticTacToe.isComplete());
    }
}

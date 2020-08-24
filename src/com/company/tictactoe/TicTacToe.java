package com.company.tictactoe;

import java.util.Arrays;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import static com.company.tictactoe.FieldState.*;

public class TicTacToe
{
    private FieldState[][] cells;
    private GameState gameState;
    private FieldState nextFieldState = X;

    public TicTacToe(String input) {
        this(parseCells(input));
    }

    public TicTacToe() {
        this(new FieldState[][]{
            {EMPTY, EMPTY, EMPTY},
            {EMPTY, EMPTY, EMPTY},
            {EMPTY, EMPTY, EMPTY}
        });
    }

    public TicTacToe(FieldState[][] cells) {
        this.cells = cells;
        showNewLine(toString());
        setGameState(calculateState());
    }

    private static FieldState[][] parseCells(String input) {
        return IntStream.iterate(0, i -> i < 9, i -> i + 3)
            .mapToObj(startIndex -> input.substring(startIndex, startIndex + 3))
            .map(line -> line.chars()
                .mapToObj(i -> String.valueOf((char) i))
                .map(FieldState::getByView)
                .toArray(FieldState[]::new))
            .toArray(FieldState[][]::new);
    }

    private Move parseValidMove(String input) {
        if (input.matches("\\d \\d")) {
            String[] parts = input.split(" ");
            int column = Integer.parseInt(parts[0]);
            int line = Integer.parseInt(parts[1]);
            FieldState cell = getCell(line, column);
            if (cell == null) {
                showNewLine("Coordinates should be from 1 to 3!");
            } else if (cell != EMPTY) {
                showNewLine("This cell is occupied! Choose another one!");
            } else {
                return new Move(line, column);
            }
        } else {
            showNewLine("You should enter numbers!");
        }
        return null;
    }

    private boolean hasCell(int line, int column) {
        return 0 < line && line < 4 && 0 < column && column < 4;
    }

    private FieldState getCell(int line, int column) {
        return hasCell(line, column) ? cells[cells.length - line][column - 1] : null;
    }

    private void setCell(int line, int column) {
        if (hasCell(line, column)) {
            cells[cells.length - line][column - 1] = nextFieldState;
            nextFieldState = nextFieldState == X ? O : X;
        }
    }

    private GameState calculateState() {
        long xLinesCount = 0;
        long oLinesCount = 0;
        long xCount = 0;
        long oCount = 0;
        long emptyCount = 0;
        FieldState[] firstDiagonal = new FieldState[cells.length];
        FieldState[] secondDiagonal = new FieldState[cells.length];
        for (int i = 0; i < cells.length; i++) {
            FieldState[] horizontal = new FieldState[cells.length];
            FieldState[] vertical = new FieldState[cells.length];
            for (int j = 0; j < cells[i].length; j++) {
                horizontal[j] = cells[i][j];
                vertical[j] = cells[j][i];
                if (i == j) {
                    firstDiagonal[j] = cells[i][j];
                }
                if (i + j == cells.length - 1) {
                    secondDiagonal[j] = cells[i][j];
                }
                switch (cells[i][j]) {
                    case X:
                        xCount++;
                        break;
                    case O:
                        oCount++;
                        break;
                    case EMPTY:
                        emptyCount++;
                }
            }
            xLinesCount += getAllMatchValueLineCount(X, horizontal, vertical);
            oLinesCount += getAllMatchValueLineCount(O, horizontal, vertical);
        }
        xLinesCount += getAllMatchValueLineCount(X, firstDiagonal, secondDiagonal);
        oLinesCount += getAllMatchValueLineCount(O, firstDiagonal, secondDiagonal);
        if (Math.abs(xCount - oCount) > 1) {
            return GameState.INVALID;
        }
        if (xLinesCount > 0) {
            return oLinesCount > 0 ? GameState.INVALID : GameState.X_WIN;
        } else if (oLinesCount > 0) {
            return GameState.O_WIN;
        } else {
            return emptyCount == 0 ? GameState.DRAW : GameState.CONTINUE;
        }
    }

    private void setGameState(GameState gameState) {
        this.gameState = gameState;
        if (gameState.hasPrompt()) {
            showLine(gameState.getPrompt());
        }
    }

    private long getAllMatchValueLineCount(FieldState fieldState, FieldState[]... lines) {
        return Arrays.stream(lines)
            .filter(line -> Arrays.stream(line)
                .allMatch(it -> it == fieldState))
            .count();
    }

    private static void showLine(String line) {
        System.out.print(line);
    }

    private static void showNewLine(String line) {
        System.out.println(line);
    }

    @Override
    public String toString() {
        return Arrays.stream(cells)
            .map(line -> Arrays.stream(line)
                .map(FieldState::getView)
                .collect(Collectors.joining(" ", "| ", " |")))
            .collect(Collectors.joining("\n",
                                        "---------\n",
                                        "\n---------"));
    }

    public void input(String input) {
        if (gameState == GameState.CONTINUE) {
            Move move = parseValidMove(input);
            if (move != null) {
                setCell(move.getLine(), move.getColumn());
                showNewLine(toString());
                setGameState(calculateState());
            } else {
                showLine(gameState.getPrompt());
            }
        }
        if (isComplete()) {
            showNewLine(gameState.toString());
        }
    }

    public boolean isComplete() {
        return gameState != GameState.CONTINUE;
    }
}

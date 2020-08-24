package com.company.tictactoe;

class Move
{
    private int line;
    private int column;

    public Move(int line, int column) {
        this.line = line;
        this.column = column;
    }

    public int getLine() {
        return line;
    }

    public int getColumn() {
        return column;
    }
}

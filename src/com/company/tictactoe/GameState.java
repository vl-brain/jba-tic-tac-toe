package com.company.tictactoe;

enum GameState
{
    X_WIN("X wins"),
    O_WIN("O wins"),
    DRAW("Draw"),
    INVALID("Impossible"),
    CONTINUE("Game not finished", "Enter the coordinates: ");
    private String description;
    private String prompt;

    GameState(String description) {
        this.description = description;
    }

    GameState(String description, String prompt) {
        this.description = description;
        this.prompt = prompt;
    }

    @Override
    public String toString() {
        return description;
    }

    public String getPrompt() {
        return prompt;
    }

    public boolean hasPrompt() {
        return prompt != null;
    }
}

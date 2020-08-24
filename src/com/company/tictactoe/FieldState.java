package com.company.tictactoe;

enum FieldState
{
    X("X"),
    O("O"),
    EMPTY("_");
    private final String view;

    FieldState(String view) {
        this.view = view;
    }

    public static FieldState getByView(String view) {
        for (FieldState fieldState : values()) {
            if (fieldState.view.equals(view)) {
                return fieldState;
            }
        }
        throw new IllegalStateException("Unknown view");
    }

    public String getView() {
        return view;
    }
}

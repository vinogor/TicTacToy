package ru.job4j.tictactoy;

interface MainActivityActions {

    void makeDialog(String message);

    void setTextCurrentPlayer(String currentSign);

    void setButtonText(int viewId, String text);

}

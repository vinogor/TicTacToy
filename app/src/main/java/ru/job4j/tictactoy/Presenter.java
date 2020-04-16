package ru.job4j.tictactoy;

public interface Presenter {

    void makeToast(String msg);

    void  startRound();

    void setTextCurrentPlayer(String sign);

    void setTextButton(int row, int column, String currentSign);
}

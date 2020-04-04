package ru.job4j.tictactoy;

import android.view.View;

interface TicTakActions {

    void answer(View view);

    void pcOrHuman(View view);

    void makeToast(String message);

    void setTextWhoseMoveNow(String currentSign);

    void setButtonText(int row, int column, String text);

}

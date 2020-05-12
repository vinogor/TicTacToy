package ru.job4j.tictactoy;

import android.os.Bundle;
import android.widget.Button;


public interface Presenter {

    interface ActionsWithLogic {

        void makeDialog(String msg);

        void startRound();

        void setTextCurrentPlayer(String sign);

        void setTextButton(int row, int column, String currentSign);

    }

    interface ActionsWithActivity {

        void start(Bundle bundle);

        void attachView(MainActivityActions activity);

        void handleAnswerByView(Button button);

        void change2player();

        void saveInstance(Bundle bundle);

        void detachView();
    }
}

package ru.job4j.tictactoy;

import android.os.Bundle;
import android.widget.Button;


public interface Presenter {

    interface LogicActions {

        void makeToast(String msg);

        void startRound();

        void setTextCurrentPlayer(String sign);

        void setTextButton(int row, int column, String currentSign);

    }

    interface ActivityActions {

        void start(Bundle bundle);

        void attachView(MainActivity activity);

        void handleAnswerByView(Button button);

        void change2player();

        void saveInstance(Bundle bundle);

        void detachView();
    }
}

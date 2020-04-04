package ru.job4j.tictactoy;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

// визуальная чать + обработка действий пользователя
public class MainActivity extends AppCompatActivity implements TicTakActions {

    private static final String ROW = "row";
    private static final String ENEMY_IS_HUMAN = "enemyIsHuman";
    private static final String COUNTER = "counter";
    private static final String SIGN = "sign";

    private TextView whoseMove;

    private Logic logic;
    private int size;
    private int[][] buttonsIds;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        buttonsIds = new int[][]{
                {R.id.button00, R.id.button01, R.id.button02},
                {R.id.button10, R.id.button11, R.id.button12},
                {R.id.button20, R.id.button21, R.id.button22}
        };

        whoseMove = this.findViewById(R.id.currentPlayer);

        this.logic = new Logic(this);
        this.size = this.logic.getSize();

        if (savedInstanceState == null) {
            this.logic.start();

        } else {
            String[][] field = new String[size][size];
            for (int i = 0; i < size; i++) {

                field[i] = savedInstanceState.getStringArray(ROW + i);
            }
            boolean enemyIsHuman = savedInstanceState.getBoolean(ENEMY_IS_HUMAN);
            int counter = savedInstanceState.getInt(COUNTER);
            String sign = savedInstanceState.getString(SIGN);

            this.logic.startAfterRestart(field, enemyIsHuman, counter, sign);
        }
    }

    // передаёт в logic уже координаты
    public void answer(View view) {
        String btnName = view.getResources().getResourceEntryName(view.getId());
        int length = btnName.length();
        int row = Character.getNumericValue(btnName.charAt(length - 2));
        int column = Character.getNumericValue(btnName.charAt(length - 1));

        logic.handleAnswer(row, column);
    }

    public void pcOrHuman(View view) {
        logic.change2player();
    }

    public void makeToast(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }

    public void setTextWhoseMoveNow(String currentSign) {
        whoseMove.setText(String.format("waiting for %s player's move", currentSign));
    }

    public void setButtonText(int row, int column, String text) {
        ((Button) findViewById(buttonsIds[row][column])).setText(text);
    }

    @Override
    protected void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);

        String[][] field = logic.getField();
        for (int i = 0; i < size; i++) {
            outState.putStringArray(ROW + i, field[i]);
        }
        outState.putBoolean(ENEMY_IS_HUMAN, logic.isEnemyIsHuman());
        outState.putInt(COUNTER, logic.getCounter());
        outState.putString(SIGN, logic.getCurrentSign());
    }
}

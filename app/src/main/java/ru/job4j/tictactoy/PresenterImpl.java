package ru.job4j.tictactoy;

import android.os.Bundle;
import android.widget.Button;

import static ru.job4j.tictactoy.Logic.S;

// === в M-V-P это PRESENTER ===

// создаёт Logic
// знает и общается с Logic и View

public class PresenterImpl implements Presenter {

    private static final String ROW = "row";
    private static final String ENEMY_IS_HUMAN = "enemyIsHuman";
    private static final String COUNTER = "counter";
    private static final String SIGN = "sign";

    private MainActivity activity;
    private Logic logic;
    private int[][] buttonsIds;
    private int size;

    public PresenterImpl() {
        logic = new Logic(this);
        buttonsIds = new int[][]{
                {R.id.button00, R.id.button01, R.id.button02},
                {R.id.button10, R.id.button11, R.id.button12},
                {R.id.button20, R.id.button21, R.id.button22}
        };
        size = logic.getSize();
    }

    public void start(Bundle bundle) {
        if (bundle == null) {
            startRound();
        } else {
            startAfterRestart(bundle);
        }
    }

    @Override
    public void startRound() {
        logic.cleanField();
        cleanTextOfButtons();
        activity.setTextCurrentPlayer(logic.getCurrentSign());
    }

    private void startAfterRestart(Bundle bundle) {

        String[][] field = new String[size][size];
        for (int i = 0; i < size; i++) {
            field[i] = bundle.getStringArray(ROW + i);
        }

        String currentSign = bundle.getString(SIGN);
        activity.setTextCurrentPlayer(currentSign);

        logic.setField(field);
        logic.setEnemyIsHuman(bundle.getBoolean(ENEMY_IS_HUMAN));
        logic.setCounter(bundle.getInt(COUNTER));
        logic.setCurrentSign(currentSign);

        setTextOfButtons(field);
    }

    private void setTextOfButtons(String[][] field) {
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                int viewId = buttonsIds[i][j];
                activity.setButtonText(viewId, field[i][j]);
            }
        }
    }

    private void cleanTextOfButtons() {
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                int viewId = buttonsIds[i][j];
                activity.setButtonText(viewId, S);
            }
        }
    }

    public void handleAnswerByView(Button button) {
        int[] coordinates = getCoordinates(button);
        logic.handleAnswerByCoordinates(coordinates[0], coordinates[1]);
    }

    public int[] getCoordinates(Button button) {
        String btnName = button.getResources().getResourceEntryName(button.getId());
        int length = btnName.length();
        int row = Character.getNumericValue(btnName.charAt(length - 2));
        int column = Character.getNumericValue(btnName.charAt(length - 1));
        return new int[]{row, column};
    }

    public void change2player() {
        logic.change2player();
    }

    public void saveInstance(Bundle bundle) {
        for (int i = 0; i < size; i++) {
            bundle.putStringArray(ROW + i, logic.getRaw(i));
        }
        bundle.putBoolean(ENEMY_IS_HUMAN, logic.isEnemyIsHuman());
        bundle.putInt(COUNTER, logic.getCounter());
        bundle.putString(SIGN, logic.getCurrentSign());
    }

    public void attachView(MainActivity activity) {
        this.activity = activity;
    }

    public void detachView() {
        this.activity = null;
    }

    @Override
    public void makeToast(String msg) {
        activity.makeToast(msg);
    }

    @Override
    public void setTextCurrentPlayer(String currentSign) {
        activity.setTextCurrentPlayer(currentSign);
    }

    @Override
    public void setTextButton(int row, int column, String currentSign) {
        int viewId = buttonsIds[row][column];
        activity.setButtonText(viewId, currentSign);
    }
}

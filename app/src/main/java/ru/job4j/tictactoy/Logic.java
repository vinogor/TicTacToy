package ru.job4j.tictactoy;

import android.os.Bundle;
import android.view.View;

// только логика
public class Logic {

    private MainActivity activity;

    public static final String X = "X";  // 1-й игрок, первый раз ходит первым
    public static final String O = "O";  // 2-й игрок, human or AI
    public static final String S = " ";  // Space

    private static final String ROW = "row";
    private static final String ENEMY_IS_HUMAN = "enemyIsHuman";
    private static final String COUNTER = "counter";
    private static final String SIGN = "sign";

    private String currentSign;
    private int counter;
    private int size;
    private String[][] field;
    private int[][] buttonsIds;

    private boolean enemyIsHuman;

    public Logic() {
        this.currentSign = X;
        this.size = 3;
        this.field = new String[size][size];
        this.enemyIsHuman = true;
        this.buttonsIds = new int[][]{
                {R.id.button00, R.id.button01, R.id.button02},
                {R.id.button10, R.id.button11, R.id.button12},
                {R.id.button20, R.id.button21, R.id.button22}
        };
    }

    public void attachView(MainActivity activity) {
        this.activity = activity;
    }

    public void detachView() {
        this.activity = null; // чтобы не было утечек памяти
    }

    public void start(Bundle savedInstanceState) {
        if (savedInstanceState == null) {
            firstStart();
        } else {
            startAfterRestart(savedInstanceState);
        }
    }

    public void firstStart() {
        clean();
        activity.setTextWhoseMoveNow(currentSign);
    }

    private void startAfterRestart(Bundle savedInstanceState) {
        String[][] field = new String[size][size];
        for (int i = 0; i < size; i++) {
            field[i] = savedInstanceState.getStringArray(ROW + i);
        }

        this.field = field;
        this.enemyIsHuman = savedInstanceState.getBoolean(ENEMY_IS_HUMAN);
        this.counter = savedInstanceState.getInt(COUNTER);
        this.currentSign = savedInstanceState.getString(SIGN);

        reDrawButtons(field);
        activity.setTextWhoseMoveNow(currentSign);
    }

    public void saveInstance(Bundle outState) {
        for (int i = 0; i < size; i++) {
            outState.putStringArray(ROW + i, field[i]);
        }
        outState.putBoolean(ENEMY_IS_HUMAN, enemyIsHuman);
        outState.putInt(COUNTER, counter);
        outState.putString(SIGN, currentSign);
    }

    private void reDrawButtons(String[][] field) {
        for (int row = 0; row < size; row++) {
            for (int column = 0; column < size; column++) {
                int viewId = buttonsIds[row][column];
                activity.setButtonText(viewId, field[row][column]);
            }
        }
    }

    public void handleAnswerByView(View viewButton) {
        int[] coordinates = getCoordinates(viewButton);
        handleAnswerByCoordinates(coordinates[0], coordinates[1]);
    }

    public void handleAnswerByCoordinates(int row, int column) {
        if (field[row][column].equals(S)) {
            makeMove(row, column);

            // если включен AI
            if (!enemyIsHuman && !currentSign.equals(X)) {
                int[] coordinates = getFreeCoordinates();
                makeMove(coordinates[0], coordinates[1]);
            }
        }
    }

    public int[] getCoordinates(View viewButton) {
        String btnName = viewButton.getResources().getResourceEntryName(viewButton.getId());
        int length = btnName.length();
        int row = Character.getNumericValue(btnName.charAt(length - 2));
        int column = Character.getNumericValue(btnName.charAt(length - 1));
        return new int[]{row, column};
    }

    private void makeMove(int row, int column) {
        setSignOnField(row, column);
        if (!checkEndOfGame()) {
            changeSign();
        }
    }

    public void change2player() {
        enemyIsHuman = !enemyIsHuman;

        // на случай если первый игрок уже походил и после переклюличи в режим AI
        if (!enemyIsHuman && !currentSign.equals(X)) {
            int[] coordinates = getFreeCoordinates();
            makeMove(coordinates[0], coordinates[1]);
        }
    }

    boolean checkEndOfGame() {
        if (checkWinner()) {
            activity.makeToast("winner is " + currentSign);
            changeSign();
            firstStart();
            return true;
        }

        if (counter == 9) {
            activity.makeToast("standoff");
            firstStart();
            return true;
        }
        return false;
    }

    private void changeSign() {
        currentSign = currentSign.equals(X) ? O : X;
        activity.setTextWhoseMoveNow(currentSign);
    }

    private void setSignOnField(int row, int column) {
        int viewId = buttonsIds[row][column];
        activity.setButtonText(viewId, currentSign);
        field[row][column] = currentSign;
        counter++;
    }

    private int[] getFreeCoordinates() {
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                if (field[i][j].equals(S)) {
                    return new int[]{i, j};
                }

            }
        }
        return null;
    }

    private void clean() {
        counter = 0;
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                // очистка внутреннего массива
                field[i][j] = S;
                // очистка текста всех кнопок
                int viewId = buttonsIds[i][j];
                activity.setButtonText(viewId, S);
            }
        }
    }

    boolean checkWinner() {

        // проверка диагоналей
        boolean leftDiag = true;
        boolean rightDiag = true;
        for (int i = 0; i < size; i++) {
            if (leftDiag && !field[i][i].equals(currentSign)) {
                leftDiag = false;
            }
            if (rightDiag && !field[i][size - i - 1].equals(currentSign)) {
                rightDiag = false;
            }
        }

        if (leftDiag || rightDiag) {
            return true;
        }

        // проверка горизонталей и вертикалей
        boolean horiz = true;
        boolean vert = true;
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                if (horiz && !field[i][j].equals(currentSign)) {
                    horiz = false;
                }
                if (vert && !field[j][i].equals(currentSign)) {
                    vert = false;
                }
            }
            if (horiz || vert) {
                return true;
            }
            horiz = true;
            vert = true;
        }
        return false;
    }

    public String[][] getField() {
        return field;
    }

    public int getSize() {
        return size;
    }

    public int getCounter() {
        return counter;
    }

    public boolean isEnemyIsHuman() {
        return enemyIsHuman;
    }

    public String getCurrentSign() {
        return currentSign;
    }

    public void setCounter(int counter) {
        this.counter = counter;
    }

    public void setField(String[][] field) {
        this.field = field;
    }
}

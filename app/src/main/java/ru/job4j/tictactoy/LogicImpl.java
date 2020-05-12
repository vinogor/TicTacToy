package ru.job4j.tictactoy;

// === в M-V-P это MODEL ===

// только логика игры
// знает только о Presenter

import androidx.annotation.VisibleForTesting;

public class LogicImpl implements Logic {

    static final String X = "X";  // 1-й игрок, первый раз ходит первым
    static final String O = "O";  // 2-й игрок, human or AI
    static final String S = " ";  // Space

    private Presenter.ActionsWithLogic presenter;

    private String currentSign;
    private int counter;
    private int size;
    private String[][] field;

    private boolean enemyIsHuman;

    public LogicImpl(Presenter.ActionsWithLogic presenter) {
        this.currentSign = X;
        this.size = 3;
        this.field = new String[size][size];
        this.enemyIsHuman = true;
        this.presenter = presenter;
    }

    @Override
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

    private void makeMove(int row, int column) {
        setSignOnField(row, column);
        if (!checkEndOfGame()) {
            changeSign();
        }
    }

    @Override
    public void change2player() {
        enemyIsHuman = !enemyIsHuman;

        // на случай если первый игрок уже походил и после переклюличи в режим AI
        if (!enemyIsHuman && !currentSign.equals(X)) {
            int[] coordinates = getFreeCoordinates();
            makeMove(coordinates[0], coordinates[1]);
        }
    }

    @VisibleForTesting
    @Override
    public boolean checkEndOfGame() {
        if (checkWinner()) {
            presenter.makeDialog("winner is " + currentSign);
            changeSign();
            presenter.startRound();
            return true;
        }

        if (counter == 9) {
            presenter.makeDialog("standoff");
            presenter.startRound();
            return true;
        }
        return false;
    }

    private void changeSign() {
        currentSign = currentSign.equals(X) ? O : X;
        presenter.setTextCurrentPlayer(currentSign);
    }

    private void setSignOnField(int row, int column) {
        presenter.setTextButton(row, column, currentSign);
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

    @Override
    public void cleanField() {
        counter = 0;
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                // очистка внутреннего массива
                field[i][j] = S;
            }
        }
    }

    @VisibleForTesting
    @Override
    public boolean checkWinner() {

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

    @Override
    public String[] getRaw(int i) {
        return field[i];
    }

    @Override
    public String[][] getField() {
        return field;
    }

    @Override
    public int getSize() {
        return size;
    }

    @Override
    public int getCounter() {
        return counter;
    }

    @Override
    public boolean isEnemyIsHuman() {
        return enemyIsHuman;
    }

    @Override
    public String getCurrentSign() {
        return currentSign;
    }

    @Override
    public void setCounter(int counter) {
        this.counter = counter;
    }

    @Override
    public void setField(String[][] field) {
        this.field = field;
    }

    @Override
    public void setCurrentSign(String currentSign) {
        this.currentSign = currentSign;
    }

    @Override
    public void setEnemyIsHuman(boolean enemyIsHuman) {
        this.enemyIsHuman = enemyIsHuman;
    }
}

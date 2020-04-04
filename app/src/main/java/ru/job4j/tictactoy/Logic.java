package ru.job4j.tictactoy;

// только логика
public class Logic {

    public static final String X = "X";  // 1-й игрок, первый раз ходит первым
    public static final String O = "O";  // 2-й игрок, human or AI
    public static final String S = " ";  // Space

    private MainActivity activity;
    private String currentSign;
    private int counter;
    private int size;
    private String[][] field;

    private boolean enemyIsHuman;

    public Logic(MainActivity activity) {
        this.activity = activity;
        this.currentSign = X;
        this.size = 3;
        this.field = new String[size][size];
        this.enemyIsHuman = true;
    }

    public void start() {
        clean();
        activity.setTextWhoseMoveNow(currentSign);
    }

    public void startAfterRestart(String[][] field, boolean enemyIsHuman, int counter, String sign) {
        this.field = field;
        this.enemyIsHuman = enemyIsHuman;
        this.counter = counter;
        this.currentSign = sign;
        activity.setTextWhoseMoveNow(currentSign);
        reDrawButtons(field);
    }

    private void reDrawButtons(String[][] field) {
        for (int row = 0; row < size; row++) {
            for (int column = 0; column < size; column++) {
                activity.setButtonText(row, column, field[row][column]);
            }
        }
    }

    public void handleAnswer(int row, int column) {

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
            start();
            return true;
        }

        if (counter == 9) {
            activity.makeToast("standoff");
            start();
            return true;
        }
        return false;
    }

    private void changeSign() {
        currentSign = currentSign.equals(X) ? O : X;
        activity.setTextWhoseMoveNow(currentSign);
    }

    private void setSignOnField(int row, int column) {
        activity.setButtonText(row, column, currentSign);
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
                activity.setButtonText(i, j, S);
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

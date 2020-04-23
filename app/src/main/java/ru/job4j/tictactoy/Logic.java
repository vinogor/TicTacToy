package ru.job4j.tictactoy;

interface Logic {

    void handleAnswerByCoordinates(int row, int column);

    void change2player();

    void cleanField();

    String[] getRaw(int i);

    String[][] getField();

    int getSize();

    int getCounter();

    boolean isEnemyIsHuman();

    String getCurrentSign();

    void setCounter(int counter);

    void setField(String[][] field);

    void setCurrentSign(String currentSign);

    void setEnemyIsHuman(boolean enemyIsHuman);


    // needs for testing:

    boolean checkWinner();

    boolean checkEndOfGame();

}

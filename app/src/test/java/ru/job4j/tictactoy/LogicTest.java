package ru.job4j.tictactoy;

import org.junit.Before;
import org.junit.Test;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;
import static ru.job4j.tictactoy.Logic.O;
import static ru.job4j.tictactoy.Logic.S;
import static ru.job4j.tictactoy.Logic.X;

public class LogicTest {

    private Logic logic;

    @Before
    public void setUp() {
        Presenter.LogicActions presenter = new PresenterStub();
        logic = new Logic(presenter);
    }

    @Test
    public void handleAnswerTest_00() {
        logic.cleanField();
        String[][] res = logic.getField();
        assertThat(res, is(new String[][]{
                {S, S, S},
                {S, S, S},
                {S, S, S}
        }));
    }

    @Test
    public void handleAnswerTest_01() {
        logic.cleanField();
        logic.handleAnswerByCoordinates(0, 0);
        String[][] res = logic.getField();
        assertThat(res, is(new String[][]{
                {X, S, S},
                {S, S, S},
                {S, S, S}
        }));
    }

    @Test
    public void handleAnswerTest_02() {
        logic.cleanField();
        logic.handleAnswerByCoordinates(0, 0);
        logic.handleAnswerByCoordinates(1, 1);
        String[][] res = logic.getField();
        assertThat(res, is(new String[][]{
                {X, S, S},
                {S, O, S},
                {S, S, S}
        }));
    }

    @Test
    public void handleAnswerTest_03() {
        logic.cleanField();
        logic.handleAnswerByCoordinates(0, 0);
        logic.handleAnswerByCoordinates(0, 0);
        logic.handleAnswerByCoordinates(1, 1);
        String[][] res = logic.getField();
        assertThat(res, is(new String[][]{
                {X, S, S},
                {S, O, S},
                {S, S, S}
        }));
    }

    @Test
    public void checkWinnerTest_01() {
        logic.cleanField();

        logic.setField(new String[][]{
                {X, S, S},
                {S, X, S},
                {S, S, X}
        });
        assertThat(logic.checkWinner(), is(true));
    }

    @Test
    public void checkWinnerTest_02() {
        logic.cleanField();

        logic.setField(new String[][]{
                {S, S, X},
                {S, X, S},
                {X, S, S}
        });
        assertThat(logic.checkWinner(), is(true));
    }

    @Test
    public void checkWinnerTest_03() {
        logic.cleanField();

        logic.setField(new String[][]{
                {X, X, X},
                {S, S, S},
                {S, S, S}
        });
        assertThat(logic.checkWinner(), is(true));
    }

    @Test
    public void checkWinnerTest_04() {
        logic.cleanField();

        logic.setField(new String[][]{
                {S, S, S},
                {X, X, X},
                {S, S, S}
        });
        assertThat(logic.checkWinner(), is(true));
    }

    @Test
    public void checkWinnerTest_05() {
        logic.cleanField();

        logic.setField(new String[][]{
                {S, S, S},
                {S, S, S},
                {X, X, X}
        });
        assertThat(logic.checkWinner(), is(true));
    }

    @Test
    public void checkWinnerTest_06() {
        logic.cleanField();

        logic.setField(new String[][]{
                {X, S, S},
                {X, S, S},
                {X, S, S}
        });
        assertThat(logic.checkWinner(), is(true));
    }

    @Test
    public void checkWinnerTest_07() {
        logic.cleanField();

        logic.setField(new String[][]{
                {S, X, S},
                {S, X, S},
                {S, X, S}
        });
        assertThat(logic.checkWinner(), is(true));
    }

    @Test
    public void checkWinnerTest_08() {
        logic.cleanField();

        logic.setField(new String[][]{
                {S, S, X},
                {S, S, X},
                {S, S, X}
        });
        assertThat(logic.checkWinner(), is(true));
    }

    @Test
    public void checkWinnerTest_09() {
        logic.cleanField();

        logic.setField(new String[][]{
                {S, S, X},
                {S, X, S},
                {S, S, X}
        });
        assertThat(logic.checkWinner(), is(false));
    }

    @Test
    public void checkWinnerTest_10() {
        logic.cleanField();

        logic.setField(new String[][]{
                {X, S, X},
                {S, O, S},
                {X, S, X}
        });
        assertThat(logic.checkWinner(), is(false));
    }

    @Test
    public void checkEndOfGameTest_01() {
        logic.cleanField();
        logic.setCounter(9);
        assertThat(logic.checkEndOfGame(), is(true));
    }

    @Test
    public void checkEndOfGameTest_02() {
        logic.cleanField();
        logic.setCounter(8);
        assertThat(logic.checkEndOfGame(), is(false));
    }
}
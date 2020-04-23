package ru.job4j.tictactoy;

import android.app.DialogFragment;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

// === в M-V-P это VIEW ===

// управдение только визуальной частью
// создаёт Presenter и знает только о нём

public class MainActivity extends AppCompatActivity implements MainActivityActions {

    private Presenter.ActionsWithActivity presenter;
    private TextView whoseMove;
    private DialogFragment dlg;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        dlg = new Dialog1();
        init();
        presenter.start(savedInstanceState);
    }

    private void init() {
        presenter = new PresenterImpl();
        presenter.attachView(this);
        whoseMove = findViewById(R.id.currentPlayer);
    }

    public void answer(View view) {
        presenter.handleAnswerByView((Button) view);
    }

    public void pcOrHuman(View view) {
        presenter.change2player();
    }

    @Override
    public void makeToast(String message) {
        Bundle bundle = new Bundle();
        bundle.putString("message", message);
        dlg.setArguments(bundle);
        dlg.show(getFragmentManager(), "dlg");
    }

    @Override
    public void setTextCurrentPlayer(String currentSign) {
        whoseMove.setText(String.format("waiting for %s player's move", currentSign));
    }

    @Override
    public void setButtonText(int viewId, String text) {
        ((Button) findViewById(viewId)).setText(text);
    }

    @Override
    protected void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        presenter.saveInstance(outState);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        presenter.detachView();
    }
}

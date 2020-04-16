package ru.job4j.tictactoy;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

// === в M-V-P это VIEW ===

// управдение только визуальной частью
// создаёт Presenter и знает только о нём

public class MainActivity extends AppCompatActivity {

    private PresenterImpl presenter;
    private TextView whoseMove;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
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

    public void makeToast(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }

    public void setTextCurrentPlayer(String currentSign) {
        whoseMove.setText(String.format("waiting for %s player's move", currentSign));
    }

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

package ru.job4j.tictactoy;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

// только визуальная часть
public class MainActivity extends AppCompatActivity {

    private Logic logic;
    private TextView whoseMove;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        init();

        logic.start(savedInstanceState);
    }

    private void init() {

        logic = new Logic();
        logic.attachView(this);

        whoseMove = findViewById(R.id.currentPlayer);
    }

    public void answer(View view) {
        logic.handleAnswerByView(view);
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

    public void setButtonText(int viewId, String text) {
        ((Button) findViewById(viewId)).setText(text);
    }

    @Override
    protected void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        logic.saveInstance(outState);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        logic.detachView();
    }
}

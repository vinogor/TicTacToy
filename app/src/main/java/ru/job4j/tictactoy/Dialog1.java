package ru.job4j.tictactoy;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.os.Bundle;
import android.view.Gravity;
import android.view.Window;

public class Dialog1 extends DialogFragment implements OnClickListener {

    public Dialog onCreateDialog(Bundle savedInstanceState) {

        String message = getArguments().getString("message");

        AlertDialog.Builder adb = new AlertDialog.Builder(getActivity())
                .setTitle(message)
                .setPositiveButton("OK", this);
        return adb.create();
    }

    @Override
    public void onStart() {
        super.onStart();
        Window window = getDialog().getWindow();
        window.setGravity(Gravity.BOTTOM);
    }

    @Override
    public void onClick(DialogInterface dialog, int which) {
        dismiss();
    }
}


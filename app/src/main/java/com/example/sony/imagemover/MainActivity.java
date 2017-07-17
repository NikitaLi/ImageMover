package com.example.sony.imagemover;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private TextView textViewHistoryOfMoves;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button btnStart = (Button) findViewById(R.id.btn_start_moving);
        btnStart.setOnClickListener(this);

        textViewHistoryOfMoves = (TextView) findViewById(R.id.tv_history);
        textViewHistoryOfMoves.setMovementMethod(new ScrollingMovementMethod());
    }

    @Override
    public void onClick(View view) {
        Intent intent = new Intent(this, Activity2.class);
        startActivityForResult(intent, 1);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        String historyInString = "";
        if (data != null) {
            SharedPreferences pref = getSharedPreferences(Constants.SHARED_PREFERENCES_FILE, MODE_PRIVATE);
            Set<String> set = pref.getStringSet(Constants.MOVING_HISTORY, null);
            if (set != null) {
                List<String> historyList = new ArrayList<>(set);
                for (String i : historyList) {
                    historyInString += i + "\n";
                }
                textViewHistoryOfMoves.setText(historyInString);
            } else {
                textViewHistoryOfMoves.setText(
                        "Перемещай картинки, чтобы здесь появилась история перемещений:)"
                );
            }
        }
        clearPreferences();
    }

    void clearPreferences() {
        getSharedPreferences(Constants.SHARED_PREFERENCES_FILE, MODE_PRIVATE)
                .edit()
                .clear()
                .apply();
    }
}

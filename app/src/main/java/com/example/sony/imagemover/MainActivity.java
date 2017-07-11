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
    
    Button mBtnStart;
    TextView mTextViewHistoryOfMoves;

    SharedPreferences sPref;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mBtnStart = (Button) findViewById(R.id.goToActivity2);
        mBtnStart.setOnClickListener(this);

        mTextViewHistoryOfMoves = (TextView) findViewById(R.id.log);
        mTextViewHistoryOfMoves.setMovementMethod(new ScrollingMovementMethod());
        clearPreferences();
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
            sPref = getSharedPreferences("myPref", MODE_PRIVATE);
            Set<String> set = sPref.getStringSet("moving_history", null);
            List<String> log = new ArrayList<>(set);
            if (log != null) {
                for (String i : log) {
                    historyInString += i + "\n";
                }
            }
        }
        mTextViewHistoryOfMoves.setText(historyInString);
    }

    void clearPreferences() {
        sPref = getSharedPreferences("myPref", MODE_PRIVATE);
        SharedPreferences.Editor ed = sPref.edit();
        ed.clear();
        ed.apply();
    }
}

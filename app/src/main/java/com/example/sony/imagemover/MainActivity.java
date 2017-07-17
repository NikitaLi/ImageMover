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

    // FIXME: m не требуется в наименовании полей класса, просто btnStart
    // FIXME: так же поле должно быть локальным
    Button mBtnStart;
    // FIXME: 17.07.17 Поле должно быть приватным
    TextView mTextViewHistoryOfMoves;

    SharedPreferences sPref;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // FIXME: id для элементов view должны быть названы в стиле btn_some_name или tv_some_name
        mBtnStart = (Button) findViewById(R.id.goToActivity2);
        mBtnStart.setOnClickListener(this);

        mTextViewHistoryOfMoves = (TextView) findViewById(R.id.log);
        mTextViewHistoryOfMoves.setMovementMethod(new ScrollingMovementMethod());
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
            // FIXME: Хардкодное название SharedPreferences
            sPref = getSharedPreferences("myPref", MODE_PRIVATE);
            Set<String> set = sPref.getStringSet("moving_history", null);
            // FIXME: set может быть null
            List<String> historyList = new ArrayList<>(set);
            // FIXME: Проверка на null не требуется
            if (historyList != null) {
                for (String i : historyList) {
                    historyInString += i + "\n";
                }
            }
        }
        mTextViewHistoryOfMoves.setText(historyInString);
        clearPreferences();
    }

    void clearPreferences() {
        // FIXME: Хардкодное название SharedPreferences
        sPref = getSharedPreferences("myPref", MODE_PRIVATE);
        SharedPreferences.Editor ed = sPref.edit();
        ed.clear();
        ed.apply();
        // FIXME: Подобная запись предпочтительнее
        // getSharedPreferences(MY_PREFERENCES, MODE_PRIVATE)
        //        .edit()
        //        .clear()
        //        .apply();
    }
}

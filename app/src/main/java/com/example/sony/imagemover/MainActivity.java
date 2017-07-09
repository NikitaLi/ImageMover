package com.example.sony.imagemover;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    
    Button mBtnStart;
    TextView mHistoryOfMoves;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mBtnStart = (Button) findViewById(R.id.goToActivity2);
        mBtnStart.setOnClickListener(this);

        mHistoryOfMoves = (TextView) findViewById(R.id.log);
        mHistoryOfMoves.setMovementMethod(new ScrollingMovementMethod());
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
            ArrayList<MoveInfo> log = data.getExtras().getParcelableArrayList("history");
            if (log != null) {
                for (MoveInfo i : log) {
                    historyInString += i.mMoveInfo + "\n";
                }
            }
        }
        mHistoryOfMoves.setText(historyInString);
    }
}

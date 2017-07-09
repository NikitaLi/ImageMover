package com.example.sony.imagemover;

import android.app.FragmentTransaction;
import android.app.FragmentManager;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;

public class Activity2 extends AppCompatActivity implements View.OnClickListener {

    Spinner mSpinner;
    Button mBtnStop;

    private FragmentManager mFragmentManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_2);

        mSpinner = (Spinner) findViewById(R.id.scaleTypeSpinner);

        mFragmentManager = getFragmentManager();
        FragmentTransaction fragmentTransaction = mFragmentManager.beginTransaction();

        for (int i = 1; i <= 6; i++) {
            int resId = getResources().getIdentifier("frame" + i, "id", getPackageName());
            fragmentTransaction.add(resId, new ImgFragment().newInstance(i), Integer.toString(i));
        }
        fragmentTransaction.commit();
        mFragmentManager.executePendingTransactions();

        mBtnStop = (Button) findViewById(R.id.stop_button);
        mBtnStop.setOnClickListener(this);
        setScaleType();
    }

    @Override
    public void onClick(View v) {
        goToMainActivityAndShowHistory();
    }

    @Override
    public void onBackPressed() {
        goToMainActivityAndShowHistory();
    }

    private void setScaleType() {

        final String[] scaleTypeVariants = getResources().getStringArray(R.array.scale_type_variants);

        ArrayAdapter<?> adapter =
                ArrayAdapter.createFromResource(this, R.array.scale_type_variants, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        mSpinner.setAdapter(adapter);

        mSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                for (int j = 1; j <= 6; j++) {
                    ImgFragment fragment = (ImgFragment) mFragmentManager.findFragmentByTag(Integer.toString(j));
                    fragment.setScaleTypeForImage(scaleTypeVariants[i]);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
    }

    protected ImgFragment getPreviousFragment() {
        FragmentManager mFragmentManager = getFragmentManager();
        return (ImgFragment) mFragmentManager.findFragmentByTag(Integer.toString(Mover.sPrevImgID));
    }

    private void goToMainActivityAndShowHistory() {
        Intent intent = new Intent();
        intent.putExtra("history", Mover.sMovingHistory);
        setResult(RESULT_OK, intent);
        finish();
    }
}

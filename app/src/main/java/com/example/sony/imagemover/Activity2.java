package com.example.sony.imagemover;

import android.app.FragmentTransaction;
import android.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class Activity2 extends AppCompatActivity implements View.OnClickListener {

    private FragmentManager mFragmentManager;
    private FragmentTransaction mFragmentTransaction;

    public static int prevImgID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_2);

        mFragmentManager = getFragmentManager();
        mFragmentTransaction = mFragmentManager.beginTransaction();

        for (int i = 1; i <= 6; i++) {
            int resId = getResources().getIdentifier("frame" + i, "id", getPackageName());
            mFragmentTransaction.add(resId, new ImgFragment().newInstance(i), Integer.toString(i));
        }
        mFragmentTransaction.commit();
        mFragmentManager.executePendingTransactions();
    }

    @Override
    public void onClick(View v) {

    }
}
